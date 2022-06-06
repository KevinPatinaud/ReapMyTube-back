pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh 'mvn clean package'
      }
    }

    stage('execute') {
      steps {
        sh '''
java -jar  ${WORKSPACE}/target/ReapMyTube-0.0.1-SNAPSHOT.jar --youtube.key=AIzaSyBW3vUm0FYk0pr65dxkc1U1FD37CCF0Kos'''
      }
    }

  }
}