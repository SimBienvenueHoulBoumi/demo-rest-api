pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'jdk'             // 📦 Java Development Kit version 17
        maven 'maven'         // 📦 Maven version 3.9
    }

    environment {
        SONARSERVER = 'sonarserver'         // ✅ Jenkins > Configure System > SonarQube servers
        SONARSCANNER = 'sonarscanner'       // ✅ Jenkins > Configure System > SonarQube scanners
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

        stage('📊 SonarQube Analysis') {
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

        stage('📥 Install Snyk CLI and snyk-to-html') {
            steps {
                sh '''
                    curl -sL https://static.snyk.io/cli/latest/snyk-linux -o snyk
                    chmod +x snyk
                    curl -sL https://github.com/snyk/snyk-to-html/releases/latest/download/snyk-to-html -o snyk-to-html
                    chmod +x snyk-to-html
                '''
            }
        }

        stage('📥 Install Snyk CLI and snyk-to-html') {
            steps {
                sh '''
                    # Détection de l'OS
                    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
                        echo "➡ Téléchargement de Snyk pour Linux"
                        curl -sL https://static.snyk.io/cli/latest/snyk-linux -o snyk
                    elif [[ "$OSTYPE" == "darwin"* ]]; then
                        echo "➡ Téléchargement de Snyk pour macOS"
                        curl -sL https://static.snyk.io/cli/latest/snyk-macos -o snyk
                    else
                        echo "❌ OS non supporté par ce script"
                        exit 1
                    fi

                    chmod +x snyk

                    echo "➡ Téléchargement de snyk-to-html"
                    curl -sL https://github.com/snyk/snyk-to-html/releases/latest/download/snyk-to-html -o snyk-to-html
                    chmod +x snyk-to-html
                '''
            }
        }


    }
}
