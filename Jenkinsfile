pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'JDK17'
        maven 'MAVEN3.9'
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
                // 🧹 Compile le projet et nettoie les anciens builds
                sh 'mvn clean install -DskipTests'
            }

            post {
                success {
                    echo "Build réussi - Archivage des artefacts..."
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
                // 🌐 Génère la documentation Maven dans target/site
                sh 'mvn clean generate-sources generate-test-sources site'
            }
        }

        stage('🧹 Checkstyle Analysis') {
            steps {
                // 📋 Analyse de style de code
                sh 'mvn checkstyle:checkstyle'
            }
        }

        stage('🔍 Scan SonarQube') {
            steps {
                // 🔍 Analyse de la qualité du code avec SonarQube
                sh 'mvn clean org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar'
            }
        }

    }

}

