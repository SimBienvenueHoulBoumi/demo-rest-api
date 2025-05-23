pipeline {
    agent { label 'agent-distant' }


    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
        GITHUB_TOKEN = credentials('github-token')
    }

    tools {
        jdk 'java-17'
        maven 'Maven3'
    }

    stages {
        stage('📥 Checkout') {
            steps {
                echo "🔄 Clonage du repo GitHub (branche main)..."
                git branch: 'main',
                    credentialsId: 'github-token',
                    url: 'https://github.com/SimBienvenueHoulBoumi/demo-rest-api.git'
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
                echo "🧪 Tests unitaires et d’intégration..."
                sh './mvnw test'
            }
        }

        stage('📦 Build') {
            steps {
                echo "📦 Construction du JAR..."
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('📂 Archive') {
            steps {
                echo "📥 Archivage de l’artefact..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('🧬 Swagger (optionnel)') {
            when {
                expression { fileExists('src/main/java/com/example/demo/config/SwaggerConfig.java') }
            }
            steps {
                echo "🔍 Swagger détecté, test de l’interface..."
                sh """
                    nohup java -jar target/*.jar & 
                    sleep 10
                    curl -s --fail http://localhost:8080/swagger-ui/index.html || echo 'Swagger indisponible... 😿'
                """
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
