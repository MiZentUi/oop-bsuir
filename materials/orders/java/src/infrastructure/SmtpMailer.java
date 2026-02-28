package infrastructure;

/**
 * SmtpMailer - имитация почтового сервиса
 */
public class SmtpMailer {
    private String server;

    public SmtpMailer(String server) {
        this.server = server;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void SendHtmlEmail(String to, String subject, String body) {
        System.out.printf(">> Connecting to SMTP server %s...\n", server);
        System.out.printf(">> Sending EMAIL to %s\n   Subject: %s\n   Body: %s\n", to, subject, body);
    }
}