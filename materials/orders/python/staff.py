# =========================================================
# Файл: staff.py
# Описание: Система управления персоналом склада.
# =========================================================
from abc import ABC, abstractmethod
from typing import List


class WarehouseWorker(ABC):
    @abstractmethod
    def process_order(self):
        pass

    @abstractmethod
    def attend_meeting(self):
        pass

    @abstractmethod
    def get_rest(self):
        pass

    @abstractmethod
    def swinging_the_lead(self):
        pass


# HumanManager - Человек
class HumanManager(WarehouseWorker):
    def process_order(self):
        print("Manager is processing logic...")

    def attend_meeting(self):
        print("Manager is boring at the meeting...")

    def get_rest(self):
        print("Manager is taking a break...")

    def swinging_the_lead(self):
        print("Manager is watching reels...")


# RobotPacker - Робот
class RobotPacker(WarehouseWorker):
    def __init__(self, model: str):
        self.model = model

    def process_order(self):
        print(f"Robot {self.model} is packing boxes...")

    def get_rest(self):
        print("Robot was taken for maintenance")

    def attend_meeting(self):
        print("ERROR: Robot cannot attend meetings")

    def swinging_the_lead(self):
        raise RuntimeError("CRITICAL ERROR: Robot cannot waste our money (we hope so)")


# manage_warehouse - функция, которая работает со списком работников
def manage_warehouse(workers: List[WarehouseWorker]):
    print("\n--- Warehouse Shift Started ---")
    for worker in workers:
        worker.process_order()
        worker.attend_meeting()
        worker.get_rest()
        worker.swinging_the_lead()