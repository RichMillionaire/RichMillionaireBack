# Rich Millionaire Backend - Authentication API

## Prérequis

### Installation de Maven
```bash
# Sur macOS avec Homebrew
brew install maven

# Vérification de l'installation
mvn --version
```

### Installation de PostgreSQL
```bash
# Sur macOS avec Homebrew
brew install postgresql
brew services start postgresql

# Créer la base de données
createdb richmillionaire_db
```

## Configuration

### Base de données
Modifiez le fichier `src/main/resources/application.yml` si nécessaire :
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/richmillionaire_db
    username: postgres
    password: postgres
```

## Démarrage

### 1. Compilation
```bash
mvn clean compile
```

### 2. Démarrage de l'application
```bash
mvn spring-boot:run
```

L'application sera accessible sur : http://localhost:8080

## API Endpoints

### Authentication

#### 1. Register (Inscription)
```bash
POST /auth/register
Content-Type: application/json

{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

#### 2. Login (Connexion)
```bash
POST /auth/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "password123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "user": {
    "id": 1,
    "username": "testuser",
    "email": "test@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "enabled": true,
    "roles": ["ROLE_USER"],
    "createdAt": "2025-10-06T...",
    "updatedAt": "2025-10-06T..."
  }
}
```

#### 3. Current User (Utilisateur actuel)
```bash
GET /auth/me
Authorization: Bearer {token}
```

### Test Endpoints

#### Public endpoint (pas d'authentification requise)
```bash
GET /api/v1/test/public
```

#### Protected endpoint (authentification requise)
```bash
GET /api/v1/test/protected
Authorization: Bearer {token}
```

## Scripts de test CURL

### 1. Inscription
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com", 
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

### 2. Connexion
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### 3. Test endpoint protégé (remplacer TOKEN par le token reçu)
```bash
curl -X GET http://localhost:8080/auth/me \
  -H "Authorization: Bearer TOKEN"
```

## Architecture

Le projet suit une architecture 3-tiers :

```
Controller → Service → DAO → Database
```

### Structure du code
- `controller/` : Contrôleurs REST
- `service/` : Logique métier
- `dao/` : Accès aux données (JPA)
- `entity/` : Entités JPA
- `dto/` : Objets de transfert de données
- `security/` : Configuration sécurité Spring
- `config/` : Configuration générale
- `utils/` : Utilitaires (JWT)
- `exception/` : Gestion des exceptions

## Technologies utilisées

- Java 17
- Spring Boot 3.5.6
- Spring Security 6
- Spring Data JPA
- PostgreSQL
- Flyway (migrations DB)
- JWT (JSON Web Tokens)
- Lombok
- MapStruct
