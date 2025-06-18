/**
 * 🛠️ Pipeline Jenkins complet pour un projet Java Spring Boot.
 */
pipeline {
    agent { label 'jenkins-agent' }

    tools {
        jdk 'jdk'
        maven 'maven'
    }

    environment {
        SONARSERVER = 'sonarserver'
        SONARSCANNER = 'sonarscanner'
        SNYK = 'snyk'
        BUILD_ID = "0.0.1"
        MAVEN_OPTS = "-Xmx1024m"
        TRIVY_URL = 'http://localhost:4954/scan'
    }

    options {
        skipDefaultCheckout(false)
        buildDiscarder(logRotator(numToKeepStr: '5'))
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
    }

    stages {

        stage('📥 Checkout') {
            steps {
                checkout scm
            }
        }

        stage('🛠️ Générer Maven Wrapper si absent') {
            steps {
                sh '''
                    if [ ! -f "./mvnw" ] || [ ! -f "./.mvn/wrapper/maven-wrapper.properties" ]; then
                        echo "➡ Maven Wrapper manquant. Génération..."
                        mvn -N io.takari:maven:wrapper
                    else
                        echo "✅ Maven Wrapper déjà présent."
                    fi
                '''
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

        stage('📊 SonarQube Analysis') {
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
                snykSecurity (
                    severity: 'high',
                    snykInstallation: "${SNYK}",
                    snykTokenId: 'snyk-token',
                    targetFile: 'pom.xml',
                    monitorProjectOnBuild: true,
                    failOnIssues: false,
                    additionalArguments: '--report --format=html --report-file=snyk_report.html'
                )
            }
        }

        stage('📦 Build Docker Image') {
            steps {
                script {
                    def imageName = "demo-rest-api:${BUILD_ID}"
                    sh "docker build -t ${imageName} ."
                    env.DOCKER_IMAGE = imageName
                    echo "Docker image built: ${imageName}"
                }
            }
        }

        stage('🔍 Scan Trivy') {
            steps {
                script {
                    def trivyUrl = env.TRIVY_URL
                    def imageName = env.DOCKER_IMAGE

                    echo "⏳ Attente que Trivy soit prêt..."
                    sh "sleep 10"

                    echo "📡 Scan de l'image ${imageName} avec Trivy..."
                    sh """
                        curl -s -X POST ${trivyUrl} \\
                        -H 'Content-Type: application/json' \\
                        -d '{
                            "image_name": "${imageName}",
                            "scan_type": "image",
                            "vuln_type": ["os", "library"],
                            "severity": ["CRITICAL", "HIGH", "MEDIUM"]
                        }' > trivy-report.json
                    """

                    echo "📄 Rapport Trivy généré avec succès"
                    archiveArtifacts artifacts: 'trivy-report.json'
                }
            }
        }
    }
}
