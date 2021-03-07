# message-sender-api

## Description

This REST API uses several providers to deliver messages to the specified mobile numbers. 
The provider is chosen by applying an LCR (Least Cost Routing) strategy based on the destination mobile phone. In case that many providers have the same costs, a random distribution of the load is applied among these providers.

## Core technologies

*Back-end*
- Spring Boot

*Unit Testing*
- JUnit
- Mockito

*Dependency management tool*
- Maven

*Containerization*
- Docker-compose

*Documentation*
- Swagger

## Build setup

- Clone this repo to your local machine. This application can be executed either with docker or with maven wrapper:

### With Docker

```
$ docker-compose up
```

### With Maven Wrapper

```
$ ./mvnw spring-boot:run
```

- Open your browser and test the application on *localhost:8080/swagger-ui.html*

## Validate test scenarios

#### Scenario 1
- Given the providers defined in the table of the exercise, iterate 10 message deliveries and show the providers used for the destination 0034666111222.
- **Expected result**: Given that there are 2 providers (P1 and P3) with the prefix 0034, and they both have the same cost (1), then a random distribution is applied for these two providers. So, most likely distribution will be 5 messages sent by P1 and 5 messages by P3.

#### Scenario 2
- Given the providers defined in the table of the exercise, iterate 10 message deliveries and show the providers used for the destination 0033777111222.
- **Expected result**: Given that there is only 1 provider (P3) with this prefix 0033, P3 will send the 10 messages.

### With Swagger

- Go to message-sender-controller endpoint on *localhost:8080/swagger-ui.html*
- Execute the requests below for both scenarios 10 times and verify that the actual result matches the expected result.

#### Scenario 1
POST http://localhost:8080/api/v1/message/send

**Request Body**
```json
{
  "textToSend": "text test",
  "toMobileNumber": "0034666111222"
}
```
**Expected response body** for approximately half of the requests should be something like:
```json
{
  "id": "33c415a4-5bf5-4426-b2d2-bc83abd2b8b1",
  "providerName": "P1"
}
```
And for the other half:
```json
{
  "id": "840c0f5e-f71a-44d2-93f8-dedd724f763a",
  "providerName": "P3"
}
```

#### Scenario 2
POST http://localhost:8080/api/v1/message/send

**Request Body**:
```json
{
  "textToSend": "text test",
  "toMobileNumber": "0033777111222"
}
```
**Expected Response body**:
```json
{
  "id": "88ba0a57-e202-48ac-aa2e-3ceda0ebb6ea",
  "providerName": "P3"
}
```

The mockup provider leaves a **log trace** like the one below at every request:
```java
 Message with text text test has been sent to mobile phone number 0034666111222
 Operation id: 88ba0a57-e202-48ac-aa2e-3ceda0ebb6ea, provider used: P3
```

### With Postman

- Import [this](https://www.getpostman.com/collections/9e4645b9a9ef475846c2) Postman collection, which contains the request, and the automated tests to validate both scenarios.
- Go to **Postman Runner** (upper-left corner, next to Import button), choose the imported collection Message Sender and set the number of Iterations to 10.

#### Scenario 1
- Import *sendMessageJSONPayloadScenario1.json* file (present in this [zip](https://drive.google.com/file/d/1d-XQMp8gtvyJ5qSaWT6RyFZsem0nRG2O/view?usp=sharing)), which contains the example request bodies for the Scenario 1.
- Click on Start Run and verify the results.

#### Scenario 2
- Import *sendMessageJSONPayloadScenario2.json* file (present in this [zip](https://drive.google.com/file/d/1d-XQMp8gtvyJ5qSaWT6RyFZsem0nRG2O/view?usp=sharing)), which contains the example request bodies for the Scenario 2.
- Click on Start Run and verify the results.

### Unit tests

```
# Run the unit tests that cover web and service layers

$ ./mvnw test
```


