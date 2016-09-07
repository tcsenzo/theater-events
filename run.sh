#!/bin/bash

# Defaults
THEATER_EVENTS_HEAP_OPTS=${THEATER_EVENTS_HEAP_OPTS:--Xms512m -Xmx512m -XX:MaxMetaspaceSize=256m}

exec java -Dfile.encoding=UTF-8 $THEATER_EVENTS_HEAP_OPTS -jar $THEATER_EVENTS_HOME/theater-events.jar --spring.config.location=file:///opt/theater-events/application.properties