# 💪 RestAPI - API de Gestion des items

## 📌 Introduction

RestAPI est une application **Spring Boot** qui expose une API REST permettant de gérer une liste de items.  
Elle fournit des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer) et utilise **Spring Cache** pour améliorer les performances.  

---

## 🎯 Objectifs

- 🔹 Développer une API REST robuste et performante.
- 🔹 Implémenter la gestion du cache pour optimiser les requêtes.

---

## 🏷️ Technologies utilisées & Justification  

### 📌 **Backend : Spring Boot (Java)**
| Technologie         | Rôle |
|---------------------|-------------------------------------|
| **Spring Boot**     | Framework pour créer l'API REST. |
| **Spring Data JPA** | Interaction simplifiée avec la BD. |
| **Spring Cache**    | Optimisation des performances. |
| **Spring Security** | Ajoute une authentification. |
| **Maven**          | Gestionnaire de dépendances. |
| **H2 / MySQL** | Base de données pour stocker les produits. |

---

## 📂 **Structure du Projet**
```
restapi/
│── src/
│   ├── main/
│   │   ├── java/com/example/RestAPI/
│   │   │   ├── config/        # Configurations (cache, sécurité)
│   │   │   ├── controller/    # API Controllers
│   │   │   ├── dto/           # Data Transfer Objects
│   │   │   ├── model/         # Entités (Base de données)
│   │   │   ├── repository/    # Spring Data JPA Repositories
│   │   │   ├── service/       # Services (logique métier)
│   │   │   ├── RestApiApplication.java  # Point d'entrée de l'application
│   ├── resources/
│   │   ├── application.properties  # Configuration de la BD
│   ├── pom.xml  # Dépendances Maven
│   ├── README.md  # Documentation
```

---

## 🚀 **Installation & Déploiement**

### 1️⃣ **Prérequis**
- ☕ **Java 11+**
- 🛠️ **Maven**
- 📄 **PostgreSQL** (ou **H2** pour des tests)


### 2️⃣ **Cloner le projet**
```bash
git clone[ https://github.com/votre-repo/restapi.git](https://github.com/aichaoukdour/RestAPI-Springboot-Multicouche)
cd restapi
```

### 3️⃣ **Configurer la base de données**
Modifier `application.properties` pour la connexion à la BD :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/rest
spring.datasource.username=postgres
spring.datasource.password=postgres1234
spring.jpa.hibernate.ddl-auto=update
```

### 4️⃣ **Lancer l’application**
```bash
mvn spring-boot:run
```
L’API sera disponible sur : `http://localhost:8080/api/items`

---

## 📌 **Documentation de l’API**
### 📛 **Endpoints REST**
| Méthode | Endpoint          | Description |
|---------|------------------|-------------|
| `GET`   | `/api/items`      | Liste des produits |
| `GET`   | `/api/items/{id}` | Détails d’un produit |
| `POST`  | `/api/items`      | Ajouter un produit |
| `PUT`   | `/api/items/{id}` | Mettre à jour un produit |
| `DELETE`| `/api/items/{id}` | Supprimer un produit |

---

# API Testing with Postman


![image](https://github.com/user-attachments/assets/740e9f8f-34eb-486f-a63b-a9f5666bdbf0)
<img width="947" alt="image" src="https://github.com/user-attachments/assets/f955d610-f852-4f40-834a-595b5943bf57" />
![image](https://github.com/user-attachments/assets/e5bff0bc-1c90-400d-b547-274ab938ee62)
![image](https://github.com/user-attachments/assets/4974e690-47fe-4c14-ad49-b9958a2f985c)



Here are some steps to test the API using Postman:

### 1. List Products

- **Method**: GET  
- **URL**: `http://localhost:8080/api/items`

### 2. Get Details of a Specific Product

- **Method**: GET  
- **URL**: `http://localhost:8080/api/items/{id}`  
  Replace `{id}` with a valid product ID.

### 3. Add a Product

- **Method**: POST  
- **URL**: `http://localhost:8080/api/items`  
- **Body (JSON)**:
    ```json
    {
      "name": "Produit Test",
      "price": 19.99
    }
    ```

### 4. Update a Product

- **Method**: PUT  
- **URL**: `http://localhost:8080/api/items/{id}`  
- **Body (JSON)**:
    ```json
    {
      "name": "Product",
      "price": 29.99
    }
    ```

### 5. Delete a Product

- **Method**: DELETE  
- **URL**: `http://localhost:8080/api/items/{id}`  
  Replace `{id}` with a valid product ID.


---


## 🎯 **Améliorations futures**
- 🔹 **Swagger** pour une meilleure documentation de l'API.
- 🔹 **JWT** pour une authentification plus sécurisée.
- 🔹 **Docker** pour faciliter le déploiement.

---

## 🏆 **Conclusion**
Ce projet démontre une API REST complète avec gestion des produits, cache et sécurité.  
 peut être amélioré avec **Swagger, JWT et Docker**.  

---

## 📌 **Auteur**
👤 **Aicha Oukdour**  
💎 [aichaoukdour02@gmail.com]  
 

