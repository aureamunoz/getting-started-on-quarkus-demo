# getting-started project

This project uses Quarkus, the Supersonic Subatomic Java Framework.
This project has been used to present Quarkus in Codemotion Madrid 2019.
The slides are available [here](Codemotion 2019 Descubriendo Quarkus, java sub-atómico en acción.pdf).

This project is the result of following the [Quarkus getting started guide](https://quarkus.io/guides/getting-started).

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `getting-started-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/getting-started-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/getting-started-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Quarkus Hello World Live coding
### Project generation and run

1. Generate project
```bash
mvn io.quarkus:quarkus-maven-plugin:1.5.2.Final:create \
    -DprojectGroupId=org.acme \
    -DprojectArtifactId=getting-started \
    -DclassName="org.acme.getting.started.GreetingResource" \
    -Dpath="/hello"

```
1. Navigate to the directory and launch the application
```bash
cd getting-started
mvn compile quarkus:dev
```
1. Open browser to http://localhost:8080
1. Open browser to http://localhost:8080/hello
1. Change the greeting message in the `HelloResource`, refresh browser

### Add method

1. Add method: 
    ```
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/city")
    public String city() {
        return "Madrid";
    }
    ```
1. Open browser to `http://localhost:8080/hello/city`

### Configuration

1. In the resource, add 
    ```
    @Inject @ConfigProperty(name = "greeting") String greeting;
    ```
1. Change the hello method to return the greeting message:
    ```
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return greeting;
    }
    ```    
1. Open browser to `http://localhost:8080/hello
1. We get an error because we have not added the property `greeting` to the configuration file
1. Open the `application.properties` file and add:
    ```
    greeting = hola
    ``` 
1. Refresh browser
1. In the resource class, add an `Optional<String>`:
    ```
    @Inject @ConfigProperty(name = "city") Optional<String> city;
    ```
1. Replace the `city` method with:
    ```
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/city")
    public String city() {
        return city.orElse("Barcelona");
    }
    ```
1. Open browser to `http://localhost:8080/hello/city`

### Introduce a bean

1. Create class `MyBean` in the `org.acme.quickstart` package with the following content:
    ```
    @ApplicationScoped
    public class MyBean {
    
        @Inject @ConfigProperty(name = "greeting") String greeting;
    
        public String greeting() {
            return greeting;
        }
    }
    ```            
2. Update the content of `HelloResource` to become:
    ```
    @Inject MyBean bean;
       // ...
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return bean.greeting();
    }
    ```    
3. Open browser to `http://localhost:8080/hello`

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `spring-on-quarkus-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.

The application is now runnable using `java -jar target/spring-on-quarkus-1.0-SNAPSHOT-runner.jar`.

### Native packaging

1. Exit application (`CTRL+C` in the terminal)
1. Launch `mvn clean package -Pnative`    
1. Run application: getting-started-1.0-SNAPSHOT-runner

### Linux executable creation

1. Exit application (`CTRL+C` in the terminal)    
1. Run: ` mvn clean package -Pnative -Dnative-image.docker-build=true`
1. While building, explain the architecture issue
1. While building, explain the Docker file (native)


