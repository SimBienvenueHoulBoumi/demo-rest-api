pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'JDK17'
        maven 'MAVEN3.9'
    }
    
    stages {

        stage('🔧 Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }

            post {
                success {
                    echo "Build réussi - Archivage des artefacts..."
                    // Archive tous les fichiers .jar trouvés dans le sous-répertoire target de user-service
                    archiveArtifacts artifacts: 'target/demo-O.O.1-SNAPSHOT.jar'
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
