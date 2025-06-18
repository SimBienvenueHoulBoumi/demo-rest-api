###############################################################################
# 📦 Dockerfile multi-étapes pour une application Java Spring Boot (Maven)
# Ce fichier suit les meilleures pratiques CI/CD en 2025 :
# - Construction dans une image Maven dédiée
# - Exécution dans une image légère JDK officielle (Ubuntu Jammy)
# - Compatible ARM et x86_64
# - Aucune image obsolète ou non maintenue
###############################################################################

##
# 🧱 Étape de build : utilise Maven + JDK pour compiler l'application.
# L'image est officielle, maintenue par Eclipse Temurin et Apache.
##
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# 📁 Crée un dossier de travail dans le conteneur
WORKDIR /app

# 📥 Copie l'ensemble du code source dans le conteneur
COPY . .

# 🏗️ Compile l'application et génère le JAR (en ignorant les tests pour aller plus vite)
RUN mvn clean package -DskipTests


##
# 🚀 Étape finale : image légère avec uniquement le JAR
# Utilise une image Java minimale, basée sur Ubuntu 22.04 (jammy)
# Remplace les images Alpine qui posent souvent des problèmes de compatibilité
##
FROM eclipse-temurin:17-jdk-jammy

# 📁 Définit le dossier de travail dans l'image finale
WORKDIR /app

# 📦 Copie le JAR généré depuis l’étape précédente
COPY --from=builder /app/target/*.jar app.jar

# 🚀 Définit la commande à exécuter au démarrage du conteneur
ENTRYPOINT ["java", "-jar", "app.jar"]
