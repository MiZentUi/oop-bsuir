import Foundation

func main() {
    let order = Order(
        id: "ORD-256-X",
        items: [
            Item(id: "1", name: "Thermal Clips", price: 1500),
            Item(id: "2", name: "UNATCO Pass Card", price: 50)
        ],
        type: "Premium",
        clientEmail: "jeevacation@gmail.com",
        destination: Address(city: "Agartha", street: "33 Thomas Street", zip: "[REDACTED]")
    )

    let processor = OrderProcessor.newOrderProcessor()

    do {
        try processor.process(order)
    } catch {
        fputs("Failed to process order: \(error)\n", stderr)
        exit(1)
    }

    print("\nTesting Warehouse Stuff:")
    let workers: [any WarehouseWorker] = [
        HumanManager(),
        RobotPacker(model: "George Droid")
    ]

    manageWarehouse(workers)
}

main()
