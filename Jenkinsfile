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
                    sh 'mvn clean compile  && mvn clean package -DskipTests'
                }

                post {
                    success {
                        echo "Build réussi - Archivage des artefacts..."
                        // Archive tous les fichiers .jar trouvés dans le sous-répertoire target de user-service
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

    }
}

