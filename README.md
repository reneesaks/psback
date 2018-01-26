# Perfect Strangers

Back end for Perfect Strangers project. For front end go to https://github.com/kasparsuvi1/psfront

## Prerequisites

This project requires the following prerequisites:
* Maven - dependency management and build automation tool. Install [Maven](https://maven.apache.org/install.html).
* Git - version control. Install [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* MySQL - production database. Install [XAMPP](https://www.apachefriends.org/download.html) for Windows. For other systems visit MySQL official [installing guide](https://dev.mysql.com/doc/refman/5.6/en/installing.html).


## Main building blocks
* Spring Boot 1.5.3.RELEASE - go to http://docs.spring.io/spring-boot/docs/1.5.3.RELEASE/reference/htmlsingle/ to learn more about Spring Boot.
* JSON Web Token - go to https://jwt.io/ to decode your generated token and learn more.
* H2 Database Engine in development environment - used for rapid prototyping and development, but not suitable for production at least in most cases. Go to http://www.h2database.com/ to learn more.
* MySQL Database in production environment - MySQL is an open-source relational database management system. Go to https://www.mysql.com/ to learn more.
* Swagger 2.0 - used for API documentation. Go to https://swagger.io/ to learn more.
 
## Editors

Project is developed in IntellJ IDEA 2017 but any other IDE should also work just fine. Once maven and git is installed, proceed with the installation instructions.

## Installing

Clone the project

```
git clone https://github.com/reneesaks/psback
```
## Running

This project has two Spring Boot profiles:
* Development environment called `dev`
* Production environment called `production` 

By default development environment is used in packaged application build process. Test data is loaded in via `import.sql` file in resources folder.

### Before running

Before running in production environment make sure you have your MySQL server up and running on port 3306 and a database named `perfectstrangers`. Your database username should be `root` and password empty. To create a database on Windows, open Shell in XAMPP and execute the following commands:
* `mysql -u root -p`
* `create database perfectstrangers`

Linux based systems commands should be somewhat similar if not the same. The development environment uses H2 database and does not require any manual configuration prior to running.

### Running as a packaged application

1. Build using maven goal: `mvn clean package -P <choose-your-environment-here>` and execute the resulting artifact in target folder as follows `java -jar <filename>.jar` or
2. On Unix/Linux based systems: run `mvn clean package -P <choose-your-environment-here>` then run the resulting jar in target folder as any other executable `./<filename>.jar`

### Running using the Maven plugin

The Spring Boot Maven plugin includes a run goal which can be used to quickly compile and run your application. Applications run in an exploded form just like in your IDE.

```
mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=<choose-your-environment-here>"
```

### Running with IntellJ IDEA 2017

Open your Run/Debug Configurations and add a new Spring Boot configuration by clicking `+`. Main class is `com.perfectstrangers.PerfectStrangersApplication`, specify the Active Profile you want to use and name it conveniently. You can have as many configurations as you want. When you run the program you can choose which one to use.

## API Documentation (Swagger)

* To see API documentation with Swagger UI visit `<your-base-bath>/swagger-ui.html`. Use username `dev` and password `password`. 
* Private endpoints `api/private/*` can not be tested using Swagger UI as they require a valid token.

## Accessing H2 database

To access to H2 database in development environment visit `http://localhost:8080/console`. For logging in use the following information:
* Driver Class - `org.h2.Driver`
* JDBC URL - `jdbc:h2:file:~/perfectstrangers`
* User Name - `sa`
* Password - leave empty

## To test the endpoints

### First you will need the following basic pieces of information:

* client: testjwtclientid
* secret: XY7kmzoNzl100
* Standard user username and password: `user` and `password`
* Admin user: `admin` and `password`
* Example of resource accessible to all authenticated users:  http://localhost:8080/api/private/hotels
* Example of resource accessible to only an admin user:  http://localhost:8080/api/private/users
* Example of resource accesible publicly: http://localhost:8080/api/public/hello

#### 1. Generate an access token

Use the following generic command to generate an access token for the non-admin user `user`:
`$ curl testjwtclientid:XY7kmzoNzl100@localhost:8080/oauth/token -d grant_type=password -d username=user -d password=password`

You'll receive a response similar to below

`
{
	"access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGVzdGp3dHJlc291cmNlaWQiXSwidXNlcl9uYW1lIjoiYWRtaW4uYWRtaW4iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNDk0NDU0MjgyLCJhdXRob3JpdGllcyI6WyJTVEFOREFSRF9VU0VSIiwiQURNSU5fVVNFUiJdLCJqdGkiOiIwYmQ4ZTQ1MC03ZjVjLTQ5ZjMtOTFmMC01Nzc1YjdiY2MwMGYiLCJjbGllbnRfaWQiOiJ0ZXN0and0Y2xpZW50aWQifQ.rvEAa4dIz8hT8uxzfjkEJKG982Ree5PdUW17KtFyeec",
	"token_type": "bearer",
	"expires_in": 43199,
	"scope": "read write",
	"jti": "0bd8e450-7f5c-49f3-91f0-5775b7bcc00f"
}`

#### 2. Use the token to access resources through your RESTful API

* Access content available to all authenticated users

Use the generated token as the value of the Bearer in the Authorization header as follows:
`curl  http://localhost:8080/api/private/hotels -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGVzdGp3dHJlc291cmNlaWQiXSwidXNlcl9uYW1lIjoiYWRtaW4uYWRtaW4iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNDk0NDU0MjgyLCJhdXRob3JpdGllcyI6WyJTVEFOREFSRF9VU0VSIiwiQURNSU5fVVNFUiJdLCJqdGkiOiIwYmQ4ZTQ1MC03ZjVjLTQ5ZjMtOTFmMC01Nzc1YjdiY2MwMGYiLCJjbGllbnRfaWQiOiJ0ZXN0and0Y2xpZW50aWQifQ.rvEAa4dIz8hT8uxzfjkEJKG982Ree5PdUW17KtFyeec" `

* Access content available only to an admin user

As with the previous example first generate an access token for the admin user with the credentials provided above then run
`curl  http://localhost:8080/api/private/users -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGVzdGp3dHJlc291cmNlaWQiXSwidXNlcl9uYW1lIjoiYWRtaW4uYWRtaW4iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNDk0NDU0OTIzLCJhdXRob3JpdGllcyI6WyJTVEFOREFSRF9VU0VSIiwiQURNSU5fVVNFUiJdLCJqdGkiOiIyMTAzMjRmMS05MTE0LTQ1NGEtODRmMy1hZjUzZmUxNzdjNzIiLCJjbGllbnRfaWQiOiJ0ZXN0and0Y2xpZW50aWQifQ.OuprVlyNnKuLkoQmP8shP38G3Hje91GBhu4E0HD2Fes" `

### Using Postman for testing

You can also use Postman for endpoint testing. Import endpoints using this link: https://www.getpostman.com/collections/67dd25c1fbcf149438c3

## Integration with Angular

[This code](https://github.com/nydiarra/springboot-jwt) was used as a seed for this application. For an example integration with Angular (version 2+) go to https://github.com/ipassynk/angular-springboot-jwt

## TODO

* Deployment
* Tests
* Disallow all origins, headers and methods in AdditionalWebConfig class later on (CSRF).
* Change DEVELOPER username and password for API documentation for production (ResourceServerConfig).
* Change security settings in `application-production.properties` for production.
* Change to a real database in `application-production.properties` and establish connection with it.
* Remove `import.sql` from production environment or change it.

## Authors

* **Renee Säks** - *Back end development* - [reneesaks](https://github.com/reneesaks/)

See also the list of [contributors](https://github.com/reneesaks/psback/contributors) who participated in this project.

## Acknowledgments

* Hat tip to anyone who's code was used
* **Kaspar Suvi** - *Front end* - [kasparsuvi1](https://github.com/kasparsuvi1)
* **Kristo Lall** - *Full stack* - [LKristo](https://github.com/LKristo)

