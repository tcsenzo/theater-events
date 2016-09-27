## How to deploy

### Locally
- harbor

### At ec2
- If the mysql isnt running:

```bash
docker run --name mysql -e MYSQL_DATABASE=theaterEvents -e MYSQL_ROOT_PASSWORD=<password> -v /mysql:/var/lib/mysql -d mysql/mysql-server --character-set-server=utf8 --collation-server=utf8_general_ci

```

- Pull the new version 

```bash
docker pull leocwolter/theater-events
```

- Stop and remove the running container
```
docker stop theater-events
docker rm theater-events
```

- Run the container:

```bash
docker run --name events -p 8080:8080 --link mysql:mysql -d leocwolter/theater-events
```

## How to setup

- Import the project as an Existing Maven Project at eclipse
- Import base.sql into your theaterEvents database:

```bash
mysql -u root theaterEvents < base.sql
``` 
- Copy the application.properties.sample:

```bash
cp src/main/resources/application.properties.sample src/main/resources/application.properties
```

- Set the amazon properties

## How to run

- If you have the project imported, just run the class com.senzo.qettal.theaterEvents.EventsApplication 
- If you don't, run 'mvn spring-boot:run' at your terminal

## How to test

 If you don't have an application to test with, use curl sending a json with the structure provided bellow:


## Login

See: https://github.com/tcsenzo/authentication/blob/master/README.md 

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


### How to update one (REQUIRES LOGIN)

- Json template: the same above

Example:

```bash
curl -b /tmp/cookies.txt -H "Content-Type:application/json" -X PUT http://localhost:8080/theaters/1 --data "{\"name\": \"Teatro Editado\", \"address\": {	\"street\": \"Rua Editada\", \"number\": \"361\", \"district\": \"Vila Editada\", \"city\": \"São Editado\", \"state\": \"SP editado\", \"country\": \"Brasil editado\", \"zip_code\": \"04551-001\"}}"
```

Possible responses:

- 200 - Theater updated
- 400 - Invalid or insufficient data
- 403 - Forbidden (the logged user is not the theaters owner)
- 404 - Theater not found

### How to list the theaters created by me? (REQUIRES LOGIN)

Example:

```bash
curl -b /tmp/cookies.txt http://localhost:8080/theaters
```

The response will be a json in as the one bellow:

```json
{
	"theaters": [{
        "id": 1,
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

### How to get details about one

Example:

```bash
curl -b /tmp/cookies.txt http://localhost:8080/theaters/1
```

The response will be a json in as the one bellow:

```json
{
    "id": 1,
    "name": "Teatro NET SP",
    "address": {
        "city": "S\u00e3o Paulo",
        "country": "Brasil",
        "district": "Vila Olimpia",
        "number": "360",
        "state": "SP",
        "street": "Rua Olimp\u00edadas",
        "zip_code": "04551-000"
    },
    "events": [
        {
            "available_quantity": 980,
            "description": "Um evento para toda a familia",
            "half_price": 11.0,
            "id": 1,
            "name": "Evento maroto",
            "original_price": 50.0,
            "price": 22.0,
            "scheduled_date": "2017-12-03T10:15:30Z"
        },
        {
            "available_quantity": 1000,
            "description": "Um evento para toda a familia",
            "half_price": 11.0,
            "id": 2,
            "name": "Evento maroto",
            "original_price": 50.0,
            "price": 22.0,
            "scheduled_date": "2017-12-03T10:15:30Z"
        },
        {
            "available_quantity": 20,
            "description": "Um evento para toda a familia",
            "half_price": 11.0,
            "id": 3,
            "name": "Evento maroto",
            "original_price": 50.0,
            "price": 22.0,
            "scheduled_date": "2017-12-03T10:15:30Z"
        },
        {
            "available_quantity": 20,
            "description": "Um evento para toda a familia",
            "half_price": 11.0,
            "id": 4,
            "name": "Love is in the air",
            "original_price": 50.0,
            "price": 22.0,
            "scheduled_date": "2016-08-22T22:15:30Z"
        }
    ]
}

```

## Events

### How to create one (REQUIRES LOGIN)

First you need to upload the events image, you can use the following endpoint to do so

```bash
curl -b /tmp/cookies.txt -X POST -H "Content-type: multipart/form-data" http://localhost:8080/events/image -F "image=@<path_for_your_image>"
```

This will return the uploaded image name, which will be used to create the actual event


```json
{
	"name": "LOVE IS IN THE AIR",
	"description": "Um evento para toda a familia",
	"image" : <image-name-returned-by-the-upload>, 
	"price": "22.0",
	"original_price": 50.0,
	"available_quantity": 20,
	"scheduled_date": "2017-12-03T10:15:30Z",
	"theater": {
		"id": 1
	}
}
```

Example:

```bash
curl -b /tmp/cookies.txt -H "Content-Type:application/json" -X POST http://localhost:8080/events --data "{\"name\" : \"Evento maroto\", \"description\": \"Um evento para toda a familia\", \"image\": \"<image-name-returned-by-the-upload>\", \"price\": \"22.0\", \"original_price\":\"50.0\", \"available_quantity\": 20, \"scheduled_date\": \"2017-12-03T10:15:30Z\", \"theater\": {\"id\":1}}"
```

You can also send the theater information if it doesn't already exists on this service's database: (REQUIRES LOGIN)

```json
{
	"name": "LOVE IS IN THE AIR",
	"description": "Um evento para toda a familia",
	"price": "22.0",
	"original_price": 50.0,
	"image" : <image-name-returned-by-the-upload>,
	"available_quantity": "20",
	"scheduled_date": "2017-12-03T10:15:30Z",
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

### How to update one (REQUIRES LOGIN)

The json is the same above

Example:

```bash
curl -b /tmp/cookies.txt -H "Content-Type:application/json" -X PUT http://localhost:8080/events/1 --data "{\"name\" : \"Evento editado\", \"description\": \"Um evento editado\", \"price\": \"10.0\", \"original_price\":\"20.0\", \"available_quantity\": 10, \"scheduled_date\": \"2018-12-03T10:15:30Z\", \"theater\": {\"id\":1}}"
```

Possible responses:

- 200 - Event updated
- 400 - Invalid or insufficient data
- 403 - Forbidden (the logged user is not the theaters owner)
- 404 - Event not found

### How to list all the events

List example:
```bash
curl http://localhost:8080/events
```

#### Optional parameters
 - hours_limit: Sets a limit in hours to the events scheduled_date
 - q: Especifies the event's name chunk you want to search by


The response will be a json in as the one bellow:
```json
{
	"events": [{
		"id": 1,
		"name": "LOVE IS IN THE AIR",
		"description": "Um evento para toda a familia",
		"image": 
		"price": 22.0,
		"original_price": 50.0,
		"half_price": 11.0,
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
		"scheduled_date": "2017-12-03T10:15:30Z"
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
	"original_price": 50.0,
	"half_price": 11.0,
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
	"scheduled_date": "2017-12-03T10:15:30Z"
}
```

### How to decrement events quantity

Example:

```bash
 curl -X PUT -H "Content-Type:application/json" http://localhost:8080/events/checkout/1 --data "{\"quantity\":1}"
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
	"scheduled_date": "2017-12-03T10:15:30Z"
}
```

- 400 - Invalid or insufficient data
- 404 - Not events with the specified id are available