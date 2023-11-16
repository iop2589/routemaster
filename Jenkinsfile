pipeline {
  agent any
  stages {
      // git에서 repository clone
    stage('Git Clone') {
      steps {
        echo 'Clonning Repository'
        git branch: 'main', credentialsId: 'GitHub-credentials', url: 'https://github.com/iop2589/routemaster.git'
      }
    }

      // gradle build
    stage('Bulid Gradle') {
      steps {
        echo 'Bulid Gradle'
        dir ('.'){
            sh """
            ./gradlew clean build --exclude-task test
            """
        }
      }
    }
      
      // docker build
    stage('Build Docker') {
      steps {
        echo 'Build Docker'
        // This step should not normally be used in your script. Consult the inline help for details.
        withDockerRegistry(url: 'https://index.docker.io/v1/', credentialsId: 'docker-hub-key') {
            sh 'docker buildx build --platform=linux/amd64 --tag iop2589/route-master ./ --push some block'
        }
      }
    }
  }
  post {
    failure {
      error 'This pipeline stops here...'
    }
  }
}
