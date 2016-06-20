## How to setup

- Import the project as an Existing Maven Project at eclipse
- Import base.sql into your theaterEvents database:
```bash
mysql -u root theaterEvents < base.sql
``` 
## How to run

- If you have the project imported, just run the class com.senzo.qettal.theaterEvents.EventsApplication 
- If you don't, run 'mvn spring-boot:run' at your terminal

## How to test

 If you don't have an application to test with, use curl sending a json with the structure provided bellow:

## Theaters

### How to create one

- Json template:

```json
{
	"name": "Teatro NET SP",
	"address": {
		"street": "Rua Olimpíadas",
		"number": "360",
		"district": "Vila Olimpia",
		"city": "São Paulo",
		"state": "SP",
		"country": "Brasil",
		"zip_code": "04551-000"
	}
}
```

Example:

```bash
curl -H "Content-Type:application/json" -X POST http://localhost:8080/theaters/create --data "{\"name\": \"Teatro NET SP\", \"address\": {	\"street\": \"Rua Olimpíadas\", \"number\": \"360\", \"district\": \"Vila Olimpia\", \"city\": \"São Paulo\", \"state\": \"SP\", \"country\": \"Brasil\", \"zip_code\": \"04551-000\"}}"
```

Possible responses:

202 - Theater created
400 - Invalid or insufficient data

### How to list all the theaters

Example:

```bash
curl http://localhost:8080/theaters
```

The response will be a json in as the one bellow:
```json
{
	"theaters": [{
		"name": "Teatro NET SP",
		"address": {
			"street": "Rua Olimpíadas",
			"number": "360",
			"district": "Vila Olimpia",
			"city": "São Paulo",
			"state": "SP",
			"country": "Brasil",
			"zip_code": "04551-000"
		}
	}]
}
```

## Events

### How to create one

```json
{
	"name": "LOVE IS IN THE AIR",
	"description": "Um evento para toda a familia",
	"price": "22.0",
	"scheduled_date": "2017-12-03T10:15:30",
	"theater": {
		"id": 1
	}
}
```

Example:

```bash
curl -H "Content-Type:application/json" -X POST http://localhost:8080/events/create --data "{\"name\" : \"Evento maroto\", \"description\": \"Um evento para toda a familia\", \"price\": \"22.0\", \"scheduled_date\": \"2017-12-03T10:15:30\", \"theater\": {\"id\":1}}"
```

You can also send the theater information if it doesn't already exists on this service's database:

```json
{
	"name": "LOVE IS IN THE AIR",
	"description": "Um evento para toda a familia",
	"price": "22.0",
	"scheduled_date": "2017-12-03T10:15:30",
	"theater": {
		"name": "Teatro NET SP",
		"address": {
			"street": "Rua Olimpíadas",
			"number": "360",
			"district": "Vila Olimpia",
			"city": "São Paulo",
			"state": "SP",
			"country": "Brasil",
			"zip_code": "04551-000"
		}
	}
}
```

Possible responses:

202 - Event created
404 - Theater not found
400 - Invalid or insufficient data

### How to list all the events

List example:
```bash
curl http://localhost:8080/events
```

The response will be a json in as the one bellow:
```json
{
	"events": [{
		"name": "LOVE IS IN THE AIR",
		"description": "Um evento para toda a familia",
		"price": 22.0,
		"theater": {
			"name": "Teatro NET SP",
			"address": {
				"street": "Rua Olimpíadas",
				"number": "360",
				"district": "Vila Olimpia",
				"city": "São Paulo",
				"state": "SP",
				"country": "Brasil",
				"zip_code": "04551-000"
			}
		},
		"scheduled_date": "2017-12-03T10:15:30"
	}]
}
```

