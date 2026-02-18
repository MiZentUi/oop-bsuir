//! # Файл `main.rs`
//! Точка входа в приложение

use std::process::exit;

use orders::{
    models::{Address, Item, Order},
    processor::OrderProcessor,
    staff::{HumanManager, RobotPacker, WarehouseWorker, manage_warehouse},
};

fn main() {
    // 1. Создание записи
    let order = Order {
        id: "ORD-256-X".to_string(),
        items: vec![
            Item {
                id: "1".to_string(),
                name: "Thermal Clips".to_string(),
                price: 1500.0,
            },
            Item {
                id: "2".to_string(),
                name: "UNATCO Pass Card".to_string(),
                price: 50.0,
            },
        ],
        r#type: "Premium".to_string(),
        client_email: "jeevacation@gmail.com".to_string(),
        destination: Address {
            city: "Agartha".to_string(),
            street: "33 Thomas Street".to_string(),
            zip: "[REDACTED]".to_string(),
        },
    };

    // 2. Инициализация процессора
    let processor = OrderProcessor::new();

    // 3. Обработка заказа
    if let Err(err) = processor.process(&order) {
        eprintln!("Failed to process order: {}", err);
        exit(1);
    }

    // 4. Работа с обслуживанием
    println!("\nTesting Warehouse Stuff:");
    let workers : [Box<dyn WarehouseWorker>; 2]= [
        Box::new(HumanManager {}),
        Box::new(RobotPacker {
            model: "George Droid".to_string(),
        }),
    ];
    manage_warehouse(&workers);
}
