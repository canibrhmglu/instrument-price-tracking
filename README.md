# **Instrument Price Tracking**
It is an application to track instrument price that consumes Websocket Streams and produce aggregated results for users.

### **Technologies**
- Java 8
- Spring Boot Framework
- H2 DB
- Docker
- Junit
- Mockito
- Swagger UI

### **Features**

For API documentation, please see  at http://localhost:8181/swagger-ui.html

There are two types of instrument reports:

**Instrument-Price**

- The last prices of every instrument that has not been deleted.

**Candle Stick**

- The last 30 minutes prices for every instrument with these attributes:
  
 **Open timestamp**

  **Open Price**

  **High Price**

  **Low Price**

  **Closing Price**

  **Close timestamp**

### **Run Application**

Before running the application, it needs to run Partner service Websocket with the commands:
```
1. cd {partner service-path}
2. java -jar partner-service-1.0-all.jar
```
Please run the docker commands below:
```
1. cd {project-path}
2. mvn clean install
3. docker build -t instrument-price-tracking .
4. docker-compose up
```
After executing the commands, the application gets ready to use.

### **Sample Payloads**
To get last prices of each instrument:

Path: /instrumentsLastPrice

Request:
```json 

{
    "link": "http://localhost:8181/instrumentsLastPrice"
}
```
Response:
```
[
    {
        "isin": "MX440A67XA20",
        "lastPrice": 539.28,
        "createdDate": "2021-10-11T13:05:58.917"
    },
    {
        "isin": "XF2M22I14S02",
        "lastPrice": 193.54,
        "createdDate": "2021-10-11T13:05:58.086"
    },
    {
        "isin": "LG7P43X40I17",
        "lastPrice": null,
        "createdDate": null
    }
]
```

To get candle stick for a specified instrument :

Path: /candleSticksByInstrument?isin={isin}

Request:
```json 

{
    "link" : "http://localhost:8181/candleSticksByInstrument?isin=XF2M22I14S02"
}
```
Response:
```
[
    {
        "isin": "XF2M22I14S02",
        "openTimestamp": "2021-10-11T13:05:00",
        "openPrice": 193.54,
        "highPrice": 193.54,
        "lowPrice": 193.54,
        "closePrice": 193.54,
        "closeTimestamp": "2021-10-11T13:06:00"
    },
    {
        "isin": "XF2M22I14S02",
        "openTimestamp": "2021-10-11T13:06:00",
        "openPrice": 194.86,
        "highPrice": 194.86,
        "lowPrice": 175.29,
        "closePrice": 175.29,
        "closeTimestamp": "2021-10-11T13:07:00"
    },
    {
        "isin": "XF2M22I14S02",
        "openTimestamp": "2021-10-11T13:07:00",
        "openPrice": 172.60,
        "highPrice": 173.22,
        "lowPrice": 162.78,
        "closePrice": 162.78,
        "closeTimestamp": "2021-10-11T13:08:00"
    },
    {
        "isin": "XF2M22I14S02",
        "openTimestamp": "2021-10-11T13:08:00",
        "openPrice": 160.09,
        "highPrice": 160.09,
        "lowPrice": 138.46,
        "closePrice": 138.46,
        "closeTimestamp": "2021-10-11T13:09:00"
    },
    {
        "isin": "XF2M22I14S02",
        "openTimestamp": "2021-10-11T13:09:00",
        "openPrice": 137.77,
        "highPrice": 137.77,
        "lowPrice": 132.08,
        "closePrice": 133.71,
        "closeTimestamp": "2021-10-11T13:10:00"
    }
]
```

To see the instruments and quotes retrieved from partner service, please visit the link below:
```
http://localhost:8181/h2-console
```

