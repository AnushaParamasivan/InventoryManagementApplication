# InventoryManagementApplication
Application to manage inventory of various products for various stores situated across the globe

Workflow:

1. Login has to be performed using username and password.  On succesful authentication, JWT token is returned which is to be used for subsequent requests.  Token expiration is set at 30 mins to avoid frequent logins.

2. Whenever an API is triggered with JWT token, say Upload store details API, authorization is performed at each API method based on the roles associated with the token.

3. On succesful authorization, the API flow continues and store details are read from the file uploaded and persisted.

4. The user can next upload product details for the stores already existing.  If a particular store mentioned for a product is not existing in database, uesr is notified about it to create the store first, before uploading product details for the store.

5. Once the products are uploaded succesfully, the user can go ahead with other find/update/delete of product details.

Design decision:

1. The application is developed using Spring boot, since the framework has production-ready features, and provides embedded server.

2. The developed application is for a retail stores chain with presence across the globe, so Product and Store entities are created separately.  Product has a ManyToOne relationship with Store.  Store entity has all details related to store, including location, id and name.

3. Granular APIs are exposed to handle different requests.

4. Global exception handler has been included, to handle various custom exceptions including ProductNotFoundException,NegativeProductPriceException,StoreNotFoundException etc.So that appropriate message is returned to the user related to the exception with proper status code, and all custom exceptions can be handled centrally in one location.

5. Cache is used for retrieve flows to improve performance, since the application has to support around 3000 stores across the globe.

6. Relational database is chosen, since the Product and Store entities has fixed schema, and also since inventory management has to adhere to ACID properties.

7. All update operations are performed within a transaction, to ensure ACID properties.


Non functional requirements considered:

Security: 
	-Basic RBAC is implemented, so that not all users can update a product.  Users with 'Admin' role can perform updates, whereas users with 'Staff' role can view all products.
	-Basic authentication is handled using JWT tokens.
	-User credentials are encoded and persisted in database for security reasons.
	  
Performance:
	-Caching is used for product read request, to improve performance.  On update, cache is evicted, so that subsequent request will fetch from database and update the cache.
	-Hikari is used for connection pooling mechanism.
	
	
Assumptions:

1. Only one ADMIN and one STAFF user has been created, on bean post construct, for demonstration purpose.  The logic can be extended to create more users.

2. hibernate.ddl-auto is set as CREATE, so that all tables will be created newly on server startup.  This can be set to UPDATE if required.

3. Only REST APIs are developed and included.

4. The deployment strategy, including compute capacity, storage, autoscalability, high availability, monitoring, shall be decided, based on the request volume, SLA, DR requirement.



