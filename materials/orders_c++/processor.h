#pragma once
#include <iostream>
#include <stdexcept>
#include <string>
#include <format>
#include "infrastructure.h"

class OrderProcessor {
    RandomSQLDatabase* database;
    SmtpMailer* mailer;

public:
    OrderProcessor() : database(new RandomSQLDatabase()), mailer(new SmtpMailer())
    {
        mailer->Server = "smtp.google.com";
    }

    ~OrderProcessor() {
        delete database;
        delete mailer;
    }

    void Process(Order order) //#include <boost> ???
    {
        std::cout << "--- Processing Order " << order.ID << " ---\n";

        //1. Логика валидации
        if (order.Items.size() == 0)
        {
            throw std::runtime_error("order must have at leat one item");
        }
        if (order.Destination.City == "")
        {
            throw std::runtime_error("destination city is required");
        }

        //2. Логика расчёта суммы
        double total;
        for (auto item : order.Items)
        {
            total += item.Price;
        }

        //3. логика скидок и налогов
        if (order.Type == "Standard")
        {
            total = total * 1.2;
        }
        else if (order.Type == "Premium")
        {
            total = (total * 0.9) * 1.2;
        }
        else if (order.Type == "Budget")
        {
            if (order.Items.size() > 3)
            {
                std::cout << "Budget orders cannot have more than 3 items. Skipping.\n";
                return;
            }
        }
        else if (order.Type == "International")
        {
            total = total * 1.5;
            if (order.Destination.City == "Nowhere")
            {
                throw std::runtime_error("cannot ship to Nowhere");
            }
        }
        else 
        {
            throw std::runtime_error("unknown order type");
        }

        //4. Логика сохранения
        bool err = this->database->SaveOrder(order, total);
        if (err != 0)
        {
            throw std::runtime_error("database error!"); //NO INTERPOLATION!
        } //very bad

        //5. Логика уведомлений
        std::string emailBody = std::format("Your order {} is confirmed!\nTotal: {}.\n", order.ID, total);
        this->mailer->SendHtmlEmail(order.ClientEmail, "Order Confirmation", emailBody);
    }
};