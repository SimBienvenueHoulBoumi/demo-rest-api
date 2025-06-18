pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'JDK17'
        maven 'MAVEN3.9'
    }

    environment {
        SONARQUBE_ENV = 'sonarqube'
        SONAR_TOKEN_ID = 'sonarqube-token'
        DOCKER_IMAGE = 'registry.simdev.com/demo-rest-api'
    }

    stages {
        stage('📥 Checkout') {
            steps {
                checkout scm
            }
        }

        stage('🔍 Static Code Analysis') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('🔧 Build') {
            steps {
                sh 'mvn install -DskipTests'
            }
        }

        stage('🧪 Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('📄 Code Coverage') {
            steps {
                sh 'mvn clean verify jacoco:report'
            }
        }

        stage('🧹 Code Quality / Checkstyle') {
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }

        stage('🔍 SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: SONAR_TOKEN_ID, variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv(SONARQUBE_ENV) {
                        sh '''
                            mvn sonar:sonar \
                            -Dsonar.projectKey=demo-rest-api \
                            -Dsonar.projectName=demo-rest-api \
                            -Dsonar.projectVersion=0.0.1 \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                            -Dsonar.java.binaries=target/classes \
                            -Dsonar.junit.reportsPath=target/surefire-reports \
                            -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml \
                            -Dsonar.token=$SONAR_TOKEN
                        '''
                    }
                }
            }
        }

        stage('✅ Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
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
