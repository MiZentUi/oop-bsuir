//! Модуль `staff`.
//!
//! Описание: система управления персоналом склада.

pub trait WarehouseWorker {
    fn process_order(&self);
    fn attend_meeting(&self);
    fn get_rest(&self);
    fn swing_the_lead(&self);
}

/// [`HumanManager`] - человек
pub struct HumanManager;

impl WarehouseWorker for HumanManager {
    fn process_order(&self) {
        println!("Manager is processing logic...");
    }

    fn attend_meeting(&self) {
        println!("Manager is boring at the meeting...");
    }

    fn get_rest(&self) {
        println!("Manager is taking a break...");
    }

    fn swing_the_lead(&self) {
        println!("Manager is watching reels...");
    }
}

/// [`RobotPacker`] - робот
pub struct RobotPacker {
    pub model: String,
}

impl WarehouseWorker for RobotPacker {
    fn process_order(&self) {
        println!("Robot {} is packing boxes...", self.model);
    }

    fn attend_meeting(&self) {
        println!("ERROR: Robot cannot attend meetings");
    }

    fn get_rest(&self) {
        println!("Robot was taken for maintenance");
    }

    fn swing_the_lead(&self) {
        panic!("CRITICAL ERROR: Robot cannot waste our money (we hope so)");
    }
}

/// [`manage_warehouse`] - функция, которая работает со списком работников
pub fn manage_warehouse(workers: &[Box<dyn WarehouseWorker>]) {
    println!("\n--- Warehouse Shift Started ---");
    for worker in workers {
        worker.process_order();
        worker.attend_meeting();
        worker.get_rest();
        worker.swing_the_lead();
    }
}
