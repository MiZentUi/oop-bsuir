#include <iostream>
#include <vector>
#include "models.h"
#include "infrastructure.h"
#include "processor.h"
#include "staff.h"

int main ()
{
    // 1. Создание заказа
    Order order{"ORD-256-X",                                   //Id
                {{"1", "Thermal Clips", 1500},                 //Item[]
                {"2", "UNATCO Pass Card", 50}},
                "Premium",                                     //Type
                "jeevacation@gmail.com",                       //ClientEmail
                {"Agartha", "33 Thomas Street", "[REDACTED]"}, //Destination                 
                };
    
    // 2. Инициализация процессора
    OrderProcessor processor;

    // 3. Обработка заказа
    processor.Process(order);

    // 4. Работа с обслуживанием

    std::cout << "\nTesting Warehouse Stuff:";
    const std::vector<WarehouseWorker*> workers = {};
    workers.push_back(HumanManager{});
    workers.push_back(RobotPacker{"George Droid"}); //fix

    ManageWarehouse(workers);
    return 0;
}