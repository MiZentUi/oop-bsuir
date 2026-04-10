# =========================================================
# Файл: processor.py
# Описание: Основная бизнес-логика.
# =========================================================
from infrastructure import RandomSQLDatabase, SmtpMailer
from models import Order


class OrderProcessor:
    def __init__(self):
        self.database: RandomSQLDatabase = RandomSQLDatabase()
        self.mailer: SmtpMailer = SmtpMailer("smtp.google.com")

    def Process(self, order: Order) -> None:
        print(f"--- Processing Order {order.id} ---")

        # 1. Логика валидации
        if len(order.items) == 0:
            raise ValueError("order must have at least one item")
        if order.destination.city == "":
            raise ValueError("destination city is required")

        # 2. Логика расчета суммы
        total = sum(item.price for item in order.items)

        # 3. Логика скидок и налогов
        match order.type:
            case "Standard":
                # Стандартный налог
                total = total * 1.2
            case "Premium":
                # Скидка 10% + налог
                total = (total * 0.9) * 1.2
            case "Budget":
                if len(order.items) > 3:
                    print("Budget orders cannot have more than 3 items. Skipping.")
                    return
            case "International":
                total = total * 1.5  # Таможенный сбор
                if order.destination.city == "Nowhere":
                    raise ValueError("cannot ship to Nowhere")
            case _:
                raise ValueError("unknown order type")

        # 4. Логика сохранения
        self.database.save_order(order, total)

        # 5. Логика уведомлений
        email_body = (
            f"<h1>Your order {order.id} is confirmed!</h1><p>Total: {total:.2f}</p>"
        )
        self.mailer.SendHtmlEmail(order.client_email, "Order Confirmation", email_body)
