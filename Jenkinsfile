// List of microservices
def microservices = [
    'config-server',
//     'discovery-server',
//     'auth-service',
//     'user-service',
//     'report-service',
//     'expense-service',
//     'transaction-service',
//     'gateway-server'
]
def frontend = 'frontend'

pipeline {
    agent any
    environment {
        APP_NAME = 'financier'
        PUSH_TO_DOCKER_HUB = 'true'
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
                    for (microservice in microservices) {
                        sh "ansible-playbook -i ansible/hosts ansible/test.yaml -e microservice_name=${microservice}"
                    }
                }
            }
        }
        stage('Run Build (Backend)') {
            steps {
                script {
                    for (microservice in microservices) {
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
                        docker.withRegistry('', 'DockerHubCred') {
                            for (microservice in microservices) {
                                sh "docker tag ${microservice} ${env.DOCKER_IMAGE_PREFIX}-${microservice}:latest"
                                sh "docker push ${env.DOCKER_IMAGE_PREFIX}-${microservice}:latest"
                            }
                        }
                        sh "docker tag ${frontend} ${env.DOCKER_IMAGE_PREFIX}-${frontend}:latest"
                        sh "docker push ${env.DOCKER_IMAGE_PREFIX}-${frontend}:latest"
                    }
                }
            }
        }
        stage('Deploy with Docker Compose') {
            steps {
                script {
                    def config = env.DOCKER_COMPOSE_CONFIG ?: 'default'
                    sh "docker-compose -f docker-compose/${config}/docker-compose.yaml up -d config-server frontend"
                }
            }
        }
//         stage('Deploying to Kubernetes') {
//             environment {
//                 ROOT_DIR = '../kubernetes'
//                 DEPLOYMENT_DIR = "${ROOT_DIR}/deploy"
//                 SERVICE_DIR = "${ROOT_DIR}/service"
//             }
//             steps {
//                 script {
//                     for (microservice in microservices) {
// //                         sh "kubectl apply -f ${env.DEPLOYMENT_DIR}/${microservice}-deployment.yaml"
// //                         sh "kubectl apply -f ${env.SERVICE_DIR}/${microservice}-service.yaml"
//                         sh """ansible-playbook -i ansible/hosts ansible/deploy-services.yaml
//                             -e service_file=${env.DEPLOYMENT_DIR}/${microservice}-service.yaml
//                             -e deployment_file=${env.DEPLOYMENT_DIR}/${microservice}-deployment.yaml"""
//                     }
//                     sh """ansible-playbook -i ansible/hosts ansible/deploy-services.yaml
//                         -e service_file=${env.DEPLOYMENT_DIR}/frontend-service.yaml
//                         -e deployment_file=${env.DEPLOYMENT_DIR}/frontend-deployment.yaml"""
// //                     sh "kubectl apply -f ${env.DEPLOYMENT_DIR}/frontend-deployment.yaml"
// //                     sh "kubectl apply -f ${env.SERVICE_DIR}/frontend-service.yaml"
//                 }
//             }
//         }
    }
}
