# Guide d'implémentation du Front-End

## 📋 Architecture de l'API

L'API REST utilise une authentification **JWT stockée dans des cookies HTTP-only** pour une sécurité maximale.

### Base URL
```
http://localhost:8080/api/v1
```

### Documentation Swagger
```
http://localhost:8080/api/v1/swagger-ui/index.html
```

### CORS Configuration
Le backend est configuré pour accepter les requêtes depuis :
- **http://localhost:4200** (Angular - par défaut)
- **http://localhost:3000** (React - si besoin)

---

## 🔐 Authentification avec Cookies HTTP-Only

### Principe
- Le JWT est stocké dans un cookie HTTP-only nommé `jwt-token`
- Le cookie est automatiquement envoyé par le navigateur à chaque requête
- JavaScript ne peut pas accéder au token (protection contre XSS)
- Durée de vie : 24 heures (86400000 ms)

### Configuration requise pour les requêtes

**Important** : Toutes les requêtes HTTP doivent inclure :
```javascript
credentials: 'include'  // Pour fetch API
// ou
withCredentials: true   // Pour Axios
```

---

## 📡 Endpoints de l'API

### 1. Inscription d'un utilisateur

**Endpoint :** `POST /auth/register`

**Body :**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Réponse (200 OK) :**
```json
{
  "message": "Registration successful",
  "user": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "enabled": true,
    "role": false,
    "createdAt": "2025-10-13T12:00:00",
    "updatedAt": "2025-10-13T12:00:00"
  }
}
```

**Cookie reçu :**
- Nom : `jwt-token`
- Type : HTTP-only
- Durée : 24h

**Champ `role` :**
- `true` → L'utilisateur est **ADMIN**
- `false` → L'utilisateur est **USER** (par défaut)

---

### 2. Connexion

**Endpoint :** `POST /auth/login`

**Body :**
```json
{
  "username": "john_doe",
  "password": "password123"
}
```

**Réponse (200 OK) :**
```json
{
  "message": "Login successful",
  "user": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "enabled": true,
    "role": false,
    "createdAt": "2025-10-13T12:00:00",
    "updatedAt": "2025-10-13T12:00:00"
  }
}
```

**Cookie reçu :**
- Nom : `jwt-token`
- Type : HTTP-only

---

### 3. Déconnexion

**Endpoint :** `POST /auth/logout`

**Body :** Aucun

**Réponse (200 OK) :** Vide

**Action :** Supprime le cookie `jwt-token`

---

### 4. Récupérer le profil de l'utilisateur connecté

**Endpoint :** `GET /auth/me`

**Authentification requise :** ✅ Oui (cookie automatique)

**Réponse (200 OK) :**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "enabled": true,
  "role": false,
  "createdAt": "2025-10-13T12:00:00",
  "updatedAt": "2025-10-13T12:00:00"
}
```

---

### 5. Endpoints de test

#### Test public (sans authentification)
**Endpoint :** `GET /test/public`

**Réponse (200 OK) :**
```
This is a public endpoint
```

#### Test protégé (avec authentification)
**Endpoint :** `GET /test/protected`

**Authentification requise :** ✅ Oui

**Réponse (200 OK) :**
```
This is a protected endpoint - you are authenticated!
```

---

## 💻 Exemples d'implémentation Front-End

### Avec Fetch API (Vanilla JS / React)

```javascript
// Configuration de base
const API_BASE_URL = 'http://localhost:8080/api/v1';

// 1. Inscription
async function register(userData) {
  const response = await fetch(`${API_BASE_URL}/auth/register`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include', // IMPORTANT : Pour recevoir et envoyer les cookies
    body: JSON.stringify(userData),
  });
  
  if (!response.ok) {
    throw new Error('Registration failed');
  }
  
  const data = await response.json();
  return data.user; // Stocker les infos utilisateur dans le state
}

