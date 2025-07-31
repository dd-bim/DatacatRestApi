# datacat REST API

This API orients on the [bSDD API](https://app.swaggerhub.com/apis/buildingSMART/Dictionaries/v1#/) and can be used to get the concepts from the property server [datacat](https://github.com/dd-bim/datacat-stack). 

<b>Not all endpoints, query parameters and resulting attributes are implemented!</b>

To connect the API with your datacat instance over the GraphQL endpoint, set `SERVER_URL`, `USERNAME` and `password` in the `docker-compose.yml`.