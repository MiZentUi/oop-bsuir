import Foundation

protocol WarehouseWorker {
    func processOrder()
    func attendMeeting()
    func getRest()
    func swingingTheLead()
}

struct HumanManager: WarehouseWorker {
    func processOrder() {
        print("Manager is processing logic...")
    }

    func attendMeeting() {
        print("Manager is boring at the meeting...")
    }

    func getRest() {
        print("Manager is taking a break...")
    }

    func swingingTheLead() {
        print("Manager is watching reels...")
    }
}

struct RobotPacker: WarehouseWorker {
    let model: String

    func processOrder() {
        print("Robot \(model) is packing boxes...")
    }

    func getRest() {
        print("Robot was taken for maintenance")
    }

    func attendMeeting() {
        print("ERROR: Robot cannot attend meetings")
    }

    func swingingTheLead() {
        fatalError("CRITICAL ERROR: Robot cannot waste our money (we hope so)")
    }
}

func manageWarehouse(_ workers: [any WarehouseWorker]) {
    print("\n--- Warehouse Shift Started ---")
    for worker in workers {
        worker.processOrder()
        worker.attendMeeting()
        worker.getRest()
        worker.swingingTheLead()
    }
}
