pipeline {
    agent any

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
        GITHUB_TOKEN = credentials('github-token')
        USE_WRAPPER = '' // sera défini dynamiquement
    }

    tools {
        jdk 'java-17'
        maven 'Maven3'
    }

    stages {
        stage('🔍 Détection Maven') {
            steps {
                echo "🧭 Détection du build tool (mvn ou mvnw)..."
                script {
                    if (fileExists('mvnw')) {
                        echo "✅ Maven Wrapper détecté !"
                        sh 'chmod +x mvnw'
                        env.USE_WRAPPER = './mvnw'
                    } else {
                        echo "⚠️ Pas de mvnw détecté, fallback sur Maven global"
                        env.USE_WRAPPER = 'mvn'
                    }
                }
            }
        }

        stage('📁 Diagnostic du workspace') {
            steps {
                echo "🕵️‍♂️ Listing des fichiers..."
                sh 'ls -al'
            }
        }

        stage('🔧 Compilation') {
            steps {
                echo "⚙️ Compilation du projet Spring Boot..."
                sh "${env.USE_WRAPPER} clean compile"
            }
        }

        stage('🧪 Tests') {
            steps {
                echo "🧪 Exécution des tests unitaires..."
                sh "${env.USE_WRAPPER} test"
            }
        }

        stage('📦 Build') {
            steps {
                echo "📦 Construction de l’artefact JAR..."
                sh "${env.USE_WRAPPER} package"
            }
        }

        stage('📂 Archivage') {
            steps {
                echo "📂 Archivage de l’artefact généré..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline exécutée avec succès 🎉'
        }
        failure {
            echo '❌ Une erreur est survenue. Check tes logs, padawan.'
        }
        always {
            echo '📌 Fin du pipeline.'
        }
    }
}
