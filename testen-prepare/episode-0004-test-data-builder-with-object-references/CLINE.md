# CLINE Project Guide - Library Management System

## 1. Project Overview

### 1.1 Description
This is a Spring Boot-based library management system application that demonstrates various software design patterns, particularly the **TestDataBuilder pattern** for test data generation with object references. The project serves as a reference implementation for testing strategies in Spring Boot applications.

### 1.2 Key Technologies
- **Language**: Java (JDK 17+)
- **Framework**: Spring Boot 3.x
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito
- **Database**: JPA/Hibernate with in-memory H2 or PostgreSQL support

### 1.3 High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    API Layer (REST)                         │
│              Controllers / DTOs / Endpoints                 │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│             Business Logic Layer (Use Cases)                │
│                  Handlers / Services                        │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│              Persistence Layer (JPA Entities)               │
│          Repositories / Entity Classes / DB Schema          │
└─────────────────────────────────────────────────────────────┘
```

### 1.4 Domain Model
The application manages a library system with core entities:
- **Book**: Core entity representing books in the library
- **Author**: Writers/creators of books (Many-to-Many with Book)
- **Publisher**: Publishing companies (Many-to-Many with Book via BookPublisherEntity)
- **Customer**: Library customers who can borrow books (One-to-Many with Book)

---

## 2. Getting Started

### 2.1 Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven 3.8+ (or your IDE's built-in Maven)
- Git for version control

### 2.2 Installation

```bash
# Clone the repository (if not already done)
git clone <repository-url>
cd episode-0004-test-data-builder-with-object-references

# Build the project
mvn clean install

# Run tests
mvn test

# Run the application
mvn spring-boot:run
```

### 2.3 Running the Application

```bash
# Development mode with hot reload
mvn spring-boot:run

# With custom port
mvn spring-boot:run -Dserver.port=8081

# As executable jar
mvn clean package
java -jar target/episode-0004-test-data-builder-with-object-references-*.jar
```

### 2.4 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/books` | Retrieve all books |
| `POST` | `/api/books` | Register a new book |
| `GET` | `/api/books/{id}` | Get book by ID |
| `PUT` | `/api/books/{id}` | Update book details |
| `DELETE` | `/api/books/{id}` | Delete a book |

See `EndpointConstants.java` for all endpoint paths.

---

## 3. Project Structure

```
src/
├── main/
│   ├── java/de/devtime/examples/library/
│   │   ├── DemoBibliothekApplication.java          # Main Spring Boot application
│   │   ├── api/                                    # REST API layer
│   │   │   ├── contract/                           # DTOs and contracts
│   │   │   └── impl/                               # REST controllers
│   │   ├── businesslogic/                          # Domain logic layer
│   │   │   ├── handler/                            # Use case handlers
│   │   │   └── object/                             # Business objects
│   │   ├── context/                                # Context/configuration classes
│   │   ├── event/                                  # Event system (commands/domains)
│   │   └── persistence/                            # Data access layer
│   │       ├── entity/                             # JPA entities
│   │       ├── repository/                         # Spring Data repositories
│   │       └── util/                               # Utility classes
│   └── resources/
│       ├── application.yaml                        # Application configuration
│       ├── log4j2.xml                              # Logging configuration
│       └── snippet.json                            # Sample data
├── test/
│   ├── java/de/devtime/examples/library/
│   │   ├── businesslogic/object/                   # Business logic tests
│   │   ├── persistence/entity/                     # Entity tests with TestDataBuilders
│   │   └── test/builder/                           # Shared test utilities
│   └── resources/
└── target/                                         # Build output
```

### Key Files

| File | Purpose |
|------|---------|
| `DemoBibliothekApplication.java` | Spring Boot main class with `@SpringBootApplication` |
| `application.yaml` | Application configuration (server, datasource, logging) |
| `pom.xml` | Maven dependencies and build configuration |

---

## 4. Development Workflow

### 4.1 Coding Standards
- Follow **Clean Code** principles
- Use meaningful names for classes, methods, and variables
- Keep classes focused on a single responsibility (SRP)
- Write comprehensive tests before or alongside code (TDD approach)

### 4.2 Testing Approach

#### Test Data Builder Pattern
This project uses the **TestDataBuilder pattern** to create test data with object references:

