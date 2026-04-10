# =========================================================
# Файл: infrastructure.py
# Описание: Имитация работы с БД и внешними сервисами.
# =========================================================
import time
from datetime import datetime
from models import Order


# RandomSQLDatabase - имитация тяжелой базы данных
class RandomSQLDatabase:
    def __init__(self):
        self.connection_string = "random://root:password@localhost:228/shop"

    # Сохранение заказа в "базу данных"
    def save_order(self, order: Order, total: float) -> None:
        print(f"Connecting to RandomSQL at {self.connection_string} ...")
        time.sleep(0.5)  # Имитация задержки сети

        with open("orders_db.txt", "a") as file:
            record = f"[{datetime.now().strftime('%Y-%m-%dT%H:%M:%S')}] ID: {order.id} | Type: {order.type} | Total: {total:.2f}\n"
            file.write(record)

        print("Order saved successfully.")

# SmtpMailer - имитация почтового сервиса
class SmtpMailer:
    def __init__(self, server: str):
        self.server = server

    def SendHtmlEmail(self, to: str, subject: str, body: str) -> None:
        print(f">> Connecting to SMTP server {self.server}...")
        print(f">> Sending EMAIL to {to}\n   Subject: {subject}\n   Body: {body}")
