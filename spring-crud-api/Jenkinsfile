pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        DOCKER = credentials("Docker Hub")
    }

    stages {

        stage("pre-build") {
            steps{
                sh "sudo docker-compose -f docker-compose.yaml down || true"
                sh "sudo docker image rm rolisangor/crud-app || true"
            }
        }

        stage("package") {
            steps {
                script {
                    sh "mvn compile"
                    sh "mvn package -Dmaven.test.skip=true"
                    sh "sudo docker-compose -f docker-compose.yaml up -d"
                }
            }
        }

        stage("test") {
            steps {
                sh "echo test"
            }
        }

        stage("deploy") {
             steps {
                 sh "echo deploy"
                 sh 'echo $DOCKER_PSW | sudo docker login -u $DOCKER_USR --password-stdin'
                 sh 'sudo docker push rolisangor/crud-app:latest'
             }
        }
    }
}
