pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'JDK17'
        maven 'MAVEN3.9'
    }

    environment {
        // 🔐 Jeton SonarQube défini dans Jenkins (Manage Jenkins > Credentials > jenkins-sonar)
        SONAR_TOKEN = credentials('jenkins-sonar')
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
                // 🧹 Compile le projet et génère les fichiers `.class` nécessaires à SonarQube
                sh 'mvn clean install -DskipTests'
            }

            post {
                success {
                    echo "✅ Build réussi - Archivage des artefacts..."
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

        stage('📄 Site Maven') {
            steps {
                // 🌐 Génère la documentation Maven (HTML) dans `target/site`
                sh 'mvn site'
            }
        }

        stage('🧹 Checkstyle Analysis') {
            steps {
                // 📋 Analyse Checkstyle → Résultats dans `target/checkstyle-result.xml`
                sh 'mvn checkstyle:checkstyle'
            }
        }

        stage('🔍 Scan SonarQube') {
            steps {
                // 🔍 Analyse de la qualité du code avec SonarQube, en précisant le dossier de classes compilées
                sh '''
                    mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.token=$SONAR_TOKEN
                '''
            }
        }

    }
}
