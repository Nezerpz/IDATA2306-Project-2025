# IDATA2306-Project-2025
The backend of the project for the spring semester 2025


## General Setup
To prepare the application on a production environment, do this:
```
git clone git@github.com:Nezerpz/IDATA2306-Project-2025.git
cd IDATA2306-Project-2025
mvn package
```

Then, create a file named `.env` and put the following variables inside of it:
```
SPRING_APPLICATION_NAME=rentalroulette
SERVER_PORT=8080
SPRING_JPA_DATABASE=POSTGRESQL
SPRING_DATASOURCE_PLATFORM=postgres
DB_PORT=5432
POSTGRES_USER="postgres"
POSTGRES_PASSWORD="elephont"
SPRING_DATASOURCE_URL=jdbc:postgresql://db:${DB_PORT}/postgres
SPRING_DATASOURCE_USERNAME=${POSTGRES_USER}
SPRING_DATASOURCE_PASSWORD=${POSTGRES_PASSWORD}
SPRING_JPA_SHOW_SQL=true
SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
SPRING_JPA_GENERATE_DDL=true
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_PROPERTIES_HIBERNATE_JDBC_LOB_NON_CONTEXTUAL_CREATION=true
```
(the password off-course being a good and secret one)


## Development: Run
To run the application for local development, run:
```
podman-compose run --rm --service-ports backend
```
The image is removed when you stop the running command.


## Production: Run & Update
To run the application in a production environment, run:
```
podman-compose build
```

To update application in a production environment, run:
```
podman-container stop container_name
podman rmi --force container_name
podman-compose build
```
