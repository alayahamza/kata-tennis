pipeline {
    agent any

    stages {
         stage('Clone') {
            steps {
                git branch: 'docker', url: 'https://github.com/alayahamza/kata-tennis.git'
            }
        }
        stage('Build') {
            steps {
                sh "docker build -t mariamtoujani/kata-tennis:2.0.0 ."
                sh "docker push mariamtoujani/kata-tennis:2.0.0"

            }
        }
    }
}
