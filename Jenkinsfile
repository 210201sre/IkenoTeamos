  pipeline {  
      agent {
        kubernetes {
          inheritFrom 'build-agent'
          defaultContainer 'jnlp'
          yaml """
          apiVersion: v1
          kind: Pod
          metadata:
          labels:
            component: ci
          spec:
            containers:
            - name: jnlp
              image: odavid/jenkins-jnlp-slave:jdk11
              workingDir: /home/jenkins
              env:
              - name: DOCKER_HOST
                value: tcp://localhost:2375
              resources:
                requests:
                  memory: "1500Mi"
                  cpu: "0.3"
                limits:
                  memory: "2000Mi"
                  cpu: "0.5"
            - name: dind-daemon
              image: docker:18-dind
              workingDir: /var/lib/docker
              securityContext:
                privileged: true
              volumeMounts:
              - name: docker-storage
                mountPath: /var/lib/docker
              resources:
                requests:
                  memory: "1500Mi"
                  cpu: "0.3"
                limits:
                  memory: "2500Mi"
                  cpu: "0.5"
            - name: kubectl
              image: jshimko/kube-tools-aws:latest
              command:
              - cat
              tty: true
            volumes:
            - name: docker-storage
              emptyDir: {}
          """
        }
    }

    // commen

    environment{
      DOCKER_IMAGE_NAME = 'huskerhayes/ikenos-teamos'
      DOCKER_IMAGE_TAG = 'latest'
    }

    stages{
      stage('Build Docker Image'){
        steps {
          script {
            app = docker.build(DOCKER_IMAGE_NAME)
          }
        }
      }

      stage('Sonar Quality Analysis') {
          tools{
            jdk "jdk11"
          }
          steps {
              sh 'java -version'
              sh 'chmod +x mvnw'
              withSonarQubeEnv(credentialsId: 'sonar-ikenosteamos-token', installationName: 'sonarcloud') {
                  sh './mvnw -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
              }
          }
      }

        stage('Wait for Quality Gate') {
            steps {
                script {
                    timeout(time: 30, unit: 'MINUTES') {
                        qualitygate = waitForQualityGate abortPipeline: true
                    }
                }
            }
        }

      stage('Push Docker Image'){
        steps {
          script {
            docker.withRegistry('https://registry.hub.docker.com', 'docker-jenkins-token-ikenosteamos'){
              app.push('latest')
            }
          }
        }
      }

      stage('Canary Deployment'){
        environment{
          CANARY_REPLICAS = 1
        }
        steps {
          script{
            container('kubectl'){
              withKubeConfig([credentialsId: 'kubeconfig']){
                sh "aws eks update-kubeconfig --name matt-oberlies-sre-943"
                sh "kubectl -n ikenos-teamos set image deployment.apps/ikenos-teamos-canary ikenos-teamos=$DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG"
                sh "kubectl -n ikenos-teamos scale deployment ikenos-teamos-canary --replicas=$CANARY_REPLICAS"
              }
            }
          }
        }
      }

      stage('Production Deployment'){
        environment {
          CANARY_REPLICAS = 0
        }
        steps{
          input 'Is the canary ready for production?'
          script{
            container('kubectl'){
              withKubeConfig([credentialsId: 'kubeconfig']){
                sh "aws eks update-kubeconfig --name matt-oberlies-sre-943"
                sh "kubectl -n ikenos-teamos set image deployment.apps/ikenos-teamos ikenos-teamos=$DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG"
                sh "kubectl -n ikenos-teamos scale deployment ikenos-teamos-canary --replicas=$CANARY_REPLICAS"
              }
            }
          }
        }
      }
    }
}