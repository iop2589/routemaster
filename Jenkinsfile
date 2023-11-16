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
            sh 'docker buildx build --platform=linux/amd64 --tag iop2589/route-master ./ --push'
        }
      }
    }

    stage('Deploy Docker') {
      steps {
        echo 'Deploy Docker'
        sshPublisher(publishers: [sshPublisherDesc(configName: 'jenkins-ansible-server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'ansible-playbook route-master-deploy-playbook.yml', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '.', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
      }
    }
  }
  post {
    success {
      echo 'Successfully docker deploy'
    }
    failure {
      error 'This pipeline stops here...'
    }
  }
}
