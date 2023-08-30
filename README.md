WebServer Video: https://youtu.be/HZeQwTzI6MI

MicroServices Video: https://youtu.be/0KyBL2Q7XZ8

    Introduction

This is the documentation for the design, implementation, and testing of an online shopping system. The system is designed using a layered architecture approach, with separate layers for the presentation, application, and data access logic. The presentation layer is implemented as a RESTful web server, while the application layer is implemented as microservices that can be accessed by clients via socket programming. The data access layer is implemented using a database management system.

This documentation provides a comprehensive guide to the design and implementation of each component of the system, as well as instructions on how to test each component individually.

    Design & Implementation

- Server Component:

The server component provides the following APIs:

- /users/<user_name>: Generates a webpage for user information.
- /users/all: Generates a webpage for all user information.
- /products/<product_id>: Generates a webpage for product information.
- /products/all: Generates a webpage for all product information.
- /orders/<order_id>: Generates a webpage for order information.
- /orders/all: Generates a webpage for all order information.

The web server component uses a Java web framework such as Spring or Jersey to handle HTTP requests and responses. The framework provides built-in classes and libraries to handle various web-related tasks such as routing, parsing request parameters, and generating response content.

For example, if a GET request is made to the /users/<username> endpoint, the web server will extract the <username> parameter from the request URL and use it to retrieve the user information from the database. Then, it will generate an HTML page containing the user information and send it as the response to the client.

The web server component can be designed to follow the Model-View-Controller (MVC) pattern, where the model represents the data layer (database), the view represents the user interface (HTML pages), and the controller acts as an intermediary between the two, handling the logic of processing user requests and generating responses.

---

- Microservices

The microservices are implemented as independent Java programs that communicate with the server component via REST APIs. Each microservice is responsible for one specific task.

- Product Information: Loads product information for a given product ID.
- Order Information: Loads information for a given order ID.
- Update Price: Updates the price of a product given its ID.
- Update Quantity: Updates the quantity of a product given its ID.
- Authentication: Checks if a pair of username and password is valid.
- Order Cancellation: Checks if a user can cancel an order given the username and order ID.


These microservices are tested with individual clients that connect via socket to the ServiceRegistry to set the service address and port. The clients then connect to the actual service file for the functionality, and the services send back the information to the client.

The microservices can be designed to follow the Single Responsibility Principle (SRP), where each microservice is responsible for performing a specific functionality, and is not concerned with other functionalities. This allows for better scalability and maintainability of the system.

---
- Database

To store user, product, and order information, you can use a relational database such as SQLite. You can create separate tables for users, products, orders, and other relevant information. The microservices can then communicate with the database to retrieve or update data.

---
- Architecture

The layered architecture for the online shopping system is a way of organizing the system's components into a set of layers that are responsible for different aspects of the system's functionality. This architecture separates the system's concerns into distinct layers, each with its own set of responsibilities and interfaces.

The layered architecture typically consists of three layers:

Presentation Layer: This is the topmost layer and is responsible for handling user interactions. It includes the user interface components, such as the web server component that generates web pages for user, product, and order information.

Application Layer: This layer sits between the presentation layer and the data layer and is responsible for implementing the system's business logic. It includes the microservices that provide the required functionality, such as loading product information, updating product price and quantity, and checking if a username and password combination exists in the database.

Data Layer: This is the bottommost layer and is responsible for managing the system's data. It includes the database and any related components, such as database access objects (DAOs).

---
- Service Registry:

The service registry is a central component that keeps track of the available microservices and their network locations. Clients can query the service registry to discover the location of the microservice they need to use.
The service registry is a key component of a microservices architecture. It serves as a central directory of available services, allowing clients to discover the location of the services they need.

When a microservice starts up, it registers itself with the service registry by providing its name, IP address, and port number. The service registry keeps track of all registered services and their locations.

When a client needs to use a particular microservice, it queries the service registry to obtain the location of the service. The client then uses this information to establish a connection with the microservice and perform the necessary actions.

The service registry can also be used to implement load balancing and failover mechanisms. By keeping track of multiple instances of the same service, the registry can direct client requests to the most available or reliable instance. If a service instance goes down, the service registry can detect the failure and redirect requests to another available instance.

---
- Clients:

The clients are implemented using Java socket programming. Each client connects to the service registry to discover the location of the required microservice. The client then establishes a socket connection to the microservice and sends the necessary input parameters to perform the required functionality.

For example, a client that wants to get the product information for a specific product might first query the service registry to discover the location of the ProductService microservice. Then, it establishes a socket connection to the ProductService microservice and sends the product ID as input parameter. The ProductService microservice processes the request and sends the product information as output back to the client.

Clients can also handle error and exception cases by checking the response from the microservice and taking appropriate actions. For example, if the ProductService microservice returns an error message indicating that the product ID is invalid, the client can display an error message to the user.

    Testing

Each microservice is tested individually using a test client. The test client is a program that connects to the service registry to discover the location of the microservice and then connects to the microservice to perform the required functionality. The test client sends the necessary parameters and verifies that the result is correct and within the expected range. This testing process can be automated using testing frameworks such as JUnit or TestNG.

The web server component is tested using a web browser or a tool such as Postman. The API endpoints are tested by sending HTTP GET requests with the required parameters and verifying that the response is correct. This can be done manually or automated using testing frameworks such as Selenium.

To test the online shopping system, you can use a combination of unit tests, integration tests, and end-to-end(system) tests. Unit tests can be used to test individual functions of each microservice, while integration tests can be used to test the interaction between microservices. End-to-end tests can be used to test the entire system, including the server component and microservices, to ensure that they work together correctly. 

    Conclusion

The system is tested using a combination of unit tests and integration tests. The previous project only had a GUI, server, and database. The GUI allowed the user to browse products, view their details, add them to the cart, and place an order. The server provided a RESTful API that allowed the GUI to interact with the database and retrieve product information, user information, and order information.

In this project, we have added new functionality to the existing system by introducing microservices and a new server component. The microservices provide specific functions to the online shopping system, such as loading product information, updating product price and quantity, checking user authentication, and order cancellation. The new server component is responsible for generating web pages for user, product, and order information.

By adding these new components, we have improved the scalability and maintainability of the system. The microservices allow us to easily add new functionality to the system without affecting other components. The new server component allows us to generate web pages dynamically, based on user requests, instead of relying solely on the GUI. This makes the system more versatile and accessible, as users can access it using any web browser instead of a standalone application.

Overall, this project builds on the previous one by enhancing the functionality of the system and improving its architecture, making it more scalable and maintainable.