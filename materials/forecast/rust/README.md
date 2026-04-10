# Weather Forecast Example Project

## Docs
[swagger](http://localhost:8080/swagger/index.html)

The documentation is regenerated each time the app is run.
`swagger.json` and `swagger.yaml` files are written to the directory specified in the
`OPENAPI_DOCS_DIR` environment variable.

## Base commands
- run: `cargo run --bin api`.

## Environment variables
`.env` example:
```
OPENWEATHER_API_KEY=xxxxxxxxxxxxxxxxx
OPENWEATHER_BASE_URL=https://api.openweathermap.org/data/2.5/weather

OPENAPI_DOCS_DIR="docs"
```

