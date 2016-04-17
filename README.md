## How to setup

- Import the project as an Existing Maven Project at eclipse
- Import base.sql into your theaterEvents database:
```
mysql -u root theaterEvents < base.sql
``` 


## How to run

- If you have the project imported, just run the class com.senzo.qettal.theaterEvents.Application 
- If you don't, run 'mvn spring-boot:run' at your terminal

## How to test

- If you don't have an application to test with, use curl as bellow:

Post example:

```
 curl -H "Content-Type:application/json" -X POST http://localhost:8080/events/create --data "{\"name\" : \"Evento maroto\", \"description\": \"Um evento para toda a familia\", \"price\": \"22.0\"} "
```

Get example:
```
curl http://localhost:8080/events/
```
