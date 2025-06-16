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

            stage('site') {
                steps {
                    sh 'mvn clean generate-sources generate-test-sources site'
                }
            }

            stage('Checkstyle Analysis') {
                steps {
                        /*
                            Commande Maven:
                            checkstyle:checkstyle : exécute l'analyse Checkstyle
                            Génère un rapport dans target/checkstyle-result.xml
                        */
                    sh 'mvn checkstyle:checkstyle'
                }
             }

    }
}

