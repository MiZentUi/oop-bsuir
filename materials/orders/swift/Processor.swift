import Foundation

enum OrderProcessingError: Error, CustomStringConvertible {
    case validation(String)
    case unknownOrderType
    case cannotShipToNowhere
    case database(String)

    var description: String {
        switch self {
        case .validation(let msg): return msg
        case .unknownOrderType: return "unknown order type"
        case .cannotShipToNowhere: return "cannot ship to Nowhere"
        case .database(let msg): return "database error: \(msg)"
        }
    }
}

final class OrderProcessor {
    private let database: RandomSQLDatabase
    private let mailer: SmtpMailer

    init(database: RandomSQLDatabase, mailer: SmtpMailer) {
        self.database = database
        self.mailer = mailer
    }

    static func newOrderProcessor() -> OrderProcessor {
        return OrderProcessor(
            database: .newMySQLDatabase(),
            mailer: SmtpMailer(server: "smtp.google.com")
        )
    }

    func process(_ order: Order) throws {
        print("--- Processing Order \(order.id) ---")

        if order.items.isEmpty {
            throw OrderProcessingError.validation("order must have at least one item")
        }
        if order.destination.city.isEmpty {
            throw OrderProcessingError.validation("destination city is required")
        }

        var total: Double = 0
        for item in order.items {
            total += item.price
        }

        switch order.type {
        case "Standard":
            total = total * 1.2
        case "Premium":
            total = (total * 0.9) * 1.2
        case "Budget":
            if order.items.count > 3 {
                print("Budget orders cannot have more than 3 items. Skipping.")
                return
            }
        case "International":
            total = total * 1.5
            if order.destination.city == "Nowhere" {
                throw OrderProcessingError.cannotShipToNowhere
            }
        default:
            throw OrderProcessingError.unknownOrderType
        }

        do {
            try database.saveOrder(order, total: total)
        } catch {
            throw OrderProcessingError.database(error.localizedDescription)
        }

        let emailBody = "<h1>Your order \(order.id) is confirmed!</h1><p>Total: \(String(format: "%.2f", total))</p>"
        mailer.sendHtmlEmail(to: order.clientEmail, subject: "Order Confirmation", body: emailBody)
    }
}
