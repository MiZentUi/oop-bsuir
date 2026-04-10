import Foundation

final class RandomSQLDatabase {
    let connectionString: String

    init(connectionString: String) {
        self.connectionString = connectionString
    }

    static func newMySQLDatabase() -> RandomSQLDatabase {
        return RandomSQLDatabase(connectionString: "random://root:password@localhost:228/shop")
    }

    func saveOrder(_ order: Order, total: Double) throws {
        print("Connecting to RandomSQL at \(connectionString) ...")
        Thread.sleep(forTimeInterval: 0.5)

        let fileURL = URL(fileURLWithPath: "orders_db.txt")
        let record = "[\(rfc3339Now())] ID: \(order.id) | Type: \(order.type) | Total: \(String(format: "%.2f", total))\n"

        if FileManager.default.fileExists(atPath: fileURL.path) == false {
            FileManager.default.createFile(atPath: fileURL.path, contents: nil)
        }

        guard let data = record.data(using: .utf8) else { return }
        let handle = try FileHandle(forWritingTo: fileURL)
        defer { try? handle.close() }

        try handle.seekToEnd()
        try handle.write(contentsOf: data)

        print("Order saved successfully.")
    }

    private func rfc3339Now() -> String {
        let fmt = ISO8601DateFormatter()
        fmt.formatOptions = [.withInternetDateTime]
        return fmt.string(from: Date())
    }
}

final class SmtpMailer {
    let server: String

    init(server: String) {
        self.server = server
    }

    func sendHtmlEmail(to: String, subject: String, body: String) {
        print(">> Connecting to SMTP server \(server)...")
        print(">> Sending EMAIL to \(to)\n   Subject: \(subject)\n   Body: \(body)")
    }
}
