# potato-market (Rest)

# Table of Contents
1. How to
    1. [Env](#markdown-header-env)
    2. [Build and Run](#markdown-header-build-run)
    3. [API usage](#markdown-api-usage)
    4. [Logs](#markdown-logs)
    5. [Debug](#markdown-debug)

# How To

## Env <a name="markdown-header-env"></a>

-   Ensure that the following is installed;
    -   Java 8 JDK
    -   Maven (min 3.5)
    -   GIT
    -   curl or Postman

## Build and Run <a name="markdown-header-build-run"></a>

-   Check out/Clone the Project
-   Run `mvn clean install` on the **potato-market (project parent)** pom
-   Go to **potato-rest/target** dir
-   Run `java -jar potato-rest-1.0.0-SNAPSHOT.jar`

## API usage <a name="markdown-api-usage"></a>

Basic sample of how to use:
```bash
curl -XPOST --header "Content-Type: application/json" --data '{"potatosNumber":"10","packedDate":"2018-08-08 12:08", "price":"10.25", "bagSupplier":"De Coster"}' http://localhost:8080/potato-bags
curl -XPOST --header "Content-Type: application/json" --data '{"potatosNumber":"10","packedDate":"2018-08-08 12:08", "bagSupplier":"De Coster","price":"10.1321325"}' http://localhost:8080/potato-bags
curl -XGET --header "Content-Type: application/json" http://localhost:8080/potato-bags?limit=10
```

## Logs <a name="markdown-logs"></a>

log file **potato-market.logs** is located at the same location as **potato-rest-1.0.0-SNAPSHOT.jar**

## Debug <a name="markdown-debug"></a>
To be able to debug app use
```bash
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8001,suspend=y -jar potato-rest-1.0.0-SNAPSHOT.jar
```