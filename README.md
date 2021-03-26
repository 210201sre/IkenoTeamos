# IkenosTeamos 
__Project 2: Inventory Managment__ 

# Grocery Store Inventory Management System

## Project Description

Developed a grocery store inventory management system that allows the user to add items to the inventory as well as purchase items listed in the inventory. Provides net profit, pure sales and business expense metrics that are accessible through HTTP requests as well as logs.

## SLOs

## Technologies Used

* Spring Boot
* Spring Data
* JDBC
* Amazon RDS
* Kubernetes
* Docker
* Maven
* FluentD
* Grafana
* Loki
* Docker
* Log4J
* PostgreSQL
* Git

## Features

* All features are accessible via HTTP requests through the use of a RESTful API
* Add items to inventory
* Create users to make purchases
* Purchase items from inventory
* Obtain net profit
* Obtain pure sales
* Obtain business expenses
* All data is stored in an Amazon RDS
* Project image was built using Docker and deployed via Kubernetes

To-do list:

* Add more robust exception handling and data validation

## Getting started

Begin by cloning the repository into a desired folder

```git clone https://github.com/210201sre/husker-p1.git```

To run application locally, Loki, Grafana and FluentD agents must be started. This can be accomplished using Docker Desktop and the provided docker compose file inside the logging folder.

```
cd logging
docker-compose up -d
```

Once the agents are running, the application can be compiled as a Spring Boot application and ran using Spring Tool Suite 4. 

All features of the application are accessible via HTTP requests to a RESTful API that sends and receives JSON data.

### User features

Baseline user endpoint: localhost:8080/data/users

#### Create User

Send POST request with JSON formatted user data to: localhost:8080/data/users

Example JSON: ```
    {
        "name": "John Smith",
        "role": "Customer"
    }
    ```

#### Get all users

Send GET request to: localhost:8080/data/users 

#### Update user

Send PUT request with JSON formatted updated user data to: localhost:8080/data/users

Example JSON: ```
    {
        "userID": 5,
        "name": "John Doe",
        "role": "Customer"
    }
    ```
#### Get single user

Send GET request to: localhost:8080/data/users/{id}

Where {id} = the user ID of the user you want to get

#### Delete user

Send DELETE request with JSON formatted user ID for user to be deleted to: localhost:8080/data/users

Example JSON: ```
    { "userID":5
    }
    ```
### Item Features

Baseline item endpoint: localhost:8080/data/items

#### Create item

Send POST request with JSON formatted item data to: localhost:8080/data/items

Example JSON: ```
    {
        "itemName": "Steak",
        "itemType": "Meat",
        "storeBuyPrice": 3.99,
        "sellPrice": 12.99,
        "quantity": 6,
    }
    ```
#### Get all items

Send GET request to: localhost:8080/data/items

#### Get single item

Send GET request to: localhost:8080/data/items/{id}

Where {id} = the item ID of the item you want to get
