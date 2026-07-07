# E-Banking Application

## Developer
HYNDI ELMEHDI

## Overview
This is a full-stack Spring Boot and JPA enterprise application designed to manage banking operations, customers, and bank accounts. The application handles current accounts (with over-draft limits) and savings accounts (with interest rates), as well as logging debits, credits, and money transfer (virement) transactions.

---

## Architecture and Workflow

The application follows a standard layered enterprise architecture:
1. **Presentation Layer (REST Controllers)**: Exposes endpoints for managing customers, bank accounts, and executing transactions.
2. **Service Layer (Business Logic)**: Coordinates transactional operations, implements business rules (such as checking if a customer has a sufficient balance before a debit), and maps entities to Data Transfer Objects (DTOs) for secure and efficient serialization.
3. **Repository Layer (DAO)**: Interfaces with the database using Spring Data JPA.
4. **Entity Layer (Database Mappings)**: Defines the persistence model mapped to H2 Database tables.

### Inheritance Mapping Strategy
The application manages two types of accounts: `CurrentAccount` and `SavingAccount`. Both inherit from the abstract class `BankAccount`. This is mapped in the database using a **Single Table Inheritance** (`InheritanceType.SINGLE_TABLE`) strategy:
* A single table named `bank_account` stores all account information.
* A discriminator column (`TYPE`) distinguishes whether an account is a Current Account (`CA`) or a Savings Account (`SA`).
* Relationships are mapped so that each account belongs to a `Customer` and contains a collection of `AccountOperation` records representing transactions.

---

## Database Schema

At startup, Hibernate automatically generates the database schema in the in-memory H2 database based on the JPA entities.

![Database Tables Schema](snapshots/tables-schema-on-h2-db.png)

---

## Features and Implementations

### 1. Customer Management (CRUD)
Exposes complete REST endpoints to manage customer profiles:
* **Create Customer**: `POST /customers`
* **Read Customers**: `GET /customers` and `GET /customers/{id}`
* **Update Customer**: `PUT /customers/{customerId}`
* **Delete Customer**: `DELETE /customers/{id}`

#### Customer Operations in Postman:
* **Creating a Customer**:
![Create Customer](snapshots/create-customer-postman.png)

* **Retrieving All Customers**:
![Get All Customers](snapshots/get-all-customers-on-postman.png)

* **Updating a Customer**:
![Update Customer](snapshots/update-customer-by-id-postman.png)

* **Deleting a Customer**:
![Delete Customer](snapshots/delete-customer-by-id-postman.png)

---

### 2. Bank Account Management
Exposes REST endpoints to fetch bank accounts:
* **Get Account Details**: `GET /accounts/{accountId}` (returns a Current or Savings Account DTO depending on the type)
* **List All Accounts**: `GET /accounts`

---

### 3. Transaction Operations
Implements core transactional operations:
* **Debit**: `POST /accounts/debit` (Subtracts an amount from the account balance and records a DEBIT transaction. It performs a business validation check to ensure the balance is sufficient).
* **Credit**: `POST /accounts/credit` (Adds an amount to the account balance and records a CREDIT transaction).
* **Transfer (Virement)**: `POST /accounts/transfer` (Executes a debit on the source account and a credit on the destination account within a single transaction).

---

### 4. Account Operations History & Pagination
To avoid returning large volumes of transaction data at once, the history endpoint implements server-side pagination:
* **Endpoint**: `GET /accounts/{accountId}/operation-pages?page={page}&size={size}`
* Returns an `AccountHistoryDTO` containing the current page index, total pages, page size, account balance, and the list of paginated `AccountOperationDTO` items.

![Account Operations Pagination](snapshots/account-operations-pagination.png)

---

### 5. API Documentation (Swagger / OpenAPI)
Integrated `springdoc-openapi` to automatically inspect controllers and generate interactive documentation.
* **Swagger UI Endpoint**: `http://localhost:8085/swagger-ui/index.html`
* **OpenAPI Specs (JSON)**: `http://localhost:8085/v3/api-docs`

#### Swagger Interface:
![Swagger Web UI](snapshots/swagger-web-ui.png)
