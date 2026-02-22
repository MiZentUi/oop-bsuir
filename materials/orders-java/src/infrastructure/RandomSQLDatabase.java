package infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import models.Order;

/**
 * RandomSQLDatabase - имитация тяжелой базы данных
 */
public class RandomSQLDatabase {
    private String connectionString;

    public RandomSQLDatabase() {
        this.connectionString = "random://root:password@localhost:228/shop";
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     * Сохранение заказа в "базу данных"
     * 
     * @param order
     * @param total
     * @throws InterruptedException
     */
    public void saveOrder(Order order, double total) throws InterruptedException {
        System.out.println("Connecting to RandomSQL at " + connectionString + " ...");
        Thread.sleep(500); // Имитация задержки сети

        var record = String.format("[%s] ID: %s | Type: %s | Total: %.2f\n",
                LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_INSTANT), order.getId(),
                order.getType(), total);
        try {
            Files.writeString(Path.of("orders_db.txt"), record, StandardOpenOption.APPEND, StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Order saved successfully.");
    }
}
