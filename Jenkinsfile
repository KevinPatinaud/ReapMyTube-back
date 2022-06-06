pipeline {
  agent any
  stages {

    stage('shutdown the server') {
      steps {
        sh '''

process=`netstat -plten |grep java | grep 8082 | tr -s \' \' | cut -d" " -f 9 | cut -d"/" -f 1`

if [[ -n "$process" ]]
then
    kill -9 $process
fi'''
      }
    }

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
