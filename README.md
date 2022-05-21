# wheeler-dealer
Simple exchange currency application in pair PLN-USD

### Endpoints:
- POST: localhost:8080/accounts - add a new user/account
  ```json
  {
    "pesel": "79020416364",
    "firstName": "Johny",
    "lastName": "Bravo",
    "initialBalanceInPln": "100000.00"
  } 
  ```
- GET: localhost:8080/accounts/79020416364 - get information about account
- POST: localhost:8080/exchange - exchange PLN for USD
  ```json
  {
    "id": "79020416364",
    "operation": "BID",
    "balance": "5000.00"
  } 
  ```
- POST: localhost:8080/exchange - exchange USD for PLN
  ```json
  {
    "id": "79020416364",
    "operation": "ASK",
    "balance": "2000.00"
  } 
  ```
