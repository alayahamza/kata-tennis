pipeline {
    agent any
    stages {
         stage('Clone') {
            steps {
                git branch: 'docker', url: 'https://github.com/alayahamza/kata-tennis.git'
            }
         }
         stage('Build maven') {
            steps {
                sh "mvn clean install"
            }
         }
        stage('Build docker') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'a9cb814e-b2e4-421b-ae6a-8653d7c808c3', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh "docker build -t mariamtoujani/kata-tennis:2.0.0 ."
                    sh 'docker login -u "$USERNAME" -p "$PASSWORD" '
                    sh "docker push mariamtoujani/kata-tennis:2.0.0"
                }
            }
        }
    }
}
