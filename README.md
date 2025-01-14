# IDATA2306-Project-2025
The backend of the project for the spring semester 2025

## Development Setup
Create a file named `.env` and put the following variables inside of it:
```
POSTGRES_PASSWORD="elephont"
```
(the password off-course being a good and secret one)


## How to Deploy
```
git clone git@github.com:Nezerpz/IDATA2306-Project-2025.git
cd IDATA2306-Project-2025
mvn package

# If you run podman:
podman-compose up --build

# If you run docker:
docker compose up --build # (i think)

```
