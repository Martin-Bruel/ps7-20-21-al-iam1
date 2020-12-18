# API

## Compile and run

`mvn clean package`  
`mvn exec:java -Dexec.mainClass="fr.Main"`  


## Documentation

you can access to the documation only when the api is running  
`http://localhost:8080/swagger-ui/index.html`

## Docker  

Build docker in api folder : `docker build -t ps7-20-21-al-iam1/api .`  
Run docker : `docker run -it -p 8080:8080 ps7-20-21-al-iam1/api`  
Test go to `http://localhost:8080/swagger-ui/index.html`  

Groupe AL-IAM1
