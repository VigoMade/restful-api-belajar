# Address API Spec

## Create Address

Endpoint : POST /api/contacts/{idContact}/addresses

Request Header : 
- X-API-Token : Token(Mandatory)

Request Body :

```json
{
  "street" :"Jalan",
  "city" : "Manchester",
  "province" : "provinsi",
  "country" : "negara",
  "postalCode" : "12313"
}
```

Response Body(Success) 

```json
{
  "data" : {
    "id" : "randomstring",
    "street" :"Jalan",
    "city" : "Manchester",
    "province" : "provinsi",
    "country" : "negara",
    "postalCode" : "12313"
  }
}
``` 

Response Body(Failed) :

```json
{
  "errors" : "Contact is not found"
}
``` 

## Update Address

Endpoint : PUT /api/contacts/{idContact}/addresses/{idAddress}

Request Header :
- X-API-Token : Token(Mandatory)

Request Body :

```json
{
  "street" :"Jalan",
  "city" : "Manchester",
  "province" : "provinsi",
  "country" : "negara",
  "postalCode" : "12313"
}
```

Response Body(Success)

```json
{
  "data" : {
    "id" : "randomstring",
    "street" :"Jalan",
    "city" : "Manchester",
    "province" : "provinsi",
    "country" : "negara",
    "postalCode" : "12313"
  }
}
``` 

Response Body(Failed) :

```json
{
  "errors" : "Address is not found"
}
``` 

## Get Address

Endpoint : GET /api/contacts/{idContact}/addresses/{idAddress}

Request Header :
- X-API-Token : Token(Mandatory)

Response Body(Success)

```json
{
  "data" : {
    "id" : "randomstring",
    "street" :"Jalan",
    "city" : "Manchester",
    "province" : "provinsi",
    "country" : "negara",
    "postalCode" : "12313"
  }
}
``` 

Response Body(Failed) :

```json
{
  "errors" : "Address is not found"
}
``` 

## Remove Address

Endpoint : DELETE /api/contacts/{idContact/addresses/{idAddresses}

Request Header :
- X-API-Token : Token(Mandatory)

Response Body(Success)

```json
{
  "data" : "OK"
}
``` 

Response Body(Failed) :

```json
{
  "errors" : "Address is not found"
}
``` 

## List Address

Endpoint : GET /api/contacts/{idContact/addresses

Request Header :
- X-API-Token : Token(Mandatory)

Response Body(Success)

```json
{
  "data" :[
    {
      "id" : "randomstring",
      "street" :"Jalan",
      "city" : "Manchester",
      "province" : "provinsi",
      "country" : "negara",
      "postalCode" : "12313"
    }
  ] 
}
``` 

Response Body(Failed) :

```json
{
  "errors" : "Contact is not found"
}
``` 
