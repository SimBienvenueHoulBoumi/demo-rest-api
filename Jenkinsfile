pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'jdk'             // 📦 Java Development Kit version 17
        maven 'maven'        // 📦 Maven version 3.9
    }

    environment {
        SONARSERVER = 'sonarserver'         // ✅ NOM visible défini dans Jenkins > Configure System > SonarQube servers
        SONARSCANNER = 'sonarscanner'       // ✅ NOM visible défini dans Jenkins > Configure System > SonarQube scanners
        
        SNYK_TOKEN = 'Snyk'  // l'identifiant du token Snyk doit être configuré dans Jenkins Credentials
        SNYK = 'SnykCLI'  // ✅ NOM visible défini dans Jenkins > Configure System > Tools > Snyk installations
    }

    stages {

        stage('📥 Checkout') {
            steps {
                checkout scm
            }
        }

        stage('🔧 Build') {
            steps {
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
                sh 'mvn test'
            }
        }

        stage('🧹 Checkstyle Analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }


        stage('SonarQube analysis') {
            environment {

                scannerHome = tool "${SONARSCANNER}"
            }
            steps {
                withSonarQubeEnv("${SONARSERVER}") {

                    sh """${scannerHome}/bin/sonar-scanner \
                    -Dsonar.projectKey=demo-rest-api \
                    -Dsonar.projectName=demo-rest-api \
                    -Dsonar.projectVersion=0.0.1 \
                    -Dsonar.sources=src/ \
                    -Dsonar.java.binaries=target/test-classes/simdev/demo/services \
                    -Dsonar.junit.reportsPath=target/surefire-reports/ \
                    -Dsonar.coverage.jacoco.xmlReportPaths=target/jacoco/jacoco.xml \
                    -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml"""
                } 
            }
        }


        stage('Snyk Dependency Scan') {
            steps {
                withCredentials([string(credentialsId: 'Snyk', variable: 'SNYK_TOKEN')]) {
                    snykSecurity (
                        severity: 'medium',
                        snykInstallation: "${SNYK}",
                        snykTokenId: 'SNYK_TOKEN',
                        targetFile: "pom.xml",
                        monitorProjectOnBuild: true,
                        failOnIssues: false,
                        additionalArguments: '--report --format=html --report-file=snyk_report.html'
                    )
                }
            }
        }

        stage('🔒 Snyk via CLI') {
            steps {
                withCredentials([string(credentialsId: 'SNYK_TOKEN', variable: 'SNYK_TOKEN')]) {
                    sh """
                        snyk auth \$SNYK_TOKEN
                        snyk test --severity-threshold=medium --file=pom.xml || true
                    """
                }
            }
        }

    }
}
 