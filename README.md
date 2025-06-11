This Rest service has apis to retrieve the customer rewards for the last three months, for all customers and by customerId as well.

Appliction is secured with Basic authentication.
username is "charter" and
password is "assessment"


**Notes**: I am using the input data as DB data in the code and processing it for the results rather than having a repository and return static data, that's why I am using only post api endpoints. I am considering the last three months which are completed (for example from today the last three months I am using in my code are March, April and May since June is not yet completed.).


Test data:
[
{
  "customerId": "CUST1001",
  "customerPurchases": [
    {
      "amount": 150.75,
      "purchaseDate": "2025-04-20"
    },
    {
      "amount": 89.99,
      "purchaseDate": "2025-03-15"
    }
  ]
},
{
  "customerId": "CUST1002",
  "customerPurchases": [
    {
      "amount": 250.75,
      "purchaseDate": "2025-04-20"
    },
    {
      "amount": 99.99,
      "purchaseDate": "2025-03-15"
    }
  ]
},
{
  "customerId": "CUST1003",
  "customerPurchases": [
    {
      "amount": 350.75,
      "purchaseDate": "2025-04-20"
    },
    {
      "amount": 49.99,
      "purchaseDate": "2025-03-15"
    }
  ]
},
{
  "customerId": "CUST1004",
  "customerPurchases": [
    {
      "amount": 225.75,
      "purchaseDate": "2025-04-20"
    },
    {
      "amount": 69.99,
      "purchaseDate": "2025-03-15"
    }
  ]
}
]
