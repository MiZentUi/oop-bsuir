import infrastructure.RandomSQLDatabase;
import infrastructure.SmtpMailer;
import models.Order;

/**
 * Основная бизнес-логика.
 */
public class OrderProcessor {
    private final RandomSQLDatabase database;
    private final SmtpMailer mailer;

    public OrderProcessor() {
        this.database = new RandomSQLDatabase();
        this.mailer = new SmtpMailer("smtp.google.com");
    }

    public void Process(Order order) {
        System.out.printf("--- Processing Order %s ---\n", order.getId());
        var orderItems = order.getItems();

        // 1. Логика валидации
        if (orderItems.isEmpty()) {
            throw new RuntimeException("order must have at least one item");
        }
        if (order.getDestination().getCity().isEmpty()) {
            throw new RuntimeException("destination city is required");
        }

        // 2. Логика расчета суммы
        double total = 0;
        for (var item : orderItems) {
            total += item.getPrice();
        }

        // 3. Логика скидок и налогов
        switch (order.getType()) {
            case "Standart":
                // Стандартный налог
                total *= 1.2;
                break;
            case "Premium":
                total *= 0.9 * 1.2;
                break;
            case "Budget":
                if (orderItems.size() > 3) {
                    System.out.println("Budget orders cannot have more than 3 items. Skipping.");
                    return;
                }
            case "International":
                total *= 1.5;
                if (order.getDestination().getCity() == "Nowhere") {
                    throw new RuntimeException("cannot ship to Nowhere");
                }
            default:
                throw new RuntimeException("unknown order type");
        }

        // 4. Логика сохранения
        try {
            database.saveOrder(order, total);
        } catch (Exception e) {
            System.err.println("database error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // 5. Логика уведомлений
        var emailBody = String.format("<h1>Your order %s is confirmed!</h1><p>Total: %.2f</p>", order.getId(), total);
        mailer.SendHtmlEmail(order.getClientEmail(), "Order Confirmation", emailBody);
    }
}
