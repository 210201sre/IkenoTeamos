  pipeline {
  
      agent {
        kubernetes {
          label 'build-agent'
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
              image: ikenoxamos/jenkins-slave:latest
              workingDir: /home/jenkins
              env:
              - name: DOCKER_HOST
                value: tcp://localhost:2375
              resources:
                requests:
                  memory: "1900Mi"
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
                  memory: "1900Mi"
                  cpu: "0.3"
                limits:
                  memory: "2000Mi"
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
    //test commennt
    environment{
      DOCKER_IMAGE_NAME = 'huskerhayes/ikenos-teamos'
    }

    stages{
      stage('Build Docker Image'){
        steps {
          // sh 'docker build -t $DOCKER_IMAGE_NAME .'
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
              // app.push("${env.BUILD_NUMBER}")
              // app.push("${env.GIT_COMMIT}")
            }
          }
        }
      }

      stage('Kubernetes Deployment'){
        steps{
          script{
            container('kubectl'){
              withKubeConfig([credentialsId: 'kubeconfig']){
                sh "aws eks update-kubeconfig --name matt-oberlies-sre-943"
                sh "kubectl -n ikenos-teamos get all"
                sh 'kubectl -n ikenos-teamos set image deployment ikenos-teamos ikenos-teamos=$DOCKER_IMAGE_NAME'
              }
            }
          }
        }
      }
    }
}