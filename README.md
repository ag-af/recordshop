# Record Shop API

**Overview** 

The Record Shop API is a RESTful API designed to manage an inventory system for a record store. 
It allows for operations such as adding, updating, searching, and deleting albums. 

The API provides endpoints to search for albums by various filters and enables interaction with the inventory, including stock management and pricing. 
The application supports error handling and includes detailed API documentation via Swagger UI.

This API is built using Spring Boot, and deployed on AWS Elastic Beanstalk. It includes OAuth2 integration for authentication, Spring Actuator for monitoring, and handles both happy and unhappy paths through custom error handling.

# Features
- Create, read, update, and delete (CRUD) operations for albums.
- OAuth2 authentication for secure access to the API.
- Swagger UI for interactive API documentation.
- Custom error handling for validation failures and resource not found errors.
- Spring Actuator integration for application health and metrics.
- Search functionality by album attributes (title, artist, genre, etc.).

# Example Input JSON
**Create a New Album (POST request)** 


 
	{

        "title": "Thriller",
        
        "artist": "Michael Jackson",
        
        "genre": "POP",
        
        "releaseYear": 1982,
        
        "price": 15.99,
        
        "stock": 30
				
    }

# Installation

To set up the Record Shop API locally, follow the steps below:

**Clone the Repository**


git clone https://github.com/ag-af/recordshop.git

**Build the Project**

Ensure you have Maven installed

**Run the Application**

**Docker Setup To build**


docker build -t recordshop:1.0 .

**Docker Run**


docker run -d -p 8080:8080 --name recordshop-container recordshop:1.0

**Deploy on AWS**


Package the project into a ZIP file:
zip -r recordshop-api.zip *

**Deploy the ZIP to AWS Elastic Beanstalk using the AWS console.**

# Usage

**API Endpoints**

GET /api/v1/albums - Get a list of all albums.

GET /api/v1/albums/{id} - Get a specific album by ID.

POST /api/v1/albums - Create a new album.

PUT /api/v1/albums/{id} - Update an existing album.

DELETE /api/v1/albums/{id} - Delete an album by ID.

# Swagger UI
To explore the API documentation, navigate to:
http://localhost:8080/swagger-ui/index.html

Here, you can test endpoints interactively and view the data schemas.

# Authentication

The API supports OAuth2 authentication using GitHub as the provider. Clients will need to authenticate before interacting with secure endpoints.

**Setting up OAuth2 with GitHub**
To enable GitHub authentication, follow these steps:

- Go to GitHub and create an OAuth application.
- Set the redirect URI to: http://localhost:8080/login/oauth2/code/github
- Update your application properties with the client ID and secret:
  
spring.security.oauth2.client.registration.github.client-id=your-client-id

spring.security.oauth2.client.registration.github.client-secret=your-client-secret

