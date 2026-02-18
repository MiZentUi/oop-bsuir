//! Модуль `infrastructure`
//!
//! Имитация работы с БД и внешними сервисами.

use chrono::Utc;
use std::{
    fs::OpenOptions, io::Write, os::unix::fs::OpenOptionsExt, thread::sleep, time::Duration,
};

use crate::models::Order;

/// [`RandomSQLDatabase`] - имитация тяжелой базы данных
pub struct RandomSQLDatabase {
    pub connection_string: String,
}

impl RandomSQLDatabase {
    pub fn new() -> RandomSQLDatabase {
        RandomSQLDatabase {
            connection_string: "random://root:password@localhost:228/shop".to_string(),
        }
    }

    /// Сохранение заказа в "базу данных"
    pub fn save_order(&self, order: &Order, total: f64) -> Result<(), std::io::Error> {
        println!("Connecting to RandomSQL at {}...", self.connection_string);
        sleep(Duration::from_millis(500));

        let mut file = OpenOptions::new()
            .append(true)
            .create(true)
            .write(true)
            .read(false)
            .mode(0o644)
            .open("orders_db.txt")?;

        let record = format!(
            "[{}] ID: {} | Type: {} | Total : {:.2}\n",
            Utc::now().to_rfc3339(), order.id, order.r#type, total
        );
        file.write_all(record.as_bytes())?;
        println!("Order saved successfully.");
        Ok(())
    }
}

/// [`SmtpMailer`] - имитация почтового сервиса
pub struct SmtpMailer {
    pub server: String,
}

impl SmtpMailer {
    pub fn send_html_email(&self, to: &str, subject: &str, body: &str) {
        println!(">> Connecting to SMTP server {}...", self.server);
        println!(">> Sending EMAIL to {to}\n   Subject: {subject}\n   Body: {body}");
    }
}
