pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'JDK17'
        maven 'MAVEN3.9'
    }

    environment {
        // 🔐 Jeton SonarQube défini dans Jenkins (Manage Jenkins > Credentials > jenkins-sonar)
        SONARSERVER = 'sonarserver'
        SONARSCANNER = 'sonarscaner'
    }

    stages {

        stage('📥 Checkout') {
            steps {
                // 📥 Clone le code source depuis le dépôt Git lié au job Jenkins
                checkout scm
            }
        }

        stage('🔧 Build') {
            steps {
                // 🧹 Compile le projet et génère les fichiers `.class` nécessaires à SonarQube
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
                // 🧪 Lance les tests unitaires
                sh 'mvn test'
            }
        }

        stage('📄 Site Maven') {
            steps {
                // 🌐 Génère la documentation Maven (HTML) dans `target/site`
                sh 'mvn site'
            }
        }

        stage('🧹 Checkstyle Analysis') {
            steps {
                // 📋 Analyse Checkstyle → Résultats dans `target/checkstyle-result.xml`
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
                    -Dsonar.java.binaries=target/test-classes/simdev/demo/services/unit \
                    -Dsonar.junit.reportsPath=target/surefire-reports/ \
                    -Dsonar.coverage.jacoco.xmlReportPaths=target/jacoco/jacoco.xml \
                    -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml"""
                } 
            }
        }

    }
}
