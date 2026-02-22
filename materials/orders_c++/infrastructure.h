#pragma once
#include "models.h"
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <thread>
#include <ctime>
#include <chrono>
#include <format>

// =========================================================
// Файл: infrastructure.cpp
// Описание: Имитация работы с БД и внешними сервисами.
// =========================================================

// RandomSQLDatabase - имитация тяжелой базы данных
class RandomSQLDatabase
{
    std::string ConnectionString;

public:
    RandomSQLDatabase() : ConnectionString("random://root:password@localhost:228/shop") {}

    // Сохранение заказа в "базу данных"
    void SaveOrder(const Order& order, double total) {
        std::cout << "Connecting to RandomSQL at " << ConnectionString << "...\n";
        std::this_thread::sleep_for(std::chrono::milliseconds(500)); // Имитация задержки сети

        std::ofstream file("orders_db.txt", std::ios::app);
        if (!file.is_open())
        {
            throw std::runtime_error("Could not open orders_db.txt !");
        }

        auto now = std::chrono::system_clock::now();
        auto local_now = std::chrono::zoned_time{std::chrono::current_zone(), now};
        std::string time_str = std::format("{:%Y-%m-%dT%H:%M:%S}", local_now);
        file << '[' << time_str << "] ID: " << order.ID << " | Type: " << order.Type << " | Total: " << total << "\n";

        if (!file)
        {
            throw std::runtime_error("Could not write to a file !");
        }

        file.close();
        std::cout << "Order saved successfully." << std::endl;
        
        return;
    }
};

// SmtpMailer - имитация почтового сервиса
class SmtpMailer
{
public:
    std::string Server;
    
    void SendHtmlEmail(std::string to, std::string subject, std::string body)
    {
        std::cout << ">> Connecting to SMTP server " << Server << " ...\n";
        std::cout << ">> Sending EMAIL to "
                  << to << "\nSubject : " << subject << "\n   Body : " << body << "\n";
    }
};