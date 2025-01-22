# datacat REST API
This API for datacat is based on a restful approach and realised with OpenAPI.
By default the API points on the domain for the ontology [IBPDI](https://ibpdi.datacat.org/api/e1/).

## Documentation
A documentation file can be found in [/doc](https://github.com/dd-bim/DatacatRestApi/tree/main/doc).
The file provides all information to work out the business logic of the REST API.
A package overview, a step-by-step instruction for an update as well as the generator-ignore list are provided.

## Usage and License
You can view the api documentation in swagger-ui by pointing to
http://localhost:3001/swagger-ui.html

Change default port value in application.properties if necessary.


## OpenAPI based API
The underlying library integrating OpenAPI to Spring Boot is [springdoc](https://springdoc.org).
Springdoc will generate an OpenAPI v3 specification based on the generated Controller and Model classes.
