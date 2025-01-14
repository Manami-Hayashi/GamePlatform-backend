# Use the Keycloak image as the base image
FROM quay.io/keycloak/keycloak:25.0.5

# Set environment variables
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin
ENV KC_DB=mssql
ENV KC_DB_URL="jdbc:sqlserver://gamearena.database.windows.net:1433;database=keycloak-db;user=gamearena@gamearena;password={Password123!};encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"

# RabbitMQ environment variables
ENV RABBITMQ_HOST=ostrich.rmq.cloudamqp.com
ENV RABBITMQ_PORT=5672
ENV RABBITMQ_USERNAME=myuser
ENV RABBITMQ_PASSWORD=mypassword
ENV RABBITMQ_VHOST=myvhost

# Copy the keycloak-event-listener-1.0.0.jar to the Keycloak providers directory
COPY application/build/libs/keycloak-event-listener-1.0.0.jar /opt/keycloak/providers/keycloak-event-listener-1.0.0.jar

# Set the command to start Keycloak in development mode with verbose logging
CMD ["start-dev","--verbose"]