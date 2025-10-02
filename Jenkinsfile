pipeline {
    agent any

    tools {
        jdk 'jdk21'
        maven 'maven3'
    }

    environment {
        SCANNER_HOME = tool 'sonar-scanner'
    }

    stages {
        stage('Git Checkout') {
            steps {
               git branch: 'main', credentialsId: 'git-cred', url: 'https://github.com/The-Cloud-Guy/task.git'
            }
        }

        stage('Compile') {
            steps {
                sh "mvn compile"
            }
        }

        stage('Test') {
            steps {
                sh "mvn test"
            }
        }

        stage('File System Scan') {
            steps {
                sh "trivy fs --format table -o trivy-fs-report.html ."
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=Task \
                           -Dsonar.projectKey=Task \
                           -Dsonar.java.binaries=. '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
                }
            }
        }

        stage('Build') {
            steps {
               sh "mvn package"
            }
        }

        stage('Publish To Nexus') {
            steps {
               withMaven(globalMavenSettingsConfig: 'global-settings', jdk: 'jdk21', maven: 'maven3', mavenSettingsConfig: '', traceability: true) {
                    sh "mvn deploy"
                }
            }
        }

        stage('Build & Tag Docker Image') {
            steps {
               script {
                   withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                        sh "docker build -t thecloudguyn/task:latest ."
                    }
               }
            }
        }

        stage('Docker Image Scan') {
            steps {
                sh "trivy image --format table -o trivy-image-report.html thecloudguyn/task:latest"
            }
        }

        stage('Push Docker Image') {
            steps {
               script {
                   withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                        sh "docker push thecloudguyn/task:latest"
                    }
               }
            }
        }

        stage('Update Helm Chart Version') {
            steps {
                script {
                    sh '''
                        NEW_VERSION=$(date +'%Y.%m.%d-%H.%M')
                        sed -i "s/^version:.*/version: ${NEW_VERSION}/" helm-Chart/Chart.yaml
                        echo "Updated Helm chart version to ${NEW_VERSION}"
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    withKubeConfig(credentialsId: 'k8s') {
                        sh '''
                            helm upgrade --install task ./helm-Chart \
                            --set image.repository=thecloudguyn/task \
                            --set image.tag=latest
                        '''
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                def recipientList = "recipient@gmail.com"
                def subject = "${currentBuild.currentResult}: Job ${env.JOB_NAME} Build ${env.BUILD_NUMBER}"
                def body = """<p>Job Name: ${env.JOB_NAME}</p>
                              <p>Build Number: ${env.BUILD_NUMBER}</p>
                              <p>Build Status: ${currentBuild.currentResult}</p>
                              <p>More details at: <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></p>"""

                emailext (
                    to: recipientList,
                    subject: subject,
                    body: body,
                    mimeType: 'text/html',
                    attachmentsPattern: 'trivy-fs-report.html,trivy-image-report.html'
                )
            }
        }
    }
}