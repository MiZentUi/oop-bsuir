# Weather Forecast Example Project (Java)

## Docs
[swagger](http://localhost:8080/swagger)

## Base commands
- build: `mvn clean install`
- run: `mvn spring-boot:run`

## Environment variables
Set environment variables before running:
```
OPENWEATHER_API_KEY=xxxxxxxxxxxxxxxxx
OPENWEATHER_BASE_URL=https://api.openweathermap.org/data/2.5/weather
```

Or create `.env` file and use a tool like `dotenv-cli`:
```
dotenv mvn spring-boot:run
```
