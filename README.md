# 🛠️ Task Management API - Spring Boot REST

Une API REST simple et modulaire pour gérer des tâches. Conçue avec une architecture propre, testée, et facilement extensible.

## 🚀 Fonctionnalités

- 🔁 CRUD complet sur les tâches (`Task`)
- 📦 Architecture en couches : Controller → Service → ServiceImpl → Repository
- ✅ Validation des entrées avec `javax.validation`
- 🛡️ Gestion centralisée des erreurs via `@ControllerAdvice`
- 🧪 Tests unitaires & d’intégration (JUnit)
- 📜 Documentation Swagger intégrée

---

## 📚 Tech Stack

| Tech         | Description                        |
|--------------|------------------------------------|
| Java 17      | Langage principal                  |
| Spring Boot  | Framework principal                |
| Spring Data JPA | Persistance avec Hibernate     |
| H2 Database  | Base de données en mémoire (test) |
| Lombok       | Réduction du boilerplate          |
| Swagger      | Documentation d’API interactive   |
| JUnit 5      | Framework de tests                 |

---

## 🔧 Architecture

```
src
├── controller
├── service
│   └── impl
├── repository
├── model
├── dto
├── exceptions
└── config
```

---

## 🥪 Lancer les tests

```bash
./mvnw test

## installer les dependences
```

## lancer le pro

```bash
mvn clean install && mvn spring-boot:run

---


## 📖 Swagger UI

Visite [http://localhost:5000/swagger-ui/index.html](http://localhost:5000/swagger-ui/index.html) pour tester l’API.

---

## 📢 Endpoints

| Méthode | Endpoint            | Description           |
|---------|---------------------|-----------------------|
| GET     | `/api/tasks`        | Lister les tâches     |
| GET     | `/api/tasks/{id}`   | Obtenir une tâche     |
| POST    | `/api/tasks`        | Créer une tâche       |
| PUT     | `/api/tasks/{id}`   | Modifier une tâche    |
| DELETE  | `/api/tasks/{id}`   | Supprimer une tâche   |

---

## ⚠️ Gestion des erreurs

Les erreurs sont renvoyées au format JSON avec un message clair.  
Exemple :

```json
{
  "timestamp": "2025-04-17T19:35:28.514+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Task not found with id: 2",
  "path": "/api/tasks/2"
}
```

---

## 💡 À venir (roadmap)

- 🔐 Sécurisation avec Spring Security + JWT
- 🧠 Ajout de filtres (par status, date, etc.)
- 🗳️ Ajout de commentaires sur les tâches
- 📊 Monitoring avec Actuator + Prometheus

---

## 👨‍💼 Auteur

Développé avec ❤️ par @SimBienvenueHoulBoumi

N’hésite pas à forker, tester, ou me faire un retour ✌️

---

## 📝 Licence

Ce projet est sous licence MIT. Tu peux l’utiliser comme base de ton propre projet.

