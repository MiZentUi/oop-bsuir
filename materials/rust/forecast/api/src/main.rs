use std::error::Error;

use api::weather;
use utoipa::OpenApi;
use utoipa_axum::router::OpenApiRouter;
use utoipa_swagger_ui::SwaggerUi;

#[derive(OpenApi)]
#[openapi(info(title = "Whether Example API", version = "1.0"))]
struct ApiDoc;

fn main() -> Result<(), Box<dyn Error>> {
    dotenvy::dotenv()?;
    tokio::runtime::Builder::new_multi_thread()
        .enable_all()
        .build()?
        .block_on(serve())
}

async fn serve() -> Result<(), Box<dyn Error>> {
    let (router, doc) = OpenApiRouter::with_openapi(ApiDoc::openapi())
        .nest("/api/v1", weather::routers())
        .split_for_parts();
    let router =
        router.merge(SwaggerUi::new("/swagger-ui").url("/api-docs/openapi.json", doc.clone()));

    let listener = tokio::net::TcpListener::bind("0.0.0.0:3000").await.unwrap();
    axum::serve(listener, router).await.unwrap();
    Ok(())
}
