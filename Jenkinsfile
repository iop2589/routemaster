node {
        def dockerHubCred = credentials('docker_hub_key')
        def appImage
        
        stage('checkout'){
            container('git'){
                checkout scm
            }
        }
        
        stage('build'){
            container('docker'){
                script {
                    appImage = docker.build("iop2589/route-master")
                }
            }
        }
        stage('push'){
            container('docker'){
                script {
                    docker.withRegistry('https://registry.hub.docker.com', dockerHubCred){
                        appImage.push("${env.BUILD_NUMBER}")
                        appImage.push("latest")
                    }
                }
            }
        }
    }