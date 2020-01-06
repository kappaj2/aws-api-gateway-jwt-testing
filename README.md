# API Testing

This project will be used to test JWT headers received from the API Gateway.

The header should contain an "Authorization" header containing a valid JWT Token.

The following on the JWT token:
* Should contain the ID_Token ( The original token contains id_token, access_token and refresh_token blocks)
* No scopes should be defined. 
* Groups should be defined for the user.

Defining scopes "breaks" the API Gateway Authorizer(s) as it will try to validate the scopes.
With the API-Gateway setup as a {Proxy} with no dedicated API-Endpoint per services, we cannot define scopes as this will require all possible scopes to be defined.
<br>
