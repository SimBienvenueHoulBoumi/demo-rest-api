pipeline {
    agent any
    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
        GITHUB_TOKEN = credentials('github-token')
    }
    tools {
        jdk 'java-17'
        maven 'Maven3'
    }

    stages {
        stage('🔧 Compilation') {
            steps {
                echo "⚙️ Compilation du projet Spring Boot..."
                sh './mvnw clean compile'
            }
        }

        stage('🧪 Tests') {
            steps {
                echo "🧪 Exécution des tests..."
                sh './mvnw test'
            }
        }

        stage('📦 Build') {
            steps {
                echo "📦 Création de l’artefact JAR..."
                sh './mvnw clean package'
            }
        }

        stage('📂 Archive') {
            steps {
                echo "📂 Archivage de l’artefact JAR..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline réussie !'
        }
        failure {
            echo '❌ Une étape a échoué. Check les logs.'
        }
    }
}
