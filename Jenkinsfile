pipeline {
    agent any

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
    }

    tools {
        maven 'Maven3'
    }

    stages {
        stage('📥 Checkout') {
            steps {
                echo "🔄 Récupération du code source..."
                git branch: 'main', url: 'https://github.com/SimBienvenueHoulBoumi/demo-rest-api.git'
            }
        }

        stage('🔧 Compilation') {
            steps {
                echo "⚙️ Compilation du projet Spring Boot..."
                sh './mvnw clean compile'
            }
        }

        stage('🧪 Tests') {
            steps {
                echo "🧪 Exécution des tests unitaires et d'intégration..."
                sh './mvnw test'
            }
        }

        stage('📦 Build & Package') {
            steps {
                echo "📦 Construction du JAR Spring Boot..."
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('📂 Archive') {
            steps {
                echo "🎯 Archivage de l'artefact généré..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('🧬 Swagger Check (optionnel)') {
            when {
                expression { fileExists('src/main/java/com/example/demo/config/SwaggerConfig.java') }
            }
            steps {
                echo "🔍 Swagger détecté, vérification de la doc..."
                sh "curl -s --fail http://localhost:5000/swagger-ui/index.html || echo 'Swagger UI indisponible (probablement hors contexte Jenkins)'"
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline terminée avec succès ! 🎉'
        }
        failure {
            echo '❌ Une étape a échoué. Consulte les logs Jenkins pour plus d’info 🪵'
        }
    }
}
