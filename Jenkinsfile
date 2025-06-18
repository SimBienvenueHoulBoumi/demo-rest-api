pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'jdk'             // 📦 Java Development Kit version 17
        maven 'maven'        // 📦 Maven version 3.9
    }

    environment {
        SONARSERVER = 'sonarserver'         // ✅ NOM visible défini dans Jenkins > Configure System > SonarQube servers
        SONARSCANNER = 'sonarscanner'       // ✅ NOM visible défini dans Jenkins > Configure System > SonarQube scanners
        SNYK = 'SnykCLI'  // ✅ NOM visible défini dans Jenkins > Configure System > Tools > Snyk installations
        SNYK_TOKEN = 'Snyk'  // l'identifiant du token Snyk doit être configuré dans Jenkins Credentials
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
                sh 'chmod +x ./mvn'
                snykSecurity (
                    severity: 'medium',                                                               // Seuil minimum de sévérité pour les vulnérabilités
                    snykInstallation: "${SNYK}",                                                      // Installation Snyk configurée dans Jenkins
                    snykTokenId: "${SNYK_TOKEN}",                                                     // Identifiant du token Snyk stocké dans Jenkins Credentials
                    targetFile: "pom.xml",                                                            // Fichier de build Maven à analyser
                    monitorProjectOnBuild: true,                                                      // Active le monitoring continu du projet dans Snyk
                    failOnIssues: false,                                                              // Autoriser le pipeline à continuer avec un avertissement
                    additionalArguments: '--report --format=html --report-file=snyk_report.html' 
                    )
            }
        }

    }   
}
 