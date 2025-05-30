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
        // gitlabPush() // Si tu kiffes GitLab
    }

    stages {
        stage('📦 Mise a jour des dépendances') {
            steps {
                echo "🔍 Vérification des dépendances, on s’assure que tout est en ordre."
                sh 'mvn dependency:tree'
            }
            steps {
                echo "🔄 Mise à jour des dépendances Maven, on s’assure que tout est à jour."
                sh 'mvn versions:display-dependency-updates'
            }
            post {
                success {
                    echo "✅ Dépendances mises à jour avec succès."
                }
                failure {
                    echo "⚠️ Échec de la mise à jour des dépendances, vérifiez les logs."
                }
            } 
        }
        
        stage('🛠️ Build & Compile') {
            steps {
                echo "⛏️ Forge en action : compilation du code..."
                sh 'mvn clean install -DskipTests'
            }
            post {
                success {
                    echo "🎯 Build réussi, on archive le précieux artefact."
                    archiveArtifacts artifacts: "${TARGET_DIR}/*.jar", fingerprint: true
                }
                failure {
                    echo "💥 Oups, build cassé, la forge a fait des étincelles… pas dans le bon sens."
                }
            }
        }

        stage('🧪 Tests Unitaires') {
            steps {
                echo "🔬 Mise sous microscope : exécution des tests unitaires."
                sh 'ls -R target'
                sh 'mvn test'
            }
            post {
                always {
                    echo "📊 Rapports unitaires à la rescousse."
                    junit "${TARGET_DIR}/surefire-reports/*.xml"
                }
            }
        }

        stage('🔎 Détection des Tests d’Intégration') {
            steps {
                echo "🧐 Inspection des tests d’intégration trouvés :"
                sh 'find src/test/java -name "*IT.java"'
            }
        }

        stage('🧬 Tests d’Intégration') {
            steps {
                echo "🧬 Tests d’intégration"
                sh 'mvn verify'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: "${TARGET_DIR}/failsafe-reports/*.xml"
                }
            }
        }

        stage('🛡️ Analyse de Sécurité (OWASP)') {
            steps {
                echo "🔍 Inspection minutieuse des dépendances, aucun vilain ne passera."
                sh 'mvn org.owasp:dependency-check-maven:check'
            }
            post {
                always {
                    echo "📑 Rapport OWASP généré, sécurité au top."
                    publishHTML([
                        reportDir: "${TARGET_DIR}",
                        reportFiles: 'dependency-check-report.html',
                        reportName: 'OWASP Dependency Check'
                    ])
                }
                failure {
                    slackSend channel: '#builds', color: 'danger', message: "OWASP Check failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
                }
            }
        }

        stage('🐳 Construction Docker') {
            steps {
                echo "🚢 Montage du conteneur Docker, le navire est prêt à voguer."
                sh 'docker build -t springboot-app:latest .'
            }
        }

        /*
        stage('📡 Analyse SonarQube (optionnel)') {
            steps {
                echo "📈 Envoi des données vers SonarQube, pour un code toujours plus propre."
                withSonarQubeEnv('MySonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        */
    }

    post {
        always {
            echo '🏁 Pipeline achevé, on se prépare pour la prochaine bataille.'
        }
        success {
            echo '🎉 Tout est nickel, déploiement en marche !'
        }
        failure {
            echo '💥 Échec du pipeline, le code a besoin d’amour et de corrections.'
        }
    }
}
