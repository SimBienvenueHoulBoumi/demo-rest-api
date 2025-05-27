pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-17'
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
        GITHUB_TOKEN = credentials('github-token')
    }

    stages {
        stage('⚙️ Préparation') {
            steps {
                cleanWs()
                script {
                    if (!fileExists('pom.xml')) error "pom.xml manquant"
                }
            }
        }

        stage('📦 Build Maven') {
            steps {
                sh 'mvn clean install -B'
            }
        }

        stage('✅ Tests') {
            steps {
                sh 'mvn test'
            }
        }
    }
}
