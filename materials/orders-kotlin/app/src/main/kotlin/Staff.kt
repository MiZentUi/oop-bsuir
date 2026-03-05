// =========================================================
// Файл: Staff.kt
// Описание: Система управления персоналом склада.
// =========================================================

interface WarehouseWorker {
    fun processOrder()
    fun attendMeeting()
    fun getRest()
    fun swingingTheLead()
}

// HumanManager - Человек
class HumanManager : WarehouseWorker {

    override fun processOrder() {
        println("Manager is processing logic...")
    }

    override fun attendMeeting() {
        println("Manager is boring at the meeting...")
    }

    override fun getRest() {
        println("Manager is taking a break...")
    }

    override fun swingingTheLead() {
        println("Manager is watching reels...")
    }
}

// RobotPacker - Робот
class RobotPacker(val model: String) : WarehouseWorker {

    override fun processOrder() {
        println("Robot $model is packing boxes...")
    }

    override fun getRest() {
        println("Robot was taken for maintenance")
    }

    override fun attendMeeting() {
        println("ERROR: Robot cannot attend meetings")
    }

    override fun swingingTheLead() {
        error("CRITICAL ERROR: Robot cannot waste our money (we hope so)")
    }
}

// manageWarehouse - функция, которая работает со списком работников
fun manageWarehouse(workers: List<WarehouseWorker>) {
    println("\n--- Warehouse Shift Started ---")
    for (worker in workers) {
        worker.processOrder()
        worker.attendMeeting()
        worker.getRest()
        worker.swingingTheLead()
    }
}
