import java.util.List;

import models.Address;
import models.Item;
import models.Order;
import staff.HumanManager;
import staff.RobotPacker;
import staff.Warehouse;

/**
 * Точка входа в приложение.
 */
public class App {
    public static void main(String[] args) {
        // 1. Создание заказа
        var order = new Order(
                "ORD-256-X",
                "Premium",
                List.of(
                        new Item("1", "Thermal Clips", 1500),
                        new Item("2", "UNATCO Pass Card", 50)),
                "jeevacation@gmail.com",
                new Address("Agartha", "33 Thomas Street", "[REDACTED]"));

        // 2. Инициализация процессора
        var processor = new OrderProcessor();

        // 3. Обработка заказа
        try {
            processor.Process(order);
        } catch (Exception e) {
            System.out.println("Failed to process order: ");
            throw new RuntimeException(e);
        }

        // 4. Работа с обслуживанием
        System.out.println("\nTesting Warehouse Stuff:");
        var workers = List.of(
                new HumanManager(),
                new RobotPacker("George Droid"));

        Warehouse.manageWarehouse(workers);
    }
}
