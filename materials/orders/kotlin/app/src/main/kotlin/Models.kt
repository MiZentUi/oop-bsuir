// =========================================================
// Файл: Models.kt
// Описание: Модели данных системы.
// =========================================================

// Item - товар в заказе
data class Item(
    val id: String,
    val name: String,
    val price: Double
)

// Address - адрес доставки
data class Address(
    val city: String,
    val street: String,
    val zip: String
)

// Order - заказ
data class Order(
    val id: String,
    val items: List<Item>,
    val type: String, // "Standard", "Premium", "Budget", "International"
    val clientEmail: String,
    val destination: Address
)
