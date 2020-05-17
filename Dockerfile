FROM openjdk:8-alpine


#COPY k8s/*.sh /usr/local/bin/
COPY entrypoint.sh /usr/local/bin/entrypoint.sh

# Adding APPDServer Agent
ADD AppServerAgent-4.5.6 /opt/AppDServerAgent
RUN mkdir -p /opt/AppDServerAgent/ver4.5.6.24621/logs && \
    chmod -R 775 /opt/AppDServerAgent/
    
RUN mkdir -p /opt/webserver/logs && chmod -R 777 /opt/webserver/logs

# Let's roll
RUN     apk update && \
        apk upgrade


RUN      apk add --update bash && \
        chmod +x /usr/local/bin/*.sh

#  Cleanup
RUN mkdir /app

COPY ./target/buffexternalapicontroller-container-0.0.1-SNAPSHOT.jar /app/buffexternalapicontroller-container-0.0.1-SNAPSHOT.jar
#COPY ./tac.json /app/tac.json

EXPOSE 8080
WORKDIR /app
RUN chmod -R 777 /home
CMD ["sh", "/usr/local/bin/entrypoint.sh"]
