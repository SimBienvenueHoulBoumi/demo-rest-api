pipeline {
    agent any

    environment {
        // Repo local Maven pour éviter de polluer globalement
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
        GITHUB_TOKEN = credentials('github-token')
    }

    tools {
        jdk 'jdk'
        maven 'maven'
    }

    stages {
        stage('⚙️ Préparation') {
            steps {
                script {
                    // Création du repo Maven local si besoin
                    sh 'mkdir -p .m2/repository'

                    if (fileExists('mvnw')) {
                        echo "✅ Maven Wrapper détecté !"
                        sh 'chmod +x mvnw'
                        env.MAVEN_CMD = './mvnw'
                    } else {
                        echo "⚠️ Pas de mvnw, fallback sur Maven global"
                        env.MAVEN_CMD = 'mvn'
                    }

                    // Petit test de version pour rassurer tout le monde
                    sh "${env.MAVEN_CMD} --version"
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
                echo "⚙️ Compilation en cours..."
                sh "${env.MAVEN_CMD} clean compile"
            }
        }

        stage('🧪 Tests') {
            steps {
                echo "🧪 Tests unitaires..."
                sh "${env.MAVEN_CMD} test"
            }
        }

        stage('📦 Build') {
            steps {
                echo "📦 Packaging JAR..."
                sh "${env.MAVEN_CMD} package"
            }
        }

        stage('📂 Archivage') {
            steps {
                echo "📂 Archivage..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline terminée avec succès 🎉'
        }
        failure {
            echo '❌ Pipeline échouée. Plonge dans les logs, apprenti.'
        }
        always {
            echo '📌 Fin du pipeline.'
        }
    }
}
