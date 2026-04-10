import java.io.FileWriter
import java.time.Instant

// =========================================================
// Файл: Infrastructure.kt
// Описание: Имитация работы с БД и внешними сервисами.
// =========================================================

// RandomSQLDatabase - имитация тяжелой базы данных
class RandomSQLDatabase(val connectionString: String) {

    // Сохранение заказа в "базу данных"
    fun saveOrder(order: Order, total: Double) {
        println("Connecting to RandomSQL at $connectionString ...")
        Thread.sleep(500) // Имитация задержки сети

        FileWriter("orders_db.txt", true).use { writer ->
            val record = "[${Instant.now()}] ID: ${order.id} | Type: ${order.type} | Total: %.2f\n".format(total)
            writer.write(record)
        }
        println("Order saved successfully.")
    }
}

fun newMySQLDatabase(): RandomSQLDatabase {
    return RandomSQLDatabase("random://root:password@localhost:228/shop")
}

// SmtpMailer - имитация почтового сервиса
class SmtpMailer(val server: String) {

    fun sendHtmlEmail(to: String, subject: String, body: String) {
        println(">> Connecting to SMTP server $server...")
        println(">> Sending EMAIL to $to\n Subject: $subject\n Body: $body")
    }
}