// 2. Connexion
async function login(username, password) {
  const response = await fetch(`${API_BASE_URL}/auth/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include', // IMPORTANT
    body: JSON.stringify({ username, password }),
  });
  
  if (!response.ok) {
    throw new Error('Login failed');
  }
  
  const data = await response.json();
  return data.user;
}

// 3. Déconnexion
async function logout() {
  const response = await fetch(`${API_BASE_URL}/auth/logout`, {
    method: 'POST',
    credentials: 'include', // IMPORTANT
  });
  
  if (!response.ok) {
    throw new Error('Logout failed');
  }
}

// 4. Récupérer le profil
async function getProfile() {
  const response = await fetch(`${API_BASE_URL}/auth/me`, {
    method: 'GET',
    credentials: 'include', // IMPORTANT
  });
  
  if (!response.ok) {
    throw new Error('Failed to fetch profile');
  }
  
  return await response.json();
}

// 5. Appel d'un endpoint protégé
async function callProtectedEndpoint() {
  const response = await fetch(`${API_BASE_URL}/test/protected`, {
    method: 'GET',
    credentials: 'include', // IMPORTANT
  });
  
  if (!response.ok) {
    throw new Error('Unauthorized');
  }
  
  return await response.text();
}
```

---

### Avec Axios (React / Vue / Angular)

```javascript
import axios from 'axios';

// Configuration globale d'Axios
const api = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  withCredentials: true, // IMPORTANT : Pour les cookies
  headers: {
    'Content-Type': 'application/json',
  },
});

// Intercepteur pour gérer les erreurs 401 (non authentifié)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Rediriger vers la page de connexion
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// 1. Inscription
export async function register(userData) {
  const response = await api.post('/auth/register', userData);
  return response.data.user;
}

// 2. Connexion
export async function login(username, password) {
  const response = await api.post('/auth/login', { username, password });
  return response.data.user;
}

// 3. Déconnexion
export async function logout() {
  await api.post('/auth/logout');
}

// 4. Récupérer le profil
export async function getProfile() {
  const response = await api.get('/auth/me');
  return response.data;
}

// 5. Appel d'un endpoint protégé
export async function callProtectedEndpoint() {
  const response = await api.get('/test/protected');
  return response.data;
}
```

---

## 🔄 Gestion de l'état utilisateur (React exemple)

```javascript
import React, { createContext, useContext, useState, useEffect } from 'react';
import * as authService from './services/authService';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Vérifier si l'utilisateur est connecté au chargement
  useEffect(() => {
    checkAuth();
  }, []);

  async function checkAuth() {
    try {
      const userData = await authService.getProfile();
      setUser(userData);
    } catch (error) {
      setUser(null);
    } finally {
      setLoading(false);
    }
  }

  async function register(userData) {
    const user = await authService.register(userData);
    setUser(user);
  }

  async function login(username, password) {
    const user = await authService.login(username, password);
    setUser(user);
  }

  async function logout() {
    await authService.logout();
    setUser(null);
  }

  const value = {
    user,
    loading,
    register,
    login,
    logout,
    isAuthenticated: !!user,
    isAdmin: user?.role === true,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
}
```

**Utilisation dans un composant :**
```javascript
import { useAuth } from './context/AuthContext';

function MyComponent() {
  const { user, isAuthenticated, isAdmin, login, logout } = useAuth();

  if (!isAuthenticated) {
    return <LoginForm onLogin={login} />;
  }

  return (
    <div>
      <h1>Bienvenue, {user.firstName} {user.lastName}!</h1>
      <p>Username: {user.username}</p>
      <p>Email: {user.email}</p>
      <p>Rôle: {isAdmin ? 'Administrateur' : 'Utilisateur'}</p>
      <button onClick={logout}>Déconnexion</button>
    </div>
  );
}
```

---

## 🛡️ Sécurité et CORS

### Configuration CORS requise (déjà configurée côté backend)

Le backend doit autoriser les requêtes depuis votre domaine front-end avec les credentials.

Si vous avez des problèmes CORS, assurez-vous que le backend inclut :
```java
// Dans SecurityConfig ou CorsConfig
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Votre URL front
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true); // IMPORTANT pour les cookies
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

---

## 🧪 Tester l'API avec Swagger

1. Accédez à : `http://localhost:8080/api/v1/swagger-ui/index.html`
2. Utilisez l'endpoint `/auth/register` ou `/auth/login`
3. Le cookie sera automatiquement stocké dans votre navigateur
4. Testez les endpoints protégés (le cookie est automatiquement envoyé)

---

## ⚠️ Points importants

1. **Toujours inclure `credentials: 'include'` ou `withCredentials: true`**
   - Sans cela, les cookies ne seront ni envoyés ni reçus

2. **Le token n'est jamais accessible en JavaScript**
   - C'est une fonctionnalité de sécurité (HTTP-only)
   - Vous ne pouvez pas lire le token avec `document.cookie`

3. **Gestion de l'expiration**
   - Le token expire après 24h
   - Si l'API retourne 401, rediriger vers la page de connexion

4. **En production**
   - Le cookie `Secure` sera activé (nécessite HTTPS)
   - Mettre à jour l'URL de base de l'API

5. **CORS**
   - En développement : front sur `localhost:3000`, back sur `localhost:8080`
   - En production : même domaine recommandé ou configuration CORS stricte

---

## 📦 Structure de données UserDto

```typescript
interface User {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  enabled: boolean;
  role: boolean;  // true = ADMIN, false = USER
  createdAt: string; // Format ISO 8601
  updatedAt: string; // Format ISO 8601
}
```

---

## 🎯 Compte de test

Un compte de test est automatiquement créé au démarrage du backend :

- **Username :** `testuser`
- **Password :** `password123`
- **Rôle :** USER (role: false)

---

## 🚀 Démarrer le backend

```bash
cd RichMillionaireBack
./mvnw spring-boot:run
```

Le serveur démarre sur : `http://localhost:8080`

---

## 📝 Exemple de formulaire de connexion (React)

```javascript
import { useState } from 'react';
import { useAuth } from './context/AuthContext';

export function LoginForm() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const { login } = useAuth();

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    
    try {
      await login(username, password);
      // Redirection automatique via le contexte ou le routeur
    } catch (err) {
      setError('Identifiants incorrects');
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <h2>Connexion</h2>
      
      {error && <div className="error">{error}</div>}
      
      <input
        type="text"
        placeholder="Nom d'utilisateur"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        required
      />
      
      <input
        type="password"
        placeholder="Mot de passe"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
      />
      
      <button type="submit">Se connecter</button>
    </form>
  );
}
```

---

## 📞 Support

Pour toute question ou problème :
- Consultez la documentation Swagger
- Vérifiez les logs du backend
- Assurez-vous que `credentials: 'include'` est bien présent dans toutes les requêtes

---

**Dernière mise à jour :** 13 octobre 2025
