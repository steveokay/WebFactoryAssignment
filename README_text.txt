# Assumptions made
1. sheduler for checking exchange rates, fetches and prints the rate since we are not saving in database.
2. Endpoint for getting latest rates queries the api endpoint and doesnt fetch from any storage i.e. db or file.
3. Endpoint for getting rates in between fetches from api endpoint and not from our storage.

# Ways to improve implementation in future
1. Store fetched exchange rate in database.
2. Fetch rates from database and let the ratesFetcher service to be the only service to query api and update in database.

# Requirements to run the code
1. Java 8 jdk or openjdk 8 and above
2. Postman or Insomnia or curl to be able to test the rest endpoints

# How to run/ test the application
1. Import the code in any java IDE Preferably InteliJ or eclipse
2. Run maven to import all dependencies
3. Run the application
4. Use any rest client tool to test the application

# Endpoints URI
1. Fetch latest rate : GET /api/rates/

    expected response :

    {
      "rates": 32391.5633,
      "statusCode": "200",
      "description": "Success"
    }

2. Fetch rates between two dates : POST /api/rates/

    expected request :
     {
       "startDate":"2021-06-16",
       "endDate":"2021-06-21"
     }

     expected response :

     {
       "rates": {
           "2021-06-16": 38344.7233,
           "2021-06-17": 38087.2533,
           "2021-06-18": 35814.6417,
           "2021-06-19": 35519.7117,
           "2021-06-20": 35605.68
        },
       "statusCode": "200",
       "description": "Success"
     }
