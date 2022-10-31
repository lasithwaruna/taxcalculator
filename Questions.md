## Questions/assumptions

1. I assumed that this API will be called with all dates at once. 
If it is to be called everytime a vehicle passes tolling station, I need
to have a persistent repository

2. I assumed the holidays will be fed in to the system every year

3. I assumed, when it comes to a new deployment at another location. property files will be changed accordingly. and application
restart is needed to take effect the configurations

4. I assumed the vehicle types will be limited to the introduced ENUM. if there is a new vehicle type with tax, OtherWithoutTax
vehicle type can be used. and if there is a new vehicle type with tax, OtherWithTax can be used.

5. When configuring the TaxCalculator, the order of the rules should be placed correctly.

## API Doc
* [Swagger UI](#http://localhost:8086/tax/swagger-ui/index.html)

## Sample Request 

Make sure the datetime format is : yyyy-MM-dd HH:mm:ss

````
curl -X 'POST' \
'http://localhost:8086/tax/api/v1/tax' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
"vehicleType": "Car",
"dateTimes": [
{
"dateTime": "2013-10-30 17:08:19"
},
{
"dateTime": "2013-10-30 07:06:19"
}
]
}'

````

