// =========================================================
// Файл: Main.kt
// Описание: Точка входа в приложение.
// =========================================================

fun main() {
    // 1. Создание заказа
    val order = Order(
        id = "ORD-256-X",
        type = "Premium",
        items = listOf(
            Item(id = "1", name = "Thermal Clips", price = 1500.0),
            Item(id = "2", name = "UNATCO Pass Card", price = 50.0)
        ),
        clientEmail = "jeevacation@gmail.com",
        destination = Address(city = "Agartha", street = "33 Thomas Street", zip = "[REDACTED]")
    )

    // 2. Инициализация процессора
    val processor = newOrderProcessor()

    // 3. Обработка заказа
    processor.process(order).onFailure { e ->
        error("Failed to process order: ${e.message}")
    }

    // 4. Работа с обслуживанием
    println("\nTesting Warehouse Stuff:")
    val workers = listOf<WarehouseWorker>(
        HumanManager(),
        RobotPacker(model = "George Droid")
    )

    manageWarehouse(workers)
}
