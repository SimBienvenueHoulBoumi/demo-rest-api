/**
 * 🛠️ Pipeline Jenkins complet pour un projet Java Spring Boot.
 * Ce pipeline est exécuté sur un agent nommé 'jenkins-agent' et utilise Maven et JDK.
 *
 * Objectifs :
 * - Compiler l'application
 * - Lancer les tests
 * - Vérifier le style de code
 * - Analyser la qualité avec SonarQube
 * - Scanner les dépendances avec Snyk
 */
pipeline {

    /** 
     * 🎯 Spécifie sur quel agent Jenkins ce pipeline doit s'exécuter.
     * 'jenkins-agent' est un nom logique, défini dans la configuration Jenkins.
     */
    agent { label 'jenkins-agent' }

    /**
     * 🔧 Outils nécessaires pour les étapes suivantes du pipeline.
     * Ils doivent être installés et configurés dans Jenkins (Manage Jenkins > Global Tool Configuration).
     */
    tools {
        jdk 'jdk'         // Java Development Kit (version 17)
        maven 'maven'     // Apache Maven (version 3.9)
    }

    /**
     * 🌍 Variables d’environnement utilisées dans certaines étapes du pipeline.
     * Elles pointent vers des outils ou configurations préalablement définis dans Jenkins.
     */
    environment {
        SONARSERVER = 'sonarserver'         // 🔍 Nom du serveur SonarQube configuré dans Jenkins
        SONARSCANNER = 'sonarscanner'       // 🔍 Scanner CLI SonarQube configuré dans Jenkins
        SNYK = 'snyk'                       // 🛡️ Nom de l'installation Snyk (scanner de vulnérabilités)
        BUILD_ID = "0.0.1"                  // 🏗️ ID unique du build Jenkins, utilisé pour taguer l'image Docker
        // Configuration de la mémoire allouée à Maven
        MAVEN_OPTS = "-Xmx1024m"                // Limite la mémoire utilisée par Maven
    }

    /* [5] OPTIONS GLOBALES
     
    Configuration du comportement du pipeline*/
    options {
        // Nettoie le workspace avant chaque build
        skipDefaultCheckout(false)// Limite le nombre de builds gardés
        buildDiscarder(logRotator(numToKeepStr: '5'))// Timeout global du pipeline
        timeout(time: 30, unit: 'MINUTES')// Horodatage des logs
        timestamps() 
     }

    /**
     * 📦 Début des étapes du pipeline (appelées "stages").
     * Chaque stage exécute une tâche spécifique dans le cycle de vie CI/CD.
     */
    stages {

        /**
         * 📥 Étape de récupération du code source depuis le dépôt Git.
         * Jenkins utilise automatiquement l'URL du dépôt configurée dans le job.
         */
        stage('📥 Checkout') {
            steps {
                checkout scm
            }
        }

        /**
         * 🛠️ Étape pour générer un Maven Wrapper si jamais il est absent.
         * Le Maven Wrapper permet d’utiliser la bonne version de Maven même si elle n’est pas installée localement.
         */
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

        /**
         * 🔧 Étape de compilation du code source Java avec Maven.
         * Les tests sont ignorés ici pour se concentrer sur la construction (build).
         */
        stage('🔧 Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
            post {
                success {
                    echo "✅ Build réussi - Archivage des artefacts..."
                    archiveArtifacts artifacts: 'target/*.jar' // 📦 Sauvegarde du fichier .jar généré
                }
            }
        }

        /**
         * 🧪 Étape d’exécution des tests unitaires avec Maven.
         * Les résultats seront utilisés plus tard pour SonarQube.
         */
        stage('🧪 Tests') {
            steps {
                sh 'mvn test'
            }
        }

        /**
         * 🧹 Étape pour vérifier la qualité du code avec Checkstyle.
         * Cela détecte des erreurs de style comme des noms de classes incorrects ou des indentations non conformes.
         */
        stage('🧹 Checkstyle Analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }

        /**
         * 📊 Étape d’analyse de qualité de code avec SonarQube.
         * Envoie les résultats de build, de test, de couverture et de checkstyle à SonarQube.
         */
        stage('📊 SonarQube Analysis') {
            environment {
                scannerHome = tool "${SONARSCANNER}" // 🛠️ Récupère le chemin d’installation du scanner
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

        /**
         * 🔐 Étape de scan des dépendances avec Snyk.
         * Permet de détecter les failles de sécurité dans les bibliothèques tierces utilisées dans le projet.
         */
        stage('Snyk Dependency Scan') {
            steps {
                snykSecurity (
                    severity: 'high',                         // 🚨 Niveau de menace minimum : high, medium, low
                    snykInstallation: "${SNYK}",              // 🔧 Nom défini dans Jenkins pour Snyk CLI
                    snykTokenId: 'snyk-token',                // 🔑 ID de la clé d'API Snyk (stockée dans Jenkins Credentials)
                    targetFile: 'pom.xml',                    // 📄 Fichier principal pour Maven
                    monitorProjectOnBuild: true,              // 📡 Envoi automatique des résultats sur Snyk.io
                    failOnIssues: false,                       // ❌ Échoue le pipeline en cas de vulnérabilités
                    additionalArguments: '--report --format=html --report-file=snyk_report.html' // 📃 Génère un rapport HTML
                ) 
            } 
        }

        /**
            * 📦 Étape de construction de l'image Docker.
            * Cette étape transforme l'application compilée en une image Docker exécutable.
            * 🔹 Docker doit être installé sur l'agent Jenkins et accessible dans le PATH.
            * 🔹 Le fichier 'Dockerfile' à la racine du projet définit comment l’image est construite.
            *
            * Pourquoi c'est utile :
            * - Pour rendre l'application portable : elle peut être exécutée sur n'importe quel serveur Docker.
            * - Pour automatiser le déploiement : l'image peut être poussée vers un registre Docker puis déployée.
            *
            * Exemple d'image produite : demo-rest-api:0.0.1
         */
        stage('📦 Build Docker Image') {
            steps {
                script {
                    def imageName = "demo-rest-api:${BUILD_ID}" // 🔖 Nom complet de l’image Docker (ex: demo-rest-api:0.0.1)
                    // 🐳 Commande pour construire l’image à partir du Dockerfile
                    sh "docker build -t ${imageName} ."

                    env.DOCKER_IMAGE = imageName 
                    
                    // ✅ Message pour confirmer que l’image est bien construite
                    echo "Docker image built: ${imageName}"
                }
            }
        }

            /**
         * 🔍 Étape de scan de l'image Docker avec Trivy.
         * Trivy est un outil de sécurité qui identifie les vulnérabilités dans les images Docker (OS + bibliothèques).
         * L'analyse est effectuée via une API HTTP exposée par le conteneur Trivy (en mode serveur).
         *
         * Conditions :
         * - Le conteneur Trivy doit être actif, sur le même réseau que Jenkins, et écouter sur le port 4954.
         * - L'image Docker doit déjà être construite et présente localement sur l’hôte.
         *
         * Objectifs :
         * - Automatiser l’analyse de sécurité à chaque build.
         * - Générer un rapport de vulnérabilités (JSON) stocké dans Jenkins.
         */
        stage('🔍 Scan Trivy') {
            steps {
                script {
                    echo "⏳ Attente que Trivy soit prêt..."
                    sh "sleep 10"

                    echo "📡 Scan de l'image ${env.DOCKER_IMAGE} avec Trivy..."
                    sh """
                        curl -s -X POST ${env.TRIVY_URL} \\
                        -H 'Content-Type: application/json' \\
                        -d '{
                            "image_name": "${env.DOCKER_IMAGE}",
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



