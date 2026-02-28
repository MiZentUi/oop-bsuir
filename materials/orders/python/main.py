# =========================================================
# Файл: main.py
# Описание: Точка входа в приложение.
# =========================================================
import sys

from models import Address, Item, Order
from processor import OrderProcessor
from staff import HumanManager, RobotPacker, WarehouseWorker, manage_warehouse


def main():
    # 1. Создание заказа
    order = Order(
        id="ORD-256-X",
        type="Premium",
        items=[
            Item(id="1", name="Thermal Clips", price=1500),
            Item(id="2", name="UNATCO Pass Card", price=50),
        ],
        client_email="jeevacation@gmail.com",
        destination=Address(
            city="Agartha", street="33 Thomas Street", zip="[REDACTED]"
        ),
    )

    # 2. Инициализация процессора
    processor = OrderProcessor()

    # 3. Обработка заказа
    try:
        processor.Process(order)
    except Exception as e:
        print(f"Failed to process order: {e}", file=sys.stderr)
        sys.exit(1)

    # 4. Работа с обслуживанием
    print("\nTesting Warehouse Stuff:")
    workers: list[WarehouseWorker] = [
        HumanManager(),
        RobotPacker(model="George Droid"),
    ]
    manage_warehouse(workers)


if __name__ == "__main__":
    main()
