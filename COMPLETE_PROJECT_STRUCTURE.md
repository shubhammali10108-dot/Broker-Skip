# 🏢 Broker-Skip Real Estate Platform - Complete Project Structure

## 📦 Project Directory Layout

```
Broker-Skip/
│
├── 📄 README.md
├── 📄 docker-compose.yml
├── 📄 COMPLETE_PROJECT_STRUCTURE.md (यह फाइल)
│
├── 📁 database/
│   └── init.sql
│
├── 📁 backend/ (Spring Boot Backend)
│   ├── pom.xml
│   ├── .gitignore
│   │
│   └── src/
│       └── main/
│           ├── java/com/brokerskip/
│           │   │
│           │   ├── 📄 BrokerSkipApplication.java (Main Application)
│           │   │
│           │   ├── 📁 config/
│           │   │   ├── SecurityConfig.java
│           │   │   ├── CorsConfig.java
│           │   │   └── JwtConfig.java
│           │   │
│           │   ├── 📁 controller/
│           │   │   ├── AuthController.java
│           │   │   ├── UserController.java
│           │   │   ├── PropertyController.java
│           │   │   └── FavoriteController.java
│           │   │
│           │   ├── 📁 service/
│           │   │   ├── OTPService.java
│           │   │   ├── UserService.java
│           │   │   ├── PropertyService.java
│           │   │   └── FavoriteService.java
│           │   │
│           │   ├── 📁 repository/
│           │   │   ├── UserRepository.java
│           │   │   ├── OTPRepository.java
│           │   │   ├── PropertyRepository.java
│           │   │   └── FavoriteRepository.java
│           │   │
│           │   ├── 📁 entity/
│           │   │   ├── User.java
│           │   │   ├── OTP.java
│           │   │   ├── Property.java
│           │   │   └── Favorite.java
│           │   │
│           │   ├── 📁 dto/
│           │   │   ├── LoginRequest.java
│           │   │   ├── OTPVerifyRequest.java
│           │   │   ├── PropertyDTO.java
│           │   │   └── UserProfileDTO.java
│           │   │
│           │   ├── 📁 exception/
│           │   │   ├── GlobalExceptionHandler.java
│           │   │   ├── InvalidOTPException.java
│           │   │   └── ResourceNotFoundException.java
│           │   │
│           │   ├── 📁 util/
│           │   │   ├── OTPUtil.java
│           │   │   ├── JwtUtil.java
│           │   │   └── ResponseUtil.java
│           │   │
│           │   └── 📁 security/
│           │       ├── JwtAuthenticationFilter.java
│           │       └── CustomUserDetailsService.java
│           │
│           └── resources/
│               ├── application.properties
│               ├── application-dev.properties
│               └── application-prod.properties
│
├── 📁 frontend/ (Angular JS Frontend)
│   ├── 📄 package.json
│   ├── 📄 .gitignore
│   ├── 📄 index.html (Main HTML)
│   │
│   ├── 📁 css/
│   │   ├── style.css
│   │   ├── responsive.css
│   │   ├── animations.css
│   │   └── variables.css
│   │
│   ├── 📁 js/
│   │   ├── 📄 app.js (Main App Configuration)
│   │   │
│   │   ├── 📁 controllers/
│   │   │   ├── MainController.js
│   │   │   ├── AuthController.js
│   │   │   ├── DashboardController.js
│   │   │   ├── ProfileController.js
│   │   │   ├── PropertyController.js
│   │   │   └── FavoriteController.js
│   │   │
│   │   ├── 📁 services/
│   │   │   ├── AuthService.js
│   │   │   ├── PropertyService.js
│   │   │   ├── FavoriteService.js
│   │   │   ├── UserService.js
│   │   │   └── ApiService.js
│   │   │
│   │   ├── 📁 directives/
│   │   │   ├── PropertyCard.js
│   │   │   ├── LoadingSpinner.js
│   │   │   └── ConfirmDialog.js
│   │   │
│   │   ├── 📁 filters/
│   │   │   ├── CurrencyFilter.js
│   │   │   └── DateFilter.js
│   │   │
│   │   └── 📁 interceptors/
│   │       └── TokenInterceptor.js
│   │
│   ├── 📁 views/
│   │   ├── 📄 login.html
│   │   ├── 📄 profile-creation.html
│   │   ├── 📄 dashboard.html
│   │   ├── 📄 my-posts.html
│   │   ├── 📄 favorites.html
│   │   ├── 📄 profile.html
│   │   ├── 📄 create-post.html
│   │   ├── 📄 property-details.html
│   │   └── 📄 edit-profile.html
│   │
│   ├── 📁 assets/
│   │   ├── images/
│   │   ├── icons/
│   │   └── fonts/
│   │
│   └── 📁 config/
│       └── api-config.js
│
└── 📁 docs/
    ├── API_DOCUMENTATION.md
    ├── SETUP_GUIDE.md
    ├── DATABASE_SCHEMA.md
    └── DEPLOYMENT_GUIDE.md
```

