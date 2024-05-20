// List of microservices
def microservices = [
    'config-server',
    'discovery-server',
//     'auth-service',
//     'user-service',
//     'report-service',
//     'expense-service',
//     'transaction-service',
//     'gateway-server'
]

pipeline {
    agent any
    environment {
        APP_NAME = 'financier'
        PUSH_TO_DOCKER_HUB = 'false'
        DOCKER_COMPOSE_CONFIG = 'default'
        DOCKER_IMAGE_PREFIX = 'anuragbabal/financier'
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
                        // Execute Ansible playbook
                        sh "ansible-playbook -i ansible/hosts ansible/test.yaml -e microservice_name=${microservice}"
                    }
                }
            }
        }
        stage('Run Build (Backend)') {
            steps {
                script {
                    // Loop through each microservice
                    for (microservice in microservices) {
                        // Execute Ansible playbook
                        sh "ansible-playbook -i ansible/hosts ansible/build.yaml -e microservice_name=${microservice}"
                    }
                }
            }
        }
        stage('Build Docker Images') {
            steps {
                script {
                    for (microservice in microservices) {
                        sh "ansible-playbook -i ansible/hosts ansible/build-backend-images.yaml -e microservice_name=${microservice}"
                    }
                    sh "ansible-playbook -i ansible/hosts ansible/build-frontend-image.yaml -e microservice_name=${frontend}"
                }
            }
        }
        stage('Push Images to Docker Hub') {
            steps {
                script {
                    if (env.PUSH_TO_DOCKER_HUB == 'true') {
                        for (microservice in microservices) {
                            sh "ansible-playbook -i ansible/hosts ansible/push-images.yaml
                                -e microservice_name=${microservice}"
                        }
                        sh "ansible-playbook -i ansible/hosts ansible/push-images.yaml
                            --ask-vault-pass
                            -e microservice_name=${frontend}
                            -e docker_hub_password=@ansible/my_vault.yaml"
                    }
                }
            }
        }
//         stage('Deploy with Docker Compose') {
//             steps {
//                 script {
//                     // sh 'docker-compose pull'
//                     // sh 'docker-compose up -d'  # Start all services in docker-compose.yml
//                     // Specify the configuration folder based on environment variable or logic
//                     def config = env.DOCKER_COMPOSE_CONFIG ?: 'default'  // Default to 'default'
//                     sh "docker-compose -f docker-compose/${config}/docker-compose.yaml up -d config-server frontend"
//                 }
//             }
//         }
        stage('Deploying to Kubernetes') {
            environment {
                ROOT_DIR = 'kubernetes'
                DEPLOYMENT_DIR = "${ROOT_DIR}/deploy"
                SERVICE_DIR = "${ROOT_DIR}/service"
            }
            steps {
                script {
                    for (microservice in microservices) {
//                         sh "kubectl apply -f ${env.DEPLOYMENT_DIR}/${microservice}-deployment.yaml"
//                         sh "kubectl apply -f ${env.SERVICE_DIR}/${microservice}-service.yaml"
                        sh "ansible-playbook -i ansible/hosts ansible/deploy-services.yaml
                            -e service_file=${env.DEPLOYMENT_DIR}/${microservice}-service.yaml
                            -e deployment_file=${env.DEPLOYMENT_DIR}/${microservice}-deployment.yaml"
                    }
                    sh "ansible-playbook -i ansible/hosts ansible/deploy-services.yaml
                        -e service_file=${env.DEPLOYMENT_DIR}/frontend-service.yaml
                        -e deployment_file=${env.DEPLOYMENT_DIR}/frontend-deployment.yaml"
//                     sh "kubectl apply -f ${env.DEPLOYMENT_DIR}/frontend-deployment.yaml"
//                     sh "kubectl apply -f ${env.SERVICE_DIR}/frontend-service.yaml"
                }
            }
        }
    }
}
