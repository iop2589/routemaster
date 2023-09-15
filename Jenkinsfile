pipeline {
    agent any

    triggers {
        pollSCM('*/3 * * * *')
    }

    environment {
        repository = "iop2589/routemaster"
        imagename = "routemaster"
        registryCredential = credentials('docker_hub_key')
        dockerImage = ''
        BUILD_NUMBER = "${BUILD_NUMBER}"
    }

    stages {
        // git에서 repository clone
        stage('Prepare') {
          steps {
            echo 'Clonning Repository'
            git url: 'https://github.com/iop2589/routemaster.git',
              branch: 'main',
              credentialsId: 'GitHub_key'
            script {
              def dockerHome = tool 'myDocker'
              env.PATH = "${dockerHome}/bin:${env.PATH}"
            }
          }
          post {
            success { 
              echo 'Successfully Cloned Repository'
            }
            failure {
              error 'This pipeline stops here...'
            }
          }
        }

        // gradle build
        stage('Bulid Gradle') {
          agent any
          steps {
            echo 'Bulid Gradle'
            dir ('.'){
                sh """
                ./gradlew clean build --exclude-task test
                """
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }
        
        // docker build
        stage('Bulid Docker') {
          agent any
          steps {
            echo 'Bulid Docker'
            script {
              try {
                //sh 'docker buildx build .'
                dockerImage = docker.build('iop2589/$imagename:$BUILD_NUMBER')
              } catch (err) {
                error "Docker Build Falied ***** : ${err}"
              }
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }

        stage('Push docker'){
          steps {
            container('docker'){
                script {
                    docker.withRegistry('https://registry.hub.docker.com', registryCredential){
                        appImage.push('$BUILD_NUMBER')
                        appImage.push("latest")
                    }
                }
            }
          }
        }
    }
}