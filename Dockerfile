# Set the base image to java8
FROM vixns/java8

MAINTAINER Leonardo Wolter <leocwolter@gmail.com>

# Define environment variables
ENV THEATER_EVENTS_HOME=/opt/theater-events

# Add artifacts
ADD build/theater-events.jar $THEATER_EVENTS_HOME/theater-events.jar
ADD build/application.properties $THEATER_EVENTS_HOME/application.properties
ADD run.sh $THEATER_EVENTS_HOME/run.sh

# Main command
ENTRYPOINT $THEATER_EVENTS_HOME/run.sh