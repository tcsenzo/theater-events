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


## User

### How to create one

- Json template:

```json
{
	"name": "Leonardo",
	"email": "leo@leo.com",
	"password": "123"
}
```

Example: 

```bash
 curl -H "Content-Type:application/json" -X POST http://localhost:8080/users --data "{\"name\": \"Leonardo\", \"email\": \"leo@leo.com\", \"password\": \"123\"}"
```

Possible responses:

- 202 - User created
- 400 - Invalid or insufficient data


## Login

After you create an user, you have to log in to be able to access any other resources

## How to

Example:

```bash
curl -X POST -c /tmp/cookies.txt  http://localhost:8080/login -d email=leo@leo.com -d password=123
```

This will create a file with your cookies at /tmp/cookies.txt, you will need to send this file in each request to prove you are authenticated

**All endpoints marked with (REQUIRES LOGIN) bellow will return 401 if you are not logged in**

## Theaters

### How to create one (REQUIRES LOGIN)

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
curl -b /tmp/cookies.txt -H "Content-Type:application/json" -X POST http://localhost:8080/theaters --data "{\"name\": \"Teatro NET SP\", \"address\": {	\"street\": \"Rua Olimpíadas\", \"number\": \"360\", \"district\": \"Vila Olimpia\", \"city\": \"São Paulo\", \"state\": \"SP\", \"country\": \"Brasil\", \"zip_code\": \"04551-000\"}}"
```

Possible responses:

- 202 - Theater created
- 400 - Invalid or insufficient data

### How to list all the theaters (REQUIRES LOGIN)

Example:

```bash
curl -b /tmp/cookies.txt http://localhost:8080/theaters
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

### How to create one (REQUIRES LOGIN)

```json
{
	"name": "LOVE IS IN THE AIR",
	"description": "Um evento para toda a familia",
	"price": "22.0",
	"available_quantity": 20,
	"scheduled_date": "2017-12-03T10:15:30",
	"theater": {
		"id": 1
	}
}
```

Example:

```bash
curl -b /tmp/cookies.txt -H "Content-Type:application/json" -X POST http://localhost:8080/events --data "{\"name\" : \"Evento maroto\", \"description\": \"Um evento para toda a familia\", \"price\": \"22.0\", \"available_quantity\": 20, \"scheduled_date\": \"2017-12-03T10:15:30\", \"theater\": {\"id\":1}}"
```

You can also send the theater information if it doesn't already exists on this service's database: (REQUIRES LOGIN)

```json
{
	"name": "LOVE IS IN THE AIR",
	"description": "Um evento para toda a familia",
	"price": "22.0",
	"available_quantity": "20",
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

- 202 - Event created
- 400 - Invalid or insufficient data
- 403 - Forbidden (the logged user is not the theaters owner)
- 404 - Theater not found


### How to list all the events

List example:
```bash
curl -b /tmp/cookies.txt http://localhost:8080/events
```

#### Optional parameter - hours_limit
Sets a limit in hours to the events scheduled_date


The response will be a json in as the one bellow:
```json
{
	"events": [{
		"id": 1,
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

### How to get a specific event

Example:
```bash
curl http://localhost:8080/events/<id>
```

The response will be a json in as the one bellow:
```json
{
	"id": 1,
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
}
```

### How to decrement events quantity

Example:
```bash
curl -X PUT http://localhost:8080/events/checkout/<id>
```


Possible responses:

- 200 - Event available and decremented with success

```json
{
	"id": 1,
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
}
```

- 400 - Invalid or insufficient data
- 404 - Not events with the specified id are available