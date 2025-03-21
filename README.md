# Spring Boot Animal Image Service

## 📌 Description
This project is a **Spring Boot microservice** that fetches and stores images of animals (cats, dogs, and bears) using public APIs. The application automatically retrieves images and stores them in an **H2 database**. It exposes REST APIs to **fetch the last stored image** and allows users to interact through a web UI.

---

## 📌 Endpoints Used (External APIs)
The service fetches images from the following sources:
- **Cats:** [The Cat API](https://api.thecatapi.com/v1/images/search)
- **Dogs:** [Random Dog API](https://random.dog/woof.json)
- **Bears:** [PlaceBear](https://placebear.com/)

---

## 📌 Endpoints Exposed (REST APIs)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/animals/fetch?type={animal}&count={num}` | Fetch and store `{num}` images for `{animal}` in DB |
| `GET`  | `/api/animals/last?type={animal}&count={num}` | Retrieve the last `{num}` stored images of `{animal}` |
| `GET`  | `/api/animals/last-stored?type={animal}` | Retrieve the last stored image of `{animal}` |

---

## 📌 How to Run the Application

### Note: Please run this project using JDK 17.

### 🚀 Running via Spring Boot (Local Development)
1. **Clone the Repository:**
   ```bash
   git clone <repository_url>
   cd springboot-animal-image-service
   ```
2. **Build the Project:**
   ```bash
   mvn clean install
   ```
3. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```
4. **Access the UI:**  
   Open in browser: [http://localhost:8080/index.html](http://localhost:8080/index.html)  
5. **Test REST APIs using Postman or Curl.**
   ```bash
   curl -X GET "http://localhost:8080/api/animals/last-stored?type=cat"
   ```

---

### 🐳 Running with Docker
1. **Build the Docker Image:**
   ```bash
   docker build -t springboot-animal-service .
   ```
2. **Run the Docker Container:**
   ```bash
   docker run -p 8080:8080 springboot-animal-service
   ```
3. **Verify the Application is Running:**
   ```bash
   curl -X GET "http://localhost:8080/api/animals/last-stored?type=cat"
   ```
4. **Access the UI:**  
   Open in browser: [http://localhost:8080/index.html](http://localhost:8080/index.html)

**Note: Follow these steps if port 8080 is already in use.**
```bash
   lsof -i :8080
   ```
**You'll see output like:**
```bash
   java    12345 rajsubburaj  ... TCP *:8080 (LISTEN)
   ```
**Take note of the PID (process ID) — in this case, 12345.**
**To stop it, run:**
```bash
   kill -9 12345
   ```
---

## 📌 How to Access H2 Console
1. **Access H2 Console in Browser:**  
   Open: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

2. **Enter Database Credentials:**
   - **JDBC URL:** `jdbc:h2:mem:testdb`
   - **Username:** `sa`
   - **Password:** (leave blank)
   - Click **Connect**

3. **Query Stored Images:**
   ```sql
   SELECT * FROM ANIMAL_IMAGE;
   ```

---

## 📌 Running Test Cases
The project includes **JUnit test cases** for:
- **Service Layer:** Validates image fetching & storing logic.
- **Controller Layer:** Ensures APIs return expected responses.

**Run tests using:**  
```bash
mvn test
```

---

## 📌 Author
Developed by **Raj** 🚀  
