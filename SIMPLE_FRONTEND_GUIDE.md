# Guide d'implémentation du Front-End - Version Simple

## 📋 Architecture de l'API

L'API REST utilise une **authentification simple** basée sur username/password **en clair** (sans encryption, sans JWT, sans cookies).

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

## 🔓 Authentification Simple

### Principe
- Pas de JWT, pas de tokens, pas de cookies
- Authentification basée uniquement sur **username + password**
- Le mot de passe est stocké **en clair** dans la base de données
- Le front-end doit stocker les informations utilisateur dans son state après connexion

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

**Réponse (400/500) en cas d'échec :**
```json
{
  "message": "Invalid username or password"
}
```

---

### 3. Récupérer un utilisateur par username

**Endpoint :** `GET /auth/users/{username}`

**Exemple :** `GET /auth/users/john_doe`

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

### 4. Endpoints de test

#### Test public
**Endpoint :** `GET /test/public`

**Réponse (200 OK) :**
```
This is a public endpoint
```

#### Test hello
**Endpoint :** `GET /test/hello`

**Réponse (200 OK) :**
```
Hello from Rich Millionaire API!
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
    body: JSON.stringify(userData),
  });
  
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || 'Registration failed');
  }
  
  const data = await response.json();
  return data.user; // Stocker dans le state (localStorage, Context, etc.)
}

// 2. Connexion
async function login(username, password) {
  const response = await fetch(`${API_BASE_URL}/auth/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ username, password }),
  });
  
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || 'Login failed');
  }
  
  const data = await response.json();
  return data.user; // Stocker dans le state
}

// 3. Récupérer un utilisateur
async function getUserByUsername(username) {
  const response = await fetch(`${API_BASE_URL}/auth/users/${username}`, {
    method: 'GET',
  });
  
  if (!response.ok) {
    throw new Error('User not found');
  }
  
  return await response.json();
}
```

---

### Avec Axios (React / Vue / Angular)

```javascript
import axios from 'axios';

// Configuration globale d'Axios
const api = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
});

// 1. Inscription
export async function register(userData) {
  try {
    const response = await api.post('/auth/register', userData);
    return response.data.user;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Registration failed');
  }
}

// 2. Connexion
export async function login(username, password) {
  try {
    const response = await api.post('/auth/login', { username, password });
    return response.data.user;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Login failed');
  }
}

// 3. Récupérer un utilisateur
export async function getUserByUsername(username) {
  try {
    const response = await api.get(`/auth/users/${username}`);
    return response.data;
  } catch (error) {
    throw new Error('User not found');
  }
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

  // Charger l'utilisateur depuis localStorage au démarrage
  useEffect(() => {
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      setUser(JSON.parse(savedUser));
    }
    setLoading(false);
  }, []);

  async function register(userData) {
    const user = await authService.register(userData);
    setUser(user);
    localStorage.setItem('user', JSON.stringify(user));
  }

  async function login(username, password) {
    const user = await authService.login(username, password);
    setUser(user);
    localStorage.setItem('user', JSON.stringify(user));
  }

  function logout() {
    setUser(null);
    localStorage.removeItem('user');
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
      setError(err.message || 'Identifiants incorrects');
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

## ⚠️ Points importants

1. **Stockage des informations utilisateur**
   - Utilisez `localStorage`, `sessionStorage`, ou un state global (Context, Redux, etc.)
   - Sauvegardez l'objet `user` complet après connexion/inscription

2. **Pas de sécurité avancée**
   - Cette approche est **simple** mais **non sécurisée** pour la production
   - Les mots de passe sont en clair dans la base de données
   - Pas de protection contre les attaques XSS/CSRF
   - Recommandé uniquement pour le développement/prototypage

3. **Gestion de la déconnexion**
   - Supprimez simplement les données utilisateur du state/localStorage
   - Pas besoin d'appeler le backend

4. **En production**
   - Il faudra implémenter une vraie sécurité (JWT, encryption, etc.)

---

## 🧪 Tester l'API avec Swagger

1. Accédez à : `http://localhost:8080/api/v1/swagger-ui/index.html`
2. Utilisez l'endpoint `/auth/register` pour créer un compte
3. Utilisez l'endpoint `/auth/login` pour vous connecter
4. Testez les autres endpoints

---

## 📞 Support

Pour toute question ou problème :
- Consultez la documentation Swagger
- Vérifiez les logs du backend

---

**Dernière mise à jour :** 13 octobre 2025

**Note importante :** Cette implémentation est volontairement simple et sans sécurité pour faciliter le développement. Ne **jamais** utiliser cette approche en production !

