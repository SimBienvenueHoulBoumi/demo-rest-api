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
        stage('📥 Checkout') {
            steps {
                echo "🔄 Clonage du repo GitHub (branche main)..."
                git branch: 'main',
                    credentialsId: 'github-token',
                    url: 'https://github.com/SimBienvenueHoulBoumi/demo-rest-api-rest-api.git'
            }
        }

        stage('🔧 Compilation') {
            steps {
                dir('demo-rest-api') {
                    echo "⚙️ Compilation du projet Spring Boot..."
                    sh './mvnw clean compile'
                }    
            }
        }

        stage('🧪 Tests') {
            steps {
                dir('demo-rest-api') {
                    echo "🧪 Exécution des tests..."
                    sh './mvnw test'
                }
            }
        }

        stage('📦 Build') {
            steps {
                dir('demo-rest-api') {
                    echo "📦 Création de l’artefact JAR..."
                    sh './mvnw clean package'
                }
            }
        }

        stage('📂 Archive') {
            steps {
                dir('demo-rest-api') {
                    echo "📂 Archivage de l’artefact JAR..."
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        /**
        
        stage('🧬 Swagger (optionnel)') {
            when {
                expression { fileExists('src/main/java/com/example/demo-rest-api/config/SwaggerConfig.java') }
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
        
        **/
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
