import Foundation

struct Item {
    let id: String
    let name: String
    let price: Double
}

struct Address {
    let city: String
    let street: String
    let zip: String
}

struct Order {
    let id: String
    let items: [Item]
    let type: String
    let clientEmail: String
    let destination: Address
}
