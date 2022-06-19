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
        sh 'java -jar  ${WORKSPACE}/target/ReapMyTube.jar --youtube.key=AIzaSyBW3vUm0FYk0pr65dxkc1U1FD37CCF0Kos  & echo $! > ./pid.file &'
        sh '''
echo "kill $(cat ${WORKSPACE}/target/pid.file)"'''
      }
    }

  }
  post {
    always {
      junit '**/nosetests.xml'
      step([$class: 'CoberturaPublisher', autoUpdateHealth: false, autoUpdateStability: false, coberturaReportFile: '**/coverage.xml', failUnhealthy: false, failUnstable: false, maxNumberOfBuilds: 0, onlyStable: false, sourceEncoding: 'ASCII', zoomCoverageChart: false])
    }

  }
}