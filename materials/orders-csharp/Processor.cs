
// =========================================================
// Файл: processor.go
// Описание: Основная бизнес-логика.
// =========================================================

namespace Orders;

class OrderProcessor
{
    private readonly RandomSQLDatabase database;
    private readonly SmtpMailer mailer;

    public OrderProcessor()
    {
        database = new RandomSQLDatabase();
        mailer = new SmtpMailer() { Server = "smtp.google.com" };
    }

    public void Process(Order order)
    {
        Console.WriteLine($"--- Processing Order {order.Id} ---");
        // 1. Логика валидации
	    if (order.Items.Length == 0) {
			throw new ArgumentException("order must have at least one item", nameof(order));
	    }
	    if (order.Destination.City == "") {
			throw new ArgumentException("destination city is required", nameof(order));
	    }

        // 2. Логика расчета суммы
	    double total = 0;
	    foreach (var item in order.Items)
        {
	    	total += item.Price;
	    }
        switch (order.Type)
        {
            case "Standard":
                total = total * 1.2;
                break;
            case "Premium":
                total = (total * 0.9) * 1.2;
                break;
            case "Budget":
                if (order.Items.Length > 3)
                {
		        	Console.WriteLine("Budget orders cannot have more than 3 items. Skipping.");
		        	return;
		        }
                break;
            case "International":
                total = total * 1.5; // Таможенный сбор
		        if (order.Destination.City == "Nowhere") {
					throw new ArgumentException("cannot ship to Nowhere", nameof(order));
		        }
                break;
            default:
				throw new ArgumentException("unknown order type", nameof(order));
        }
        
        // 4. Логика сохранения
		try
		{
			database.SaveOrder(order, total);
		}
		catch (Exception e)
		{
			Console.WriteLine($"database error: {e.Message}");
			throw;
		}

		// 5. Логика уведомлений

		var emailBody = $"<h1>Your order {order.Id} is confirmed!</h1><p>Total: {total:.2f}</p>";
	    mailer.SendHtmlEmail(order.ClientEmail, "Order Confirmation", emailBody);
    }
}
