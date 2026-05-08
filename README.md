# Broker-Skip - Real Estate Platform

Complete end-to-end Real Estate application with mobile OTP authentication, property listings, and dashboard.

## Features

✅ Mobile OTP Authentication
✅ 5 Minute OTP Expiry
✅ User Profile Creation
✅ Real Estate Property Listings
✅ Dashboard with Multiple Views (All Posts, My Posts, Favorites)
✅ Create New Property Listings
✅ Favorite/Save Properties
✅ User Profile Management

## Tech Stack

- **Frontend:** Angular JS
- **Backend:** Spring Boot (Java)
- **Database:** MySQL
- **DevOps:** Docker & Docker Compose

## Project Structure

```
Broker-Skip/
├── frontend/                 # Angular JS Frontend
├── backend/                  # Spring Boot Backend
├── database/                 # Database Schema
├── docker-compose.yml        # Docker Configuration
└── README.md
```

## Setup Instructions

### Prerequisites
- Docker & Docker Compose installed
- Java 11+ (for local Spring Boot development)
- Node.js & npm (for Angular development)
- MySQL Client (optional, for direct database access)

### Quick Start with Docker

```bash
# Clone the repository
git clone <repo-url>
cd Broker-Skip

# Start all services
docker-compose up -d

# Check logs
docker-compose logs -f
```

### Manual Setup

#### 1. Database Setup
```bash
mysql -u root -p < database/init.sql
```

#### 2. Backend Setup
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

#### 3. Frontend Setup
```bash
cd frontend
npm install
npm start
```

## API Endpoints

### Authentication
- `POST /api/auth/send-otp` - Send OTP to mobile
- `POST /api/auth/verify-otp` - Verify OTP
- `POST /api/auth/logout` - Logout user

### User Profile
- `POST /api/users/profile` - Create user profile
- `GET /api/users/profile/{userId}` - Get user profile
- `PUT /api/users/profile/{userId}` - Update user profile

### Properties
- `GET /api/properties` - Get all properties
- `POST /api/properties` - Create new property
- `GET /api/properties/{id}` - Get property details
- `PUT /api/properties/{id}` - Update property
- `DELETE /api/properties/{id}` - Delete property
- `GET /api/properties/user/{userId}` - Get user's properties

### Favorites
- `POST /api/favorites` - Add to favorites
- `GET /api/favorites/user/{userId}` - Get user's favorites
- `DELETE /api/favorites/{propertyId}` - Remove from favorites

## Database Schema

### users Table
- id (Primary Key)
- phone_number (Unique)
- email
- first_name
- last_name
- profile_photo_url
- bio
- city
- state
- is_verified
- created_at
- updated_at

### otp Table
- id (Primary Key)
- phone_number
- otp_code
- is_verified
- expires_at (5 minutes from creation)
- created_at

### properties Table
- id (Primary Key)
- user_id (Foreign Key)
- title
- description
- property_type (Apartment, House, Land, etc.)
- price
- area (in sq ft)
- bedrooms
- bathrooms
- location
- city
- state
- pincode
- image_urls (JSON array)
- amenities (JSON array)
- is_active
- created_at
- updated_at

### favorites Table
- id (Primary Key)
- user_id (Foreign Key)
- property_id (Foreign Key)
- created_at

## Frontend Pages

### 1. Login Page
- Mobile number input
- OTP input with 5 minute countdown
- Auto-submit on correct OTP

### 2. Profile Creation Page
- First Name, Last Name
- Email
- City, State, Pincode
- Profile Photo Upload
- Bio/Description

### 3. Dashboard
- **Header:** Logo, Create Post Button, Profile Menu
- **Sidebar/Navigation:**
  - All Posts
  - My Posts
  - Favorites
  - Profile
- **Main Content Area:**
  - Displays properties based on selected section
  - Each property card shows: Image, Title, Price, Location, Bedrooms, Bathrooms
  - Action buttons: View Details, Add to Favorites, Edit (if owner), Delete (if owner)

### 4. Create Property Page
- Title, Description
- Property Type, Price, Area
- Bedrooms, Bathrooms
- Location Details
- Image Upload (Multiple)
- Amenities Selection
- Submit Button

### 5. Property Details Page
- Full property information
- Image gallery
- Contact seller option
- Add to favorites
- Share options

## Configuration Files

### docker-compose.yml
Configures MySQL and Spring Boot services.

### application.properties
Spring Boot configuration for database, server port, etc.

## Running Tests

```bash
# Backend tests
cd backend
mvn test

# Frontend tests
cd frontend
npm test
```

## Deployment

### Docker Image Build
```bash
docker build -t broker-skip-backend:latest ./backend
```

### Production Deployment
Use docker-compose for production with proper environment variables.

## Troubleshooting

### MySQL Connection Issues
- Check if MySQL container is running: `docker-compose ps`
- Verify database credentials in `application.properties`

### Spring Boot Not Starting
- Check Java version: `java -version`
- View logs: `docker-compose logs broker-skip-backend`

### Angular Not Loading
- Clear npm cache: `npm cache clean --force`
- Reinstall dependencies: `npm install`

## Contributing

1. Create a feature branch
2. Commit your changes
3. Push to the branch
4. Create a Pull Request

## License

MIT License - feel free to use this project

## Support

For issues and questions, please create an issue in the repository.
