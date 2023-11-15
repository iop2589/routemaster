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
        sshPublisher(publishers: [sshPublisherDesc(configName: 'jenkins-ansible-server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'docker buildx build --platform=linux/amd64 --tag route-master ./ --push', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '.', remoteDirectorySDF: false, removePrefix: 'build/libs', sourceFiles: 'build/libs/*.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
      }
    }
  }
  post {
    failure {
      error 'This pipeline stops here...'
    }
  }
}
