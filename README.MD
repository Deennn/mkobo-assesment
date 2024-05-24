### Requirements to run application
    Java 17
    postgresql

#### Clone repository to machine and set up postgresql with all credentials
        DB_DIALECT=org.hibernate.dialect.PostgreSQLDialect;
        DB_PASSWORD=admin;DB_TYPE=postgressql;
        DB_URL=jdbc:postgresql://localhost:5432/postgres?currentSchema=mkobo;
        DB_USERNAME=admin;DRIVER_CLASS_NAME=org.postgresql.Driver


#### Finally run
        mvn clean install

##### NB: DB credentials names and schema naming are upto you, you can choose to use the default schema

### postman collection url: 

    https://documenter.getpostman.com/view/27513331/2sA3QqfsXN