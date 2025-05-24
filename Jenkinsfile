/*

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
        stage('🔍 Détection Maven') {
            steps {
                script {
                    def useWrapper = ''
                    if (fileExists('mvnw')) {
                        echo "✅ Maven Wrapper détecté !"
                        sh 'chmod +x mvnw'
                        useWrapper = './mvnw'
                    } else {
                        echo "⚠️ Pas de mvnw détecté, fallback sur Maven global"
                        useWrapper = 'mvn'
                    }

                    // On stocke dans l’environnement Jenkins pour les étapes suivantes
                    env.USE_WRAPPER = useWrapper
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

*/

pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                echo '🚀 Le pipeline démarre enfin !'
                sh 'echo Hello world'
            }
        }
    }
}
