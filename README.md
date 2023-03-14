# getting-started project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

This project is the result of following the [Quarkus getting started guide](https://quarkus.io/guides/getting-started).

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Quarkus Hello World Live coding
### Project generation and run

1. Generate project
```bash
mvn io.quarkus.platform:quarkus-maven-plugin:2.16.2.Final:create \
    -DprojectGroupId=org.acme \
    -DprojectArtifactId=getting-started-on-quarkus-demo \
    -Dextensions="resteasy"

```
1. Navigate to the directory and launch the application
```bash
    cd getting-started-on-quarkus-demo
    mvn compile quarkus:dev
```
1. Open browser to http://localhost:8080
1. Open browser to http://localhost:8080/hello
1. Change the greeting message in the `HelloResource`, refresh browser

### Add method

1. Add method:
```java
    @GET
@Produces(MediaType.TEXT_PLAIN)
@Path("/city")
public String city() {
        return "Madrid";
        }
```
1. Open browser to http://localhost:8080/hello/city

### Configuration

1. In the resource, add
```java
    @ConfigProperty(name = "greeting")
    String greeting;
```
3. Change the hello method to return the greeting message:
```java
@GET
@Produces(MediaType.TEXT_PLAIN)
public String hello() {
        return greeting + " world";
        }
```    
1. Open browser to http://localhost:8080/hello
1. We get an error because we have not added the property `greeting` to the configuration file
1. Open the `application.properties` file and add:
```properties
    greeting=Hola
``` 
1. Refresh browser
1. In the resource class, add an `Optional<String>`:
```java
    @ConfigProperty(name = "city") 
    Optional<String> city;
```
1. Replace the `city` method with:
```java
    @GET
@Produces(MediaType.TEXT_PLAIN)
@Path("/city")
public String city() {
        return city.orElse("Barcelona");
        }
```
1. Open browser to http://localhost:8080/hello/city

### Introduce a bean

1. Create class `GreetingService` in the `org.acme` package with the following content:
```java
    @ApplicationScoped
    public class GreetingService {

    @ConfigProperty(name="greeting")
    String greeting;

    public String greeting(String name){
        return  greeting +" "+name;
    }
```            
2. Update the content of `HelloResource` to become:
```java
package org.acme;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/hello")
public class GreetingResource {

    @ConfigProperty(name = "city")
    Optional<String> city;

    @Inject
    GreetingService greetingService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{name}")
    public String hello(@PathParam("name") String name) {
        return greetingService.greeting(name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/city")
    public String city() {
        return city.orElse("Barcelona");
    }
}
```    
3. Open browser to http://localhost:8080/hello/quarkus

### Testing

The generated project contains a simple test. Edit the src/test/java/org/acme/GreetingResourceTest.java to match the following content with the last modifications:

```java
package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello/quarkus")
          .then()
             .statusCode(200)
             .body(is("Hola quarkus"));
    }

}
```
You can run these using Maven:

```shell
./mvnw test
```

## Generating JAX-RS resources with Panache

A lot of web applications are monotonous CRUD applications with REST APIs that are tedious to write. 
REST Data with Panache extension can generate the basic CRUD endpoints for your entities and repositories.

### Setting up REST Data with Panache
Add the required dependencies to your build file

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-hibernate-orm-rest-data-panache</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jdbc-postgresql</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-resteasy-reactive-jackson</artifactId>
</dependency>

```
Hibernate ORM REST Data with Panache extension, a JDBC postgresql driver extension and the RESTEasy reactive JSON serialization extensions.

### Implement the Panache entities and/or repositories

We will use the active record pattern. Create the `Book` entity and annotate it with `@Entity` as follows: 

````java
package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Book extends PanacheEntity {

    public String name;
    public Integer publicationYear;
}
````

### Define the interfaces for generation

REST Data with Panache generates JAX-RS resources based on the interfaces available in your application. 
For that, we need to create the following interface: 

````java
package org.acme;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface BookResource extends PanacheEntityResource<Book, Long> {
}
````

The generated resources will provide the CRUD methods to access your `Book` entity. 

### Set up the data base and some data in it

Open the `application.properties` file and add database access configuration
````properties
    quarkus.datasource.jdbc.url=jdbc:postgresql:quarkus_test
    quarkus.datasource.db-kind=postgresql
    quarkus.datasource.username=quarkus_test
    quarkus.datasource.password=quarkus_test
    quarkus.datasource.jdbc.min-size=2
    quarkus.datasource.jdbc.max-size=8
````

Note that this step is optional in `dev` mode.

1. Add database population script `import.sql` in resources folder with the following content
````properties
    INSERT INTO book(id, name, publicationYear) VALUES (1, 'Sapiens' , 2011);
    INSERT INTO book(id, name, publicationYear) VALUES (2, 'Homo Deus' , 2015);
    INSERT INTO book(id, name, publicationYear) VALUES (3, 'Enlightenment Now' , 2018);
    INSERT INTO book(id, name, publicationYear) VALUES (4, 'Factfulness' , 2018);
    INSERT INTO book(id, name, publicationYear) VALUES (5, 'Sleepwalkers' , 2012);
    INSERT INTO book(id, name, publicationYear) VALUES (6, 'The Silk Roads' , 2015);
````

1. Configure the loading of data adding the following properties in the `application.properties` file
```properties
    quarkus.hibernate-orm.database.generation=drop-and-create
    quarkus.hibernate-orm.sql-load-script=import.sql
```

Note that this step is optional in `dev` mode.

1. At last, start a postgresql database by running the following command:

````properties
    docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name quarkus_test -e POSTGRES_USER=quarkus_test -e POSTGRES_PASSWORD=quarkus_test -e POSTGRES_DB=quarkus_test -p 5432:5432 postgres:14.5
````

Note that this step is optional in `dev` mode.

1. Open browser to http://localhost:8080/book


## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces several outputs:
- the `getting-started-on-quarkus-demo-1.0-SNAPSHOT.jar` file in the `/target` directory.
- the quarkus-app directory which contains the `quarkus-run.jar`

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

1. Install GraalVM if you haven’t already.
2. Configure the runtime environment. Set GRAALVM_HOME environment variable to the GraalVM installation directory, for example:
```shell
export GRAALVM_HOME=$HOME/Development/graalvm/
```
3. Add the GraalVM bin directory to the path
```shell
export PATH=$GRAALVM_HOME/bin:$PATH
```

You can create a native executable using: `./mvnw package -Pnative`.
You can then execute your native executable with: `./target/getting-started-on-quarkus-demo-1.0-SNAPSHOT-runner`

Or you can run the native executable build in a container.
Build the docker image with `docker build -f src/main/docker/Dockerfile.native -t $USER/getting-started .`
Finally, run the container using `docker run -i --rm -p 8080:8080 $USER/getting-started`

Note: don't forget to replace $USER by your own

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.


## Deploy to Kubernetes
config:

````properties

# Kubernetes Client
quarkus.kubernetes-client.trust-certs=true
quarkus.kubernetes-client.namespace=aurins

# Expose the service when deployed
quarkus.kubernetes.ingress.expose=true

quarkus.container-image.registry=image-registry.openshift-image-registry.svc:5000
quarkus.container-image.group=auri
quarkus.container-image.tag=1.0-SNAPSHOT
````