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
**Requirements**
- Java 11 or higher
- Maven (for building the project) 
- PostgreSQL (if you are using it as a local database) or Postman
- Docker (optional, for containerisation)
- AWS Account (for Elastic Beanstalk deployment) 

To set up the Record Shop API on your local machine or deploy it to a server, follow the steps below:

**Clone the Repository**


`git clone https://github.com/ag-af/recordshop.git`


**If you are using PostgreSQL:**
- Install PostgreSQL and set up a database for the application
- Create a user for the database
- Update the application.properties file too configure the PostgreSQL connection

**If you are using AWS RDS:**
- Set up an RDS instance with PostgreSQL
- Update the application-rds.properties file with your RDS connection details

**Build the Project**

**Run the Application**

Once the application is running, you can access the Swagger UI to interact with the API: `http://localhost:8080/swagger-ui/index.html`. 

**Optional**

To run the application inside a Docker container, follow these steps:
- Build the Docker Image: `docker build -t recordshop:1.0 .`
- Run the Docker Container: `docker run -d -p 8080:8080 --name recordshop-container recordshop:1.0`
- Start the Container and expose the API at `http://localhost:8080`.

If you are deploying to AWS Elastic Beanstalk, follow these steps:
- Zip the project files for deployment, including the target/directory and any configuration files
- Log in to your AWS account and navigate to the Elastic Beanstalk service
- Create a new applicaction or update an existing one
- Upload the `recordshop-api.zip` file and deploy it to your Elastic Beanstalk environment
Once deployed, your API will be accessible via the Elastic Beanstalk URL.
  

# Usage

**API Endpoints**

GET /api/v1/albums - Get a list of all albums.

GET /api/v1/albums/{id} - Get a specific album by ID.

POST /api/v1/albums - Create a new album.

PUT /api/v1/albums/{id} - Update an existing album.

DELETE /api/v1/albums/{id} - Delete an album by ID.

# Testing
You can now interact with the Record Shop API using Postman or any other HTTP client. Make sure you set your base URL depending on whether you are running locally `http://localhost:8080` or on AWS Elastic Beanstalk `http://<your-aws-elastic-beanstalk-url>/swagger-ui/index.html`. 

You can test endpoints interactively and view response data. Swagger UI will also show you the expected input format, data models, and response formats. 

# Authentication

The API supports OAuth2 authentication using GitHub as the provider. Clients will need to authenticate before interacting with secure endpoints.

**Setting up OAuth2 with GitHub**

To enable GitHub authentication, follow these steps:

- Go to GitHub and create an OAuth application.
- Set the redirect URI to: `http://localhost:8080/login/oauth2/code/github`
- Update your application properties with the client ID and secret:
  
`spring.security.oauth2.client.registration.github.client-id=your-client-id`

`spring.security.oauth2.client.registration.github.client-secret=your-client-secret`