---

## 📋 File Details

### Backend Files

#### Controllers
- **AuthController.java** - OTP send, verify, login
- **UserController.java** - User profile management
- **PropertyController.java** - Property CRUD operations
- **FavoriteController.java** - Favorites management

#### Services
- **OTPService.java** - OTP generation, verification, 5min expiry
- **UserService.java** - User business logic
- **PropertyService.java** - Property business logic
- **FavoriteService.java** - Favorite management logic

#### Repositories
- **UserRepository.java** - User database queries
- **OTPRepository.java** - OTP database queries
- **PropertyRepository.java** - Property database queries
- **FavoriteRepository.java** - Favorite database queries

#### Entity Classes
- **User.java** - User model
- **OTP.java** - OTP model
- **Property.java** - Property listing model
- **Favorite.java** - Favorite model

---

### Frontend Files

#### Controllers
- **AuthController.js** - Login & OTP verification
- **DashboardController.js** - Dashboard & property list
- **ProfileController.js** - Profile creation & update
- **PropertyController.js** - Property details & editing
- **FavoriteController.js** - Favorites management

#### Services
- **AuthService.js** - Authentication APIs
- **PropertyService.js** - Property APIs
- **FavoriteService.js** - Favorite APIs
- **UserService.js** - User profile APIs

#### Views
- **login.html** - Mobile OTP login
- **dashboard.html** - Main dashboard
- **profile-creation.html** - New user profile setup
- **create-post.html** - Create property listing
- **profile.html** - User profile view

---

## 🔧 Configuration Files

### Backend
- **application.properties** - Main config (port, database, JWT)
- **pom.xml** - Maven dependencies

### Frontend
- **package.json** - NPM dependencies
- **index.html** - Main HTML file

### Docker
- **docker-compose.yml** - MySQL + Spring Boot services

### Database
- **init.sql** - Database initialization script

---

## 🚀 How to Use

### Development Setup

```bash
# 1. Clone repository
git clone https://github.com/shubhammali10108-dot/Broker-Skip.git
cd Broker-Skip

# 2. Start Docker services
docker-compose up -d

# 3. Backend (Terminal 1)
cd backend
mvn clean install
mvn spring-boot:run

# 4. Frontend (Terminal 2)
cd frontend
npm install
npm start
```

---

## 📞 API Endpoints

### Authentication
- `POST /api/auth/send-otp` - Send OTP
- `POST /api/auth/verify-otp` - Verify OTP

### User Profile
- `POST /api/users/profile` - Create profile
- `GET /api/users/profile/{id}` - Get profile
- `PUT /api/users/profile/{id}` - Update profile

### Properties
- `GET /api/properties` - Get all properties
- `POST /api/properties` - Create property
- `GET /api/properties/{id}` - Get property details
- `PUT /api/properties/{id}` - Update property
- `DELETE /api/properties/{id}` - Delete property

### Favorites
- `POST /api/favorites` - Add favorite
- `GET /api/favorites/user/{userId}` - Get user favorites
- `DELETE /api/favorites/{propertyId}` - Remove favorite

---

## 💾 Database Tables

- **users** - User profiles
- **otp** - OTP records (5 min expiry)
- **properties** - Property listings
- **favorites** - User favorites

---

## 🎨 Frontend Pages

1. **Login** → OTP verification (5 min timer)
2. **Profile Creation** → New user setup
3. **Dashboard** → View all properties
4. **My Posts** → User's listed properties
5. **Favorites** → Saved properties
6. **Profile** → User profile view
7. **Create Post** → New property listing

---

## 📱 Key Features

✅ Mobile OTP Authentication  
✅ 5 Minute OTP Expiry  
✅ Profile Management  
✅ Property Listings  
✅ Favorites System  
✅ Search & Filter  
✅ Responsive Design  
✅ Real-time Updates  

---

## 🔒 Security

- JWT Token Authentication
- OTP Verification
- CORS Configuration
- SQL Injection Prevention
- Password Hashing (in future)

---

## 📧 Support

For issues or questions, create an issue in the repository.

---

**Last Updated:** 2026-05-08  
**Version:** 1.0.0
