pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'JDK17'             // 📦 Java Development Kit version 17 (déclaré dans Jenkins)
        maven 'MAVEN3.9'        // 📦 Maven version 3.9 (déclaré dans Jenkins)
    }

    environment {
        SONARQUBE_ENV = 'sonarserver'   // 🔐 Nom du serveur SonarQube configuré dans Jenkins
        SONARSCANNER = 'sonarscaner'    // 🔧 Nom du scanner SonarQube configuré dans Jenkins
    }

    stages {

        stage('📥 Checkout') {
            steps {
                // ⬇️ Récupération du code source depuis le SCM (Git, etc.)
                checkout scm
            }
        }

        stage('🔧 Build') {
            steps {
                // 🧱 Compilation du projet Java sans exécuter les tests
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
                // 🧪 Exécution des tests unitaires
                sh 'mvn test'
            }
        }

        stage('📄 Site Maven') {
            steps {
                // 📚 Génération de la documentation HTML du projet (target/site)
                sh 'mvn site'
            }
        }

        stage('🧹 Checkstyle Analysis') {
            steps {
                // 🧼 Analyse statique du code avec Checkstyle
                sh 'mvn checkstyle:checkstyle'
            }
        }

        stage('🔍 SonarQube Analysis') {
            steps {
                // 📡 Envoi des résultats vers SonarQube via le plugin Maven
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    sh """
                        mvn sonar:sonar \
                          -Dsonar.projectKey=demo-rest-api \
                          -Dsonar.projectName=demo-rest-api \
                          -Dsonar.projectVersion=0.0.1 \
                          -Dsonar.coverage.jacoco.xmlReportPaths=target/jacoco/jacoco.xml \
                          -Dsonar.java.binaries=target/classes \
                          -Dsonar.junit.reportsPath=target/surefire-reports \
                          -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml
                    """
                }
            }
        }

        stage('✅ Quality Gate') {
            steps {
                // ⏳ Attend le retour du Quality Gate (fail ou pass)
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
