// Directory containing microservices
def microservices_dir = 'backend'

// List of microservices
def microservices = [
    'config-server',
//     'discovery-server',
//     'gateway-server',
//     'auth-service',
//     'user-service',
//     'report-service',
//     'expense-service',
//     'transaction-service'
]

pipeline {
    agent any
    environment {
        PUSH_TO_DOCKER_HUB = 'true'
        DOCKER_COMPOSE_CONFIG = 'default'
    }
    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'master', url: 'https://github.com/anurag-babal/financier.git'
            }
        }
        stage('Run Tests (Backend)') {
            steps {
                script {
                    // Loop through each microservice
                    for (microservice in microservices) {
                        dir("${microservices_dir}/${microservice}") {
                            // Execute Ansible playbook
                            // sh "ansible-playbook -i localhost ansible/test.yaml -e microservice_name=${microservice}"
                            sh 'mvn test'
                        }
                    }
                }
            }
        }
        stage('Run Build (Backend)') {
            steps {
                script {
                    // Loop through each microservice
                    for (microservice in microservices) {
                        dir("${microservices_dir}/${microservice}") {
                            // Execute Ansible playbook
                            // sh "ansible-playbook -i localhost ansible/build.yaml -e microservice_name=${microservice}"
                            sh 'mvn clean package'
                        }
                    }
                }
            }
        }
        stage('Build Frontend') {
            steps {
                script {
                    sh "ansible-playbook -i localhost frontend/frontend-web/ansible/build.yaml"
                }
            }
        }
        stage('Build Docker Images') {
            steps {
                script {
                    sh "ansible-playbook -i localhost ansible/build-backend-images.yaml -e docker_username=anuragbabal"
                    sh "ansible-playbook -i localhost ansible/build-frontend-image.yaml -e docker_username=anuragbabal"
                }
            }
        }
//         stage('Build Backend Docker Images') {
//             steps {
//                 script {
//                     // Loop through all microservice directories
//                     for (dir in glob('backend/*')) {
//                         sh "ansible-playbook -i localhost ansible/build-backend-image.yaml -e microservice_name=${dir##*/}"
//                     }
//                 }
//             }
//         }
//         stage('Build Frontend Docker Image') {
//             steps {
//                 sh "docker build -t anuragbabal/frontend:latest frontend/frontend-web"
//             }
//         }
        stage('Push Images to Docker Hub') {
            steps {
                script {
                    if (env.PUSH_TO_DOCKER_HUB == 'true') {
                        // sh "docker login -u your-username -p \$DOCKER_PASSWORD"
                        docker.withRegistry('https://registry.hub.docker.com', 'DockerHubCred') {
                            // Loop through backend and frontend images to push
                            for (microservice in microservices) {
                                sh "docker push anuragbabal/${microservice}:latest"
                            }
                            sh "docker push anuragbabal/frontend:latest"
                        }
                    }
                }
            }
        }
        stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh 'docker-compose pull'
                    // sh 'docker-compose up -d'  # Start all services in docker-compose.yml
                    // Specify the configuration folder based on environment variable or logic
                    def config = env.DOCKER_COMPOSE_CONFIG ?: 'default'  // Default to 'default'
                    sh "docker-compose -f docker-compose/${config}/docker-compose.yaml up -d"
                }
            }
        }
    }
}
