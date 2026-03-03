# 🏥 Hospital Management REST API

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4-brightgreen?style=for-the-badge&logo=spring-boot)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-green?style=for-the-badge&logo=spring-security)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?style=for-the-badge&logo=mysql)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-85EA2D?style=for-the-badge&logo=swagger)

A robust, highly secure, and fully documented RESTful API built to manage modern hospital operations. This backend handles the entire clinical workflow, from patient registration and secure authentication to doctor scheduling and medical record management.

## ✨ Key Technical Achievements

I built this API to demonstrate enterprise-level backend architecture, focusing heavily on data integrity and security:

* **🔐 Advanced JWT Authentication Flow:** Implemented a stateless authentication system using short-lived Access Tokens (15 mins) and database-backed, long-lived Refresh Tokens (7 days) for a seamless and secure user experience.
* **🏗️ "Shared ID" Database Architecture:** Engineered a relational schema where specific clinical roles (`patients`, `doctors`, `secretaries`) share primary keys with a central `users` table. This enforces strict referential integrity and prevents role-mixing (e.g., a Secretary cannot be booked for a medical appointment).
* **🛡️ Granular Role-Based Access Control (RBAC):** Leveraged Spring Security's `@PreAuthorize` to secure endpoints. Patients can only fetch their own medical history, Doctors manage their specific daily schedules, and Secretaries manage global appointments.
* **🚦 Global Exception Handling:** Designed a `@RestControllerAdvice` layer to intercept runtime exceptions (e.g., `PatientNotFoundException`, JWT expirations) and return clean, standardized JSON error responses instead of default stack traces.
* **📖 Interactive API Documentation:** Integrated Springdoc OpenAPI 3 (Swagger UI) with Bearer Token support, allowing developers and frontend teams to test secured endpoints directly in the browser.

## 🛠️ Tech Stack

* **Core:** Java, Spring Boot 3
* **Data Access:** Spring Data JPA, Hibernate, MySQL
* **Security:** Spring Security, JSON Web Tokens (JJWT), BCrypt Password Hashing
* **API Documentation:** Swagger UI / OpenAPI 3
* **Tools:** Maven, Postman (Automated Environment Variables)

## 🗄️ Core Domain Logic

The system is built around a strict workflow:
1. **Identity Management:** Users register with an email/password and are assigned a specific `UserRole`.
2. **Profile Completion:** A specialized entity (Doctor, Patient) is created, sharing the User's ID.
3. **Clinical Workflow:** Secretaries create `Appointments` linking a `doctor_id` to a `patient_id`. 
4. **Consultation:** Doctors complete the appointment, which automatically generates a `MedicalRecord` tied to the patient's history.

## 🚀 Getting Started

### Prerequisites
* JDK 17 or higher
* MySQL Server (running on port 3306)
* Maven

### Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/your-username/your-repo-name.git](https://github.com/your-username/your-repo-name.git)
   cd your-repo-name
