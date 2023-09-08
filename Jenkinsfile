pipeline {
    agent any

    triggers {
        pollSCM('*/3 * * * *')
    }

    environment {
        imagename = "route-master"
        registryCredential = credentials('docker_hub_key')
        dockerImage = ''
    }

    stage('Initialize'){
        def dockerHome = tool 'myDocker'
        env.PATH = "${dockerHome}/bin:${env.PATH}"
    }

    stages {
        // git에서 repository clone
        stage('Prepare') {
          steps {
            echo 'Clonning Repository'
            git url: 'https://github.com/iop2589/routemaster.git',
              branch: 'main',
              credentialsId: 'GitHub_key'
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
                dockerImage = docker.build imagename
              } catch (err) {
                echo "Docker Build Falied ***** : ${err}"
              }
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }

        // docker push
        stage('Push Docker') {
          agent any
          steps {
            echo 'Push Docker'
            script {
                docker.withRegistry('https://registry.hub.docker.com', registryCredential) {
                    dockerImage.push("1.0")  // ex) "1.0"
                }
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }
    }
}