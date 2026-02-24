// =========================================================
// Файл: Processor.kt
// Описание: Основная бизнес-логика.
// =========================================================

class OrderProcessor(
    private val database: RandomSQLDatabase,
    private val mailer: SmtpMailer
) {

    fun process(order: Order): Result<Unit> {
        println("--- Processing Order ${order.id} ---")

        // 1. Логика валидации
        if (order.items.isEmpty()) {
            return Result.failure(Exception("order must have at least one item"))
        }
        if (order.destination.city.isEmpty()) {
            return Result.failure(Exception("destination city is required"))
        }

        // 2. Логика расчета суммы
        var total = order.items.sumOf { it.price }

        // 3. Логика скидок и налогов
        when (order.type) {
            "Standard" -> {
                // Стандартный налог
                total *= 1.2
            }
            "Premium" -> {
                // Скидка 10% + налог
                total = (total * 0.9) * 1.2
            }
            "Budget" -> {
                if (order.items.size > 3) {
                    println("Budget orders cannot have more than 3 items. Skipping.")
                    return Result.success(Unit)
                }
            }
            "International" -> {
                total *= 1.5 // Таможенный сбор
                if (order.destination.city == "Nowhere") {
                    return Result.failure(Exception("cannot ship to Nowhere"))
                }
            }
            else -> return Result.failure(Exception("unknown order type"))
        }

        // 4. Логика сохранения
        runCatching {
            database.saveOrder(order, total)
        }.onFailure { e ->
            return Result.failure(Exception("database error: ${e.message}"))
        }

        // 5. Логика уведомлений
        val emailBody = "<h1>Your order ${order.id} is confirmed!</h1><p>Total: %.2f</p>".format(total)
        mailer.sendHtmlEmail(order.clientEmail, "Order Confirmation", emailBody)

        return Result.success(Unit)
    }
}

fun newOrderProcessor(): OrderProcessor {
    return OrderProcessor(
        database = newMySQLDatabase(),
        mailer = SmtpMailer(server = "smtp.google.com")
    )
}
