pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'jdk'             // 📦 Java Development Kit version 17
        maven 'maven'        // 📦 Maven version 3.9
    }

    environment {
        SONARSERVER = 'sonarserver'         // ✅ NOM visible défini dans Jenkins > Configure System > SonarQube servers
        SONARSCANNER = 'sonarscanner'       // ✅ NOM visible défini dans Jenkins > Configure System > SonarQube scanners
    }

    stages {

        stage('📥 Checkout') {
            steps {
                checkout scm
            }
        }

        stage('🔧 Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
            post {
                success {
                    echo "✅ Build réussi - Archivage des artefacts..."
                    archiveArtifacts artifacts: 'target/*.jar'
                }
            }
        }

        stage('🧪 Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('🧹 Checkstyle Analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }

        stage('SonarQube analysis') {
                    environment {

                        scannerHome = tool "${SONARSCANNER}"
                    }
                    steps {
                        withSonarQubeEnv("${SONARSERVER}") {

                            sh """${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectKey=demo-rest-api \
                            -Dsonar.projectName=demo-rest-api \
                            -Dsonar.projectVersion=0.0.1 \
                            -Dsonar.sources=src/ \
                            -Dsonar.java.binaries=target/test-classes/simdev/demo/services \
                            -Dsonar.junit.reportsPath=target/surefire-reports/ \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/jacoco/jacoco.xml \
                            -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml"""
                        } 
                    }
                }


            stage('✅ Quality Gate') {
                    steps {
                        timeout(time: 5, unit: 'MINUTES') { // ⬅️ passe à 5 min par exemple
                            waitForQualityGate abortPipeline: true
                        }
                    }
                }


                stage('📦 Package') {
                    steps {
                        sh 'mvn package -DskipTests'
                    }
                }

                stage('📁 Archive Artifacts') {
                    steps {
                        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                    }
                }

                /*
                stage('🐳 Docker Build & Push') {
                    steps {
                        sh '''
                            docker build -t $DOCKER_IMAGE:latest .
                            docker push $DOCKER_IMAGE:latest
                        '''
                    }
                }

                stage('🧪 Integration Tests') {
                    steps {
                        sh 'echo "Run integration tests here (e.g. with Testcontainers)"'
                    }
                }

                stage('🧪 E2E Tests') {
                    steps {
                        sh 'echo "Run E2E tests here (e.g. with Postman CLI / Cypress)"'
                    }
                }

                stage('🌍 Deploy to Staging') {
                    steps {
                        sh 'echo "Deploy application to staging environment here"'
                    }
                }

                stage('📊 Performance Tests') {
                    steps {
                        sh 'echo "Run performance tests here (e.g. with Gatling or JMeter)"'
                    }
                }

                stage('🔒 Security Scan') {
                    steps {
                        sh '''
                            echo "Run OWASP Dependency-Check or Snyk here"
                            # Exemple OWASP :
                            # dependency-check.sh --project demo-rest-api --scan . --format HTML
                        '''
                    }
                }

                stage('✅ Manual Approval') {
                    steps {
                        input message: 'Proceed to production?', ok: 'Yes, deploy'
                    }
                }

                stage('🚀 Deploy to Production') {
                    steps {
                        sh 'echo "Deploy to production here (e.g. Helm/K8s or Docker Swarm)"'
                    }
                }

                stage('📣 Notification') {
                    steps {
                        echo "Pipeline complete. Notify team (e.g. via Slack/Email)."
                    }
                }
                */
            }
    }
