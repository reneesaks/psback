# Perfect Strangers

Back end for Perfect Strangers project. For front end go to https://github.com/kasparsuvi1/psfront

## Prerequisites

This project uses maven dependency management and build automation tool. Version control is done with git.

* [Maven installation](https://maven.apache.org/install.html)
* [Git installation](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

## Main building blocks
* Spring Boot 1.5.3.RELEASE - go to http://docs.spring.io/spring-boot/docs/1.5.3.RELEASE/reference/htmlsingle/ to learn more about spring boot
* JSON Web Token - go to https://jwt.io/ to decode your generated token and learn more
* H2 Database Engine - used for rapid prototyping and development, but not suitable for production at least in most cases. Go to www.h2database.com to learn more
* Swagger 2.0 - used for API documentation. Go to https://swagger.io/ to learn more
 
## Editors

Project is developed in IntellJ IDEA 2017 but any other IDE should also work just fine. Once maven and git is installed, proceed with the installation instructions.

## Installing

Clone the project

```
git clone https://github.com/reneesaks/psback
```

### Running as a packaged application

1. Build using maven goal: `mvn clean package` and execute the resulting artifact as follows `java -jar <filename>.jar` or
2. On Unix/Linux based systems: run `mvn clean package` then run the resulting jar as any other executable `./<filename>.jar`

### Running using the Maven plugin

The Spring Boot Maven plugin includes a run goal which can be used to quickly compile and run your application. Applications run in an exploded form just like in your IDE.

```
mvn spring-boot:run
```

## API Documentation (Swagger)

* To see API documentation with Swagger UI visit `<your-base-bath>/swagger-ui.html`. Use username `dev` and password `password`. 
* Private endpoints `api/private/*` can not be tested using Swagger UI as they require a valid token.

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
* Change security settings in `application.properties` for production.

## Authors

* **Renee Säks** - *Back end development* - [reneesaks](https://github.com/reneesaks/)

See also the list of [contributors](https://github.com/reneesaks/psback/contributors) who participated in this project.

## Acknowledgments

* Hat tip to anyone who's code was used
* **Kaspar Suvi** - *Front end* - [kasparsuvi1](https://github.com/kasparsuvi1)
* **Kristo Lall** - *Full stack* - [LKristo](https://github.com/LKristo)

