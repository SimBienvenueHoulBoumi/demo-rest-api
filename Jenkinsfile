pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'JDK17'
        maven 'MAVEN3.9'
    }

    environment {
        JAR_NAME = 'demo-0.0.1-SNAPSHOT.jar'
        TARGET_DIR = 'target'
    }

    triggers {
     githubPush()
    // gitlabPush() // Si GitLab
    }


    stages {

        stage('Build') {
            steps {
                echo "⛏️ Compilation..."
                sh 'mvn clean install -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: "${TARGET_DIR}/*.jar", fingerprint: true
                }
            }
        }

        stage('Unit Tests') {
            steps {
                echo "🧪 Tests unitaires..."
                sh 'ls -R target'
                sh 'mvn test'
            }
            post {
                always {
                    junit "${TARGET_DIR}/surefire-reports/*.xml"
                }
            }
        }

        stage('Integration Tests') {
            steps {
                echo "🧬 Tests d’intégration..."
                sh 'mvn verify -DskipUnitTests=true'
            }
            post {
                always {
                    junit "${TARGET_DIR}/failsafe-reports/*.xml"
                }
            }
        }

        stage('Security Scan') {
            steps {
                echo "🛡️ Analyse de dépendances (OWASP)..."
                sh 'mvn org.owasp:dependency-check-maven:check'
            }
            post {
                always {
                    publishHTML([
                        reportDir: "${TARGET_DIR}",
                        reportFiles: 'dependency-check-report.html',
                        reportName: 'OWASP Dependency Check'
                    ])
                }
            }
        }

        stage('Checkstyle Analysis') {
            steps {
                echo "🔍 Checkstyle..."
                sh 'mvn checkstyle:checkstyle'
            }
            post {
                always {
                    recordIssues tools: [checkStyle(pattern: "${TARGET_DIR}/checkstyle-result.xml")]
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo "🐳 Docker build multi-stage..."
                sh 'docker build -t springboot-app:latest .'
            }
        }

        // Optionnel : si tu veux un sonar
        /*
        stage('SonarQube Analysis') {
            steps {
                echo "📡 Envoi vers SonarQube..."
                withSonarQubeEnv('MySonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        */
    }

    post {
        always {
            echo '🏁 Pipeline terminé.'
        }
        success {
            echo '🎉 Succès complet du pipeline !'
        }
        failure {
            echo '💥 Le pipeline a échoué...'
        }
    }
}
