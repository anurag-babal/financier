// List of microservices
def microservices = [
    'config-server',
//     'discovery-server',
//     'auth-service',
//     'user-service',
//     'report-service',
//     'expense-service',
//     'gateway-server'
]
def frontend = 'frontend'

pipeline {
    agent any
    environment {
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
                for (microservice in microservices) {
                    sh "mvn test -f backend/${microservice}/pom.xml"
                }
            }
        }
        stage('Run Build (Backend)') {
            steps {
                for (microservice in microservices) {
                    sh "mvn test -f backend/${microservice}/pom.xml"
                }
            }
        }
        stage('Build Docker Images') {
            steps {
                script {
                    for (microservice in microservices) {
                        sh "docker build -t ${microservice} backend/${microservice}"
                    }
                    sh "docker build -t ${frontend} frontend"
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
                            sh "docker tag ${frontend} ${env.DOCKER_IMAGE_PREFIX}-${frontend}:latest"
                            sh "docker push ${env.DOCKER_IMAGE_PREFIX}-${frontend}:latest"
                        }
                    }
                }
            }
        }
        stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh "ansible-playbook -i ansible/hosts ansible/deploy.yaml -e docker_compose_config=${env.DOCKER_COMPOSE_CONFIG}"
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
