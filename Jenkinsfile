pipeline {
    agent any
    environment {
        registryCredential = credentials('docker_hub_key')
        dockerImage = ''
        BUILD_NUMBER = "${BUILD_NUMBER}"
    }

    stages {
        // git에서 repository clone
        stage('Git Clone') {
          steps {
            echo 'Clonning Repository'
            git url: 'https://github.com/iop2589/routemaster.git' branch: 'main' credentialsId: 'GitHub_key'
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
            sh '''
              docker buildx build --platform=linux/amd64 --tag route-master ./ --push
            '''
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