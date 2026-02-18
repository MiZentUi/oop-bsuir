use std::{
    fs::OpenOptions,
    io::Write,
    os::unix::fs::OpenOptionsExt,
    thread::sleep,
    time::Duration,
};

use crate::models::Order;

pub struct RandomSQLDatabase {
    pub connection_string: String,
}

impl RandomSQLDatabase {
    pub fn new() -> RandomSQLDatabase {
        RandomSQLDatabase {
            connection_string: "random://root:password@localhost:228/shop".to_string(),
        }
    }
    pub fn save_order(&self, order: &Order, total: f64) -> Result<(), std::io::Error> {
        println!("Connecting to RandomSQL at {}...", self.connection_string);
        sleep(Duration::from_millis(
            500 * 1, /*todo: time.Milliseconds */
        ));

        let mut file = OpenOptions::new()
            .append(true)
            .create(true)
            .write(true)
            .read(false)
            .mode(0o644)
            .open("orders_db.txt")?;

        // Can it be modelled in Rust?
        // defer file.Close()

        // TODO: a `chrono` or `time` crate is required to format time as in RFC3339
        let record = format!(
            "[{}] ID: {} | Type: {} | Total : {:.2}\n",
            "ONCE", order.id, order.r#type, total
        );
        file.write_all(record.as_bytes())?;
        println!("Order saved successfully.");
        Ok(())
    }
}

pub struct SmtpMailer {
    pub server: String,
}

impl SmtpMailer {
    pub fn send_html_email(&self, to: &str, subject: &str, body: &str) {
        println!(">> Connecting to SMTP server {}...", self.server);
        println!(">> Sending EMAIL to {to}\n   Subject: {subject}\n   Body: {body}");
    }
}
