pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'JDK17'             // 📦 Java Development Kit version 17
        maven 'MAVEN3.9'        // 📦 Maven version 3.9
    }

    environment {
        SONARQUBE_ENV = 'sonarqube-token'   // 🔐 Nom du serveur SonarQube configuré dans Jenkins
        SONAR_TOKEN_ID = 'squ_1518063ed11325d73f160a32d01e1489b88ce1f1'
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

        stage('📄 Site Maven') {
            steps {
                sh 'mvn site'
            }
        }

        stage('🧹 Checkstyle Analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }

        stage('🔍 Debug Token') {
            steps {
                withCredentials([string(credentialsId: "${SONAR_TOKEN_ID}", variable: 'SONAR_TOKEN')]) {
                    sh 'echo "Token starts with: ${SONAR_TOKEN:0:8}"'
                }
            }
        }

        stage('🔍 SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: "${SONAR_TOKEN_ID}", variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv("${SONARQUBE_ENV}") {
                        sh '''
                            mvn sonar:sonar \
                            -Dsonar.projectKey=demo-rest-api \
                            -Dsonar.projectName=demo-rest-api \
                            -Dsonar.projectVersion=0.0.1 \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/jacoco/jacoco.xml \
                            -Dsonar.java.binaries=target/classes \
                            -Dsonar.junit.reportsPath=target/surefire-reports \
                            -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml \
                            -Dsonar.token=${SONAR_TOKEN}
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
    }
}
