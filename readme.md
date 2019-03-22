# Example API Tests

## How to run test API: 

Make sure you have installed Node.js.
Run command (in project directory):
```bash
npx json-server -w WH.json
```

## Requirements: 
You have been asked to produce Java code to fulfil the following scenarios

1. Ensure that the user with the highest balance, irrespective of currency, is the only user with the ”high-roller” field set to true
2. Ensure all users have a balance in Pounds Sterling, and convert any balances and currency codes which are not
3. Process a deposit (update the balance) for Brian of 20,000 of the users currency
4. Register a new customer (add a user) with the following informationname = James, Balance=20, Currency=GBP, likes=Bingo
5. Produce a list of the most popular games this week in order from most popular to least popular
6. The marketing department are looking to run a campaign on Starburst, produce a list of target customers based on their likes.
7. Produce a list for each user showing how many spins they can make on each game, at each level of stake available


## How to run tests: 
```
mvn test 
```