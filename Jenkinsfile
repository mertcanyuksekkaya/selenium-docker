pipeline{
    agent any

    stages{
        stage("Build jar"){
            steps{
                //mac
                sh "mvn clean package -DskipTests"
                //windows
                //bat "mvn clean package -DskipTests"
            }
        }
        stage("Build image"){
            steps{
                sh "docker build -t=mertcany/selenium-grid ."
            }
        }
        stage("Push image"){
            environment{
                DOCKER_HUB = credentials('dockerhub-creds')
            }
            steps{
                //windows
                //bat 'docker login -u ${DOCKER_HUB_USR} -p ${DOCKER_HUB_PSW}'
                sh 'echo ${DOCKER_HUB_PSW} | docker login -u ${DOCKER_HUB_USR} --password-stdin'
                sh "docker push mertcany/selenium-grid"
            }
        }
    }
    post{
        always{
            sh "docker logout"
        }
    }
}