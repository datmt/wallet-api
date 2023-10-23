//This pipeline build master and deploy on dev env
pipeline {
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven39'
    }
    environment {
        //The path in the deploy command are the one on the prod and preview environment respectively
        DEPLOY_COMMAND="ssh -o StrictHostKeyChecking=no  -p 25782 jenkins-agent@sso.openexl.com  echo PIGGYS_WALLET_API_VERSION=${GIT_COMMIT} > /data/other-projects/piggys-wallet/.env && docker-compose -f /data/other-projects/piggys-wallet/piggys-wallet-api.yaml up -d"

        JAVA_HOME="/opt/jdk-17.0.6/"

        SSH_AGENT="jenkins_docker_agent"
    }
    stages {
        stage('Maven test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Maven build image') {
            steps {
                sh 'mvn clean && mvn spring-boot:build-image -Dmaven.test.skip=true -Dspring-boot.build-image.imageName=registry.openexl.com/piggys-wallet-api:${GIT_COMMIT}'
            }
        }

        stage('Push to docker hub') {
            steps {
                script {
                docker.withRegistry('https://registry.openexl.com/repository/d1', 'jenkins_docker') {
                    docker.image("registry.openexl.com/piggys-wallet-api:${GIT_COMMIT}").push()
                 }
                }
            }
        }

        stage('Update deployment') {
            steps {
                script {
                    echo "Deploy command: ${GIT_COMMIT} ${DEPLOY_COMMAND}"
                    sshagent (credentials: ['${SSH_AGENT}']) {
                       sh '${DEPLOY_COMMAND}'
                   }
                }

            }
        }
    }
}
