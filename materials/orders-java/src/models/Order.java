package models;

import java.util.List;

/**
 * Order - заказ
 */
public class Order {
    private String id;
    private List<Item> items;
    private String type; // "Standard", "Premium", "Budget", "International"
    private String clientEmail;
    private Address destination;

    public Order(String id, String type, List<Item> items, String clientEmail, Address destination) {
        this.id = id;
        this.items = items;
        this.type = type;
        this.clientEmail = clientEmail;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }
}
