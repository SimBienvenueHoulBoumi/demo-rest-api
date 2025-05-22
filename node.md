Parfait, Simbie. Un projet **Spring Boot** + **Jenkins** + **Maven**, c’est du classique… mais faut que ce soit bien huilé. Voilà **ta config Jenkins** *sur-mesure*, pensée pour **builder**, **tester** et pourquoi pas **packager ton `.jar`** façon pro.

---

## 🏗️ 1. Configuration Jenkins globale

### ✅ JDK

* **Nom** : `java-17`
* ✅ Coche **"Install automatically"**
* Sélectionne **Adoptium** (ou autre)
* **Version** : `17`

> Spring Boot 3.x tourne avec Java 17 mini.

---

### ✅ Maven

* **Nom** : `maven-3.9.9`
* ✅ Coche **"Install automatically"**
* Version : `3.9.9`

---

## 📁 Arborescence attendue de ton projet

```bash
demo/
├── pom.xml
├── src/
│   ├── main/
│   └── test/
└── Jenkinsfile
```

---

## ✍️ 2. `Jenkinsfile` (dans ton repo Git)

Voici un exemple complet et modulaire :

```groovy
pipeline {
    agent any

    tools {
        jdk 'java-17'
        maven 'maven-3.9.9'
    }

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
    }

    stages {
        stage('🔧 Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('📦 Compile + Test') {
            steps {
                sh 'mvn install -DskipTests=false'
            }
        }

        stage('🧪 Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('🎁 Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('🚀 Archive JAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo "✅ Build terminé avec succès !"
        }
        failure {
            echo "❌ Échec du build... check les logs."
        }
    }
}
```

---

## 🔥 Optionnel : Exécuter l’app après build

Ajoute une étape comme :

```groovy
stage('▶️ Run') {
    steps {
        sh 'java -jar target/*.jar &'
    }
}
```

Mais ⚠️ en CI/CD, mieux vaut *ne pas run en direct*. Préfère un **docker build & push**, ou une **livraison vers serveur distant**.

---

## 🧠 Bonus : enchaîner avec Docker

Tu veux un `Dockerfile` + `docker-compose.yml` pour containeriser ton app Spring Boot après le build ? Ou bien tu veux que Jenkins le fasse pour toi en pipeline ? Je peux te générer ça aussi 👇

Tu veux partir sur une image `openjdk`, ou une base plus légère (genre `distroless` ou `alpine`) ?
