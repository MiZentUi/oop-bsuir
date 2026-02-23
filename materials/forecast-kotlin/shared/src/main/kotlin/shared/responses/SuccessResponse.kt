package shared.responses

data class SuccessResponse<T>(
    val code: Int,
    val message: String,
    val data: T
)
