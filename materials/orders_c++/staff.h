#pragma once
#include <iostream>
#include <vector>
#include <string>

// =========================================================
// Файл: staff.cpp
// Описание: Система управления персоналом склада.
// =========================================================

class WarehouseWorker {
public:
  virtual ~WarehouseWorker() = default;
  virtual void ProcessOrder() = 0;
  virtual void AttendMeeting() = 0;
  virtual void GetRest() = 0;
  virtual void SwingingTheLead() = 0;
};

// HumanManager - Человек
class HumanManager : public WarehouseWorker {
public:
    void ProcessOrder() override {
        std::cout << "Manager is processing logic..." << std::endl;
    }

    void AttendMeeting() override {
        std::cout << "Manager is boring at the meeting..." << std::endl;
    }

    void GetRest() override {
        std::cout << "Manager is taking a break..." << std::endl;
    }

    void SwingingTheLead() override {
        std::cout << "Manager is watching reels..." << std::endl;
    }
};

// RobotPacker - Робот
class RobotPacker : public WarehouseWorker {
public:
    std::string Model;

    explicit RobotPacker(std::string model) : Model(std::move(model)) {}

    void ProcessOrder() override {
        std::cout << "Robot " << Model << " is packing boxes..." << std::endl;
    }

    void GetRest() override {
        std::cout << "Robot was taken for maintenance" << std::endl;
    }

    void AttendMeeting() override {
        std::cout << "ERROR: Robot cannot attend meetings" << std::endl;
    }

    void SwingingTheLead() override {
        throw std::runtime_error("CRITICAL ERROR: Robot cannot waste our money (we hope so)");
    }
};

// ManageWarehouse - функция, которая работает со списком работников
void ManageWarehouse(const std::vector<WarehouseWorker*>& workers) {
    std::cout << "\n--- Warehouse Shift Started ---" << std::endl;
    for (auto* worker : workers) {
        worker->ProcessOrder();
        worker->AttendMeeting();
        worker->GetRest();
        worker->SwingingTheLead();
    }
}