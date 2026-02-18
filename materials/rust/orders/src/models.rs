//! Модуль `models` - модели данных системы

/// [`Item`] - товар в заказе
pub struct Item {
    pub id: String,
    pub name: String,
    pub price: f64
}

/// [`Address`] - адрес доставки
pub struct Address {
    pub city: String,
    pub street: String,
    pub zip: String
}

/// [`Order`] - заказ
pub struct Order {
    pub id: String,
    pub items: Vec<Item>,
    /// "Standard", "Premium", "Budget", "International"
    pub r#type: String,
    pub client_email: String,
    pub destination: Address
}