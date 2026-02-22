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

class RandomSQLDatabase
{
  std::string ConnectionString;
public:
  RandomSQLDatabase() : ConnectionString("random://root:password@localhost:228/shop") {std::cout << "randomsqldatabase" << std::endl;} //!!!w

  bool SaveOrder(const Order& order, double total)
  {
    std::cout << "Connecting to RandomSQL at" << ConnectionString << "...\n";
    std::this_thread::sleep_for(std::chrono::milliseconds(500));
    std::ofstream file("orders_db.txt", std::ios::app);
    if (!file.is_open())
    {
      return 1;
    }
    auto now = std::chrono::system_clock::now();
    auto local_now = std::chrono::zoned_time{std::chrono::current_zone(), now};
    std::string time_str = std::format("{:%Y-%m-%dT%H:%M:%S}", local_now);
    std::cout << '[' << time_str << "] ID: " << order.ID << " | Type: " << order.Type << " | Total: " << total << "\n";

    file.close();

    std::cout << "Order saved successfully." << std::endl;
    return 0;
  }
};

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