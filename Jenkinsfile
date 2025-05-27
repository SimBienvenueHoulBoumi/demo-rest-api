
/*
 * [1] DÉCLARATION DU PIPELINE
 * Définit un pipeline Jenkins qui s'exécutera sur un agent spécifique
*/
pipeline {
    /*
     * [2] CONFIGURATION DE L'AGENT
     * Spécifie que le pipeline s'exécutera sur un nœud Jenkins avec le label 'jenkins-agent'
    */
    agent { label 'jenkins-agent' }

    /*
     * [3] CONFIGURATION DES OUTILS
     * Définit les outils nécessaires qui doivent être préconfigurés dans Jenkins
    */
    tools {
        jdk "JDK17"       // Requiert JDK 17 configuré dans "Manage Jenkins > Global Tool Configuration"
        maven "MAVEN3.9"  // Requiert Maven 3.9 configuré de la même manière
    }

    /*
     * [3] ÉTAPES DU PIPELINE
     * Contient toutes les étapes d'exécution séquentielles
    */
    stages {
        /*
         * [6] ÉTAPE BUILD - COMPILATION
         * Compile le code source et génère les artefacts
        */
        stage('Build') {
            steps {
                /*
                * Exécute la commande Maven pour :
                * - clean : nettoie le répertoire target
                * - install : installe l'artefact dans le repository local
                * - -DskipTests : saute l'exécution des tests pour accélérer le build
                */
                sh 'mvn clean install -DskipTests'
            }

            /*
             * [7] POST-ACTIONS DU BUILD
             * Actions exécutées après l'étape de build selon son statut
            */
            post {
                success {
                    echo "Build réussi - Archivage des artefacts..."
                    // Archive tous les fichiers .jar trouvés dans le sous-répertoire target de demo-rest-api
                    archiveArtifacts artifacts: 'target/*.jar'
                }
            }
        }

        /*
         * [8] ÉTAPE TEST - TESTS UNITAIRES
         * Exécute les tests unitaires et génère des rapports
         * Ne s'exécute que si l'étape Build a réussi
        */
        stage('Test') {
            steps {
                /*
                * Commande Maven:
                * - test : exécute les tests unitaires
                * Génère des rapports dans target/surefire-reports/
                */
                sh 'mvn test'
            }
        }
    }
}
