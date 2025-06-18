pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'JDK17'             // 📦 Java Development Kit version 17
        maven 'MAVEN3.9'        // 📦 Maven version 3.9
    }

    environment {
        SONARQUBE_ENV = 'sonarqube'         // ✅ NOM visible défini dans Jenkins > Configure System > SonarQube servers
        SONAR_TOKEN_ID = 'sonarqube-token'  // ✅ ID d'un "Secret Text" dans les credentials Jenkins
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

        stage('📊 Code Coverage') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('📊 JaCoCo Report') {
            steps {
                jacoco execPattern: 'target/jacoco.exec', classPattern: 'target/classes', sourcePattern: 'src/main/java', exclusionPattern: '**/test/**'
            }
            
            post {
                success {
                    echo "✅ JaCoCo report generated successfully."
                }
                failure {
                    echo "❌ JaCoCo report generation failed."
                }
            }
        }

        stage('📊 Surefire Report') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('📊 SpotBugs Analysis') {
            steps {
                sh 'mvn spotbugs:check'
            }
        }

        stage('📊 PMD Analysis') {
            steps {
                sh 'mvn pmd:check'
            }
        }

        stage('📊 FindBugs Analysis') {
            steps {
                sh 'mvn findbugs:check'
            }
        }

        stage('📊 Checkstyle Report') {
            steps {
                sh 'mvn checkstyle:check'
            }
        }

        stage('📊 PMD Report') {
            steps {
                pmd parser: 'maven', pattern: '**/target/pmd.xml', ruleSet: 'rulesets/java/basic.xml'
            }
        }

        stage('📊 SpotBugs Report') {
            steps {
                spotBugs pattern: '**/target/spotbugsXml.xml', trendPattern: '**/target/spotbugsXml.xml'
            }
        }

        stage('📊 FindBugs Report') {
            steps {
                findbugs pattern: '**/target/findbugsXml.xml', trendPattern: '**/target/findbugsXml.xml'
            }
        }

        stage('📊 Checkstyle Result') {
            steps {
                checkstyle pattern: '**/target/checkstyle-result.xml', trendPattern: '**/target/checkstyle-result.xml'
            }
        }

        stage('📊 PMD Result') {
            steps {
                pmd pattern: '**/target/pmd.xml', trendPattern: '**/target/pmd.xml'
            }
        }

        stage('📊 SpotBugs Result') {
            steps {
                spotBugs pattern: '**/target/spotbugsXml.xml', trendPattern: '**/target/spotbugsXml.xml'
            }
        }

        stage('📊 FindBugs Result') {
            steps {
                findbugs pattern: '**/target/findbugsXml.xml', trendPattern: '**/target/findbugsXml.xml'
            }
        }


        stage('🔍 SonarQube Environment') {
            steps {
                script {
                    echo "Using SonarQube environment: ${SONARQUBE_ENV}"
                }
            }
        }


        stage('🧹 Checkstyle Analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }

        stage('🔍 Debug Token') {
            steps {
                withCredentials([string(credentialsId: SONAR_TOKEN_ID, variable: 'SONAR_TOKEN')]) {
                    sh 'echo "Token starts with: ${SONAR_TOKEN:0:8}"'
                }
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
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/jacoco/jacoco.xml \
                            -Dsonar.java.binaries=target/classes \
                            -Dsonar.junit.reportsPath=target/surefire-reports \
                            -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml \
                            -Dsonar.host.url=http://localhost:9000 \
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
