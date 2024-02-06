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
            steps{
                sh "docker push mertcany/selenium-grid"
            }
        }
    }
    post{
        always{
            echo "doing clean up"
        }
    }
}