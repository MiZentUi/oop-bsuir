# =========================================================
# Файл: models.py
# Описание: Модели данных системы.
# =========================================================
from dataclasses import dataclass
from typing import List


# Item - товар в заказе
@dataclass
class Item:
    id: str
    name: str
    price: float


# Address - адрес доставки
@dataclass
class Address:
    city: str
    street: str
    zip: str


# Order - заказ
@dataclass
class Order:
    id: str
    items: List[Item]
    type: str  # "Standard", "Premium", "Budget", "International"
    client_email: str
    destination: Address