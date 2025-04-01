# GrapeGrade 🍷

A wine rating and review application that helps wine enthusiasts discover and rate their favorite wines.

## Features

- **Wine Management**: Add, update, and manage wine information including name, type, grape variety, year, and alcohol percentage
- **Rating System**: Rate wines on a scale from 0 to 10 with decimal precision
- **Review System**: Share detailed reviews about your wine experiences
- **Top Wines**: Discover the highest-rated wines with filtering options by:
  - Grape variety
  - Country
  - Wine type
  - Year
- **Excel Import**: Bulk import wine data and ratings from Excel files
- **User Authentication**: Secure user authentication with JWT tokens

## Tech Stack

- **Backend**: Spring Boot
- **Database**: MongoDB
- **Authentication**: JWT (JSON Web Tokens)
- **API Documentation**: Swagger 2.9.2
- **Excel Processing**: Apache POI 4.1.2
- **Logging**: Log4j2 2.17.1

## Prerequisites

- Java 11
- MongoDB running locally or a MongoDB Atlas connection string
- Maven 3.6 or higher

## Getting Started

1. Clone the repository:
```bash
git clone https://github.com/yourusername/grapegrade.git
```

2. Navigate to the project directory:
```bash
cd grapegrade
```

3. Configure MongoDB connection in `src/main/resources/application.properties`:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/grapeGrade
```

4. Build the project:
```bash
mvn clean install
```

5. Run the application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /authenticate`: Generate JWT token
- `POST /users/register`: Register new user

### Wines
- `GET /wine`: List all wines
- `GET /wine/{id}`: Get wine by ID
- `POST /wine`: Create new wine
- `PUT /wine/{id}`: Update wine
- `DELETE /wine/{id}`: Delete wine
- `GET /wine/topWines`: Get top-rated wines with filters

### Ratings
- `GET /ratings`: List all ratings
- `GET /ratings/{id}`: Get rating by ID
- `POST /ratings`: Create new rating
- `PUT /ratings/{id}`: Update rating
- `DELETE /ratings/{id}`: Delete rating

### Import
- `POST /import`: Import wine data from Excel file

## Excel Import Format

The Excel file should have the following columns:
1. Wine Name
2. Grape Varieties (comma-separated)
3. Year
4. Country
5. Wine Type
6. Alcohol Percentage
7. Username
8. Rating (0-10)

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Acknowledgments

- Spring Boot team for the amazing framework
- MongoDB team for the powerful database
- All contributors who help improve this project
