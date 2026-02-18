//! Модуль `processor` - основная бизнес-логика
use crate::{
    infrastructure::{RandomSQLDatabase, SmtpMailer},
    models::Order,
};

pub struct OrderProcessor {
    pub database: RandomSQLDatabase,
    pub mailer: SmtpMailer,
}

impl OrderProcessor {
    pub fn new() -> OrderProcessor {
        OrderProcessor {
            database: RandomSQLDatabase::new(),
            mailer: SmtpMailer {
                server: "smtp.google.com".to_string(),
            },
        }
    }
    /// TODO: `order`: reference or value
    pub fn process(&self, order: &Order) -> Result<(), String> {
        println!("Processing order {}", order.id);

        // 1. Логика валидации
        if order.items.len() == 0 {
            return Err("order must have at least one item".to_string());
        }
        if order.destination.city == "" {
            return Err("destination city is required".to_string());
        }

        // 2. Логика расчета суммы
        let mut total: f64 = 0.0;
        for item in &order.items {
            total += item.price;
        }

        // 3.  Логика скидок и налогов
        match order.r#type.as_str() {
            // Стандартный налог
            "Standard" => total = total * 1.2,
            // Скидка 10% + налог
            "Premium" => total = (total * 0.9) * 1.2,
            "Budget" => {
                if order.items.len() > 3 {
                    println!("Budget orders cannot have more than 3 items. Skipping.");
                    return Ok(());
                }
            }
            "International" => {
                total = total * 1.5; // Таможенный сбор
                if order.destination.city == "Nowhere" {
                    return Err("cannot ship to Nowhere".to_string());
                }
            }
            _ => return Err("unknown order type".to_string()),
        }
        // 4. Логика сохранения
        if let Err(err) = self.database.save_order(order, total) {
            return Err(format!("database error: {}", err.to_string()))
        }
        todo!()
    }
}
