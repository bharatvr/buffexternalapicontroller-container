#!/bin/bash

# Prepare log files and start outputting logs to stdout
export HOSTNAME=`hostname`

echo "Application Name: $APP_NAME"
echo "Cisco Lifecycle: $CISCO_LC"
echo "Hostname: $HOSTNAME"
echo "Data Center:  $DC_NAME"

if [ "$CISCO_LC" == "prod-green" ] || [ "$CISCO_LC" == "prod-blue" ]; then
java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$CISCO_LC -Dserver.port=8080 -Dappdynamics.agent.applicationName="Bot Unification Framework (BUFF)" -Dappdynamics.agent.tierName="buff-externalapi-controller-prod" -Dappdynamics.agent.nodeName="${DATA_CENTER}_${HOSTNAME}"  -Dappdynamics.controller.hostName=cisco1.saas.appdynamics.com -Dappdynamics.agent.accountName=cisco1 -Dappdynamics.agent.accountAccessKey=e9409b02-9fe6-425e-b05e-fdf995bcf299  -javaagent:/opt/AppDServerAgent/ver4.5.6.24621/javaagent.jar -Dappdynamics.controller.port=443 -Dappdynamics.controller.ssl.enabled=true -jar /app/buffexternalapicontroller-container-0.0.1-SNAPSHOT.jar
elif [ "$CISCO_LC" == "stg" ]; then
java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$CISCO_LC -Dserver.port=8080  -jar /app/buffexternalapicontroller-container-0.0.1-SNAPSHOT.jar
else
java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$CISCO_LC -Dserver.port=8080  -jar /app/buffexternalapicontroller-container-0.0.1-SNAPSHOT.jar
fi