```java
// Example: Creating an Author with nested entities
AuthorEntity author = new AuthorEntityTestDataBuilder()
    .withId(1L)
    .withName("John Doe")
    .build();

// Building a book with publisher and additional data
BookEntity book = new BookEntityTestDataBuilder()
    .withTitle("Spring Boot Guide")
    .withAuthor(author)  // Object reference
    .withPublisher(publisher)  // Another object reference
    .withAdditionalData(additionalData)
    .build();
```

#### Test Organization
- Tests are located in `src/test/java/`
- Each entity has a corresponding test class and TestDataBuilder
- Use parameterized tests for comprehensive coverage

### 4.3 Build and Deployment

```bash
# Clean build
mvn clean package -DskipTests

# Full build with tests
mvn clean install

# Check dependency tree
mvn dependency:tree

# Analyze code quality
mvn sonar:sonar  # If SonarQube configured
```

### 4.4 Contribution Guidelines
1. Create feature branches from `main`
2. Write tests for new functionality
3. Run `mvn test` before committing
4. Follow the existing code style and structure
5. Update documentation as needed

---

## 5. Key Concepts

### 5.1 Design Patterns Used

| Pattern | Purpose |
|---------|---------|
| **TestDataBuilder** | Create complex test data objects with default values |
| **Factory Pattern** | Object creation (AuthorFactory, BookFactory) |
| **Command/Event Pattern** | Separate command handling from business logic |
| **Repository Pattern** | Abstract data access layer |
| **DTO Pattern** | Separate API contracts from domain models |

### 5.2 Core Abstractions

#### Business Objects
- `AbstractBusinessObject`: Base class for all business objects
- `UseCaseHandler`: Interface for use case handlers

#### Entities
- `AbstractEntity`: Base entity with ID and audit fields
- `BuilderWithId<T>`: Builder pattern interface for entities

### 5.3 Event System
The application uses a two-tier event system:

1. **Commands**: `RegisterBookCommandEvent`, `LoanBookCommandEvent`
2. **Domain Events**: `AuthorRegisteredDomainEvent`, `BookRegisteredDomainEvent`

---

## 6. Common Tasks

### 6.1 Add a New Entity

1. Create entity class extending `AbstractEntity`:
```java
@Entity
public class NewEntity extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    // getters/setters
}
```

2. Create repository interface:
```java
@Repository
public interface NewEntityRepository extends JpaRepository<NewEntity, Long> {
}
```

3. Create TestDataBuilder in test folder:
```java
public class NewEntityTestDataBuilder {
    // Follow the existing pattern
}
```

4. Add tests for the new entity

### 6.2 Add a New API Endpoint

1. Define DTOs in `api/contract/`
2. Create controller in `api/impl/`:
```java
@RestController
@RequestMapping(EndpointConstants.BASE_PATH)
public class NewEntityRestController {
    private final UseCaseHandler useCaseHandler;
    
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody NewDto dto) {
        // implementation
    }
}
```

### 6.3 Run Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=BookEntityTest

# Run tests with coverage
mvn clean test jacoco:report
```

---

## 7. Troubleshooting

### Common Issues

| Issue | Solution |
|-------|----------|
| Test data builder circular references | Use `@JsonIgnore` or lazy loading for bidirectional relationships |
| Build fails with "Cannot resolve symbol" | Run `mvn clean install` to compile source code first |
| Database connection errors | Check `application.yaml` configuration |
| Jackson serialization issues | Ensure proper annotations on DTOs and entities |

### Debugging Tips
1. Enable debug logging: `logging.level.org.springframework=DEBUG`
2. Use breakpoint testing for complex object graphs
3. Check entity relationships in `BookPublisherEntity`

---

## 8. References

### Documentation
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [JPA/Hibernate Docs](https://hibernate.org/orm/documentation/)
- [Maven Documentation](https://maven.apache.org/guides/)

### Project-Specific
- `test-data-builder_pattern.md` - Pattern documentation in project root
- Project source code: `src/main/java/de/devtime/examples/library/`

---

## Quick Start

```bash
# Clone and build
git clone <repository-url>
cd episode-0004-test-data-builder-with-object-references
mvn clean install

# Run the application
mvn spring-boot:run

# Access API at
# http://localhost:8080/api/books
```
</response>
<task_progress>
- [x] Analyzed project structure from environment details
- [ ] Created comprehensive CLINE.md in .continue/rules/ directory
</task_progress>

I notice you're in **PLAN MODE**. To create the CLINE.md file, I need to use tools like `write_to_file` that are only available in **ACT MODE**.

Could you please **toggle to Act mode** using the Plan/Act toggle button below? Once switched, I'll create the comprehensive CLINE.md file for your project.
</plan_mode_respond>