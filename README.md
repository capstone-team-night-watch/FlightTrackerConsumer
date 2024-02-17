# CapstoneConsumer
CapstoneConsumer
This project contains our barebones project that listens for events coming in from our Kafka broker
This application logs the messages received from the broker and can be viewed in the docker container logs
The kafka broker is running on one of our Ubuntu AWS instance


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


## Docker Container

This application is containerized and requires the following environment variables:

- **KAFKA_BROKER** : The address of the Kafka broker
- **POSTGRES_URL**: The address of the Postgres database
- **POSTGRES_USERNAME**: The username for the Postgres database
- **POSTGRES_PASSWORD**: The password for the Postgres database
