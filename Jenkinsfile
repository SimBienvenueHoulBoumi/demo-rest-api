pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'JDK17'
        maven 'MAVEN3.9'
    }
    
    stages {

        stage('🔧 Build') {
            steps {
                sh 'mvn clean compile -DskipTests'
            }


            steps {
                sh 'ls -al'
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
                sh 'mvn test'
            }
        }
        
    }
}
