pipeline {
  agent any
  stages {
    stage('code coverage') {
      steps {
        sh 'mvn clean cobertura:cobertura'
      }
    }

    stage('build') {
      steps {
        sh 'mvn clean package'
      }
    }

    stage('deployement') {
      steps {
        sh 'mv ${WORKSPACE}/target/ReapMyTube.jar /var/SpringServer/ReapMyTube.jar'
        sh '''FILE="/var/SpringServer/pid.file"
if [ -f "$FILE" ]; then
PID=$(cat "$FILE")
if ps -p $PID > /dev/null
then
kill $PID 
fi
rm "$FILE"
fi'''
        sleep 10
        sh 'java -jar  /var/SpringServer/ReapMyTube.jar --youtube.key=AIzaSyBW3vUm0FYk0pr65dxkc1U1FD37CCF0Kos  & echo $! > /var/SpringServer/pid.file &'
      }
    }

  }
}