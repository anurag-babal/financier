pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'master', url: 'https://github.com/anurag-babal/financier.git'
            }
        }
        stage('Run Tests (Backend)') {
            steps {
                script {
                    // Loop through all microservice directories (assuming they're in a folder named 'backend')
                    for (dir in glob('backend/*')) {
                        sh "ansible-playbook -i localhost ansible/test.yaml -e microservice_name=${basename dir}"
                    }
                }
            }
        }
        stage('Run Build (Backend)') {
            steps {
                script {
                    // Loop through all microservice directories (assuming they're in a folder named 'backend')
                    for (dir in glob('backend/*')) {
                        sh "ansible-playbook -i localhost ansible/build.yaml -e microservice_name=${basename dir}"
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
                            for (dir in glob('backend/*')) {
                                sh "docker push anuragbabal/${basename dir}:latest"
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
                    sh 'docker-compose pull'  # Pull latest images if pushed to Docker Hub
                    // sh 'docker-compose up -d'  # Start all services in docker-compose.yml
                    // Specify the configuration folder based on environment variable or logic
                    def config = env.DOCKER_COMPOSE_CONFIG ?: 'default'  // Default to 'default'
                    sh "docker-compose -f docker-compose/${config}/docker-compose.yaml up -d"
                }
            }
        }
    }
}
