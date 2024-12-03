# User API Spec


## Register User

Endpoint :  POST /api/users

Request Body :

```json
{
  "username" : "vigo",
  "password" : "rahasia",
  "name" : "Vigo"
}
```

Response Body (Success) :

```json
{
  "data" : "OK"
}
```
Response Body (Failed) :

```json
{
  "errors" : "Username is must not blank, ??"
}
```


## Login User

Endpoint :  POST /api/auth/login

Request Body :
```json
{
  "username" : "vigo",
  "password" : "rahasia"
}
```

Response Body (Success) :
```json
{
  "data" : {
    "token" : "TOKEN",
    "expiredAt" : 2323223432432 //milliseconds
  }
}
```
Response Body (Failed) :
```json
{
  "errors" : "username or password wronghg, ??"
}
```

## Get User

Endpoint :  GET /api/users/current 

Request Header :

- X-API-Token : Token(Mandatory)

Response Body (Success) :
```json
{
  "data" : {
    "username" : "vigo",
    "name" : "Vigo"
  }
}
```
Response Body (Failed,401) :
```json
{
  "errors" : "Unauthorized"
}
```

## Update User

Endpoint :  PATCH /api/users/current

Request Header :

- X-API-Token : Token(Mandatory)

Request Body : 
```json
{
  "name": "Made", //put if only want to update name
  "password" : "pwpwpw", //put if only want to update password
}
```

Response Body (Success) :
```json
{
  "data" : {
    "username" : "vigo",
    "name" : "Vigo"
  }
}
```
Response Body (Failed,401) :
```json
{
  "errors" : "Unauthorized"
}
```

## Logout User

Endpoint :  DELETE /api/auth/logout

Request Header :

- X-API-Token : Token(Mandatory)
- Response Body (Success) :
```json
{
  "data" : "oke"
}
```