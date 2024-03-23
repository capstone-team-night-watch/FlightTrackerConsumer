# CapstoneConsumer

## About
This project contains our barebones project that listens for events coming in from our Kafka broker
This application logs the messages received from the broker and can be viewed in the docker container logs
The kafka broker is running on one of our Ubuntu AWS instance.

## Release Notes

Milestone 2: Consumer sends flight and no-fly-zone data to the frontend for display. Supports no-fly-zone data from FAA NOTAM messages and flight checkpoint/waypoint data. Collision detection between flights and no-fly-zones is currently in development. Consumer application can be run using its associated docker container:  
https://hub.docker.com/r/ewagner2802/flighttrackerconsumer

## Milestones

Milestone 1:
Environment set up.
Milestone 2:
Created initial application that is able to recieve hello world messages from broker
Milestone 3:
Initial integration consuming flight data, added various services that will serve the front end.
Milestone 4:
Heavily integrated with front end, websockets set up and communicating
Milestone 5:
General clean up and commenting.

## Kafka listeners payload

- Sample Polygon no fly-zone payload

```json5
{
  "altitude": 1000,
  "notamNumber": "1234",
  "type": "POLYGON",
  "vertices": [
    {
      "latitude": 1,
      "longitude": 1
    },
    {
      "latitude": 2,
      "longitude": 2
    },
    {
      "latitude": 3,
      "longitude": 3
    }
  ]
}
```

- Sample circular no fly-zone payload

```json5
{
  "altitude": 1000,
  "notamNumber": "1234",
  "type": "CIRCLE",
  "center": {
    "latitude": 1,
    "longitude": 1
  },
  radius: 100
}
```

- Sample flight location data message

```json5
{
  "location": {
    "altitude": 1,
    "latitude": 1,
    "longitude": 1
  },
  "heading": 90,
  "groundSpeed": 100,
  "flightId": "1234",
  "timestamp": "2021-10-10T10:10:10"
}
```


## Docker Container

This application is containerized and requires the following environment variables:

- **KAFKA_BROKER** : The address of the Kafka broker
- **POSTGRES_URL**: The address of the Postgres database
- **POSTGRES_USERNAME**: The username for the Postgres database
- **POSTGRES_PASSWORD**: The password for the Postgres database
