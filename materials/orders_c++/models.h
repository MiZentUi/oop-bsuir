#pragma once
#include <vector>
#include <string>

struct Item {
  std::string ID;
  std::string Name;
  double Price;
};

struct Address {
  std::string City;
  std::string Street;
  std::string Zip;
};

struct Order {
  std::string ID;
  std::vector<Item> Items;
  std::string Type;
  std::string ClientEmail;
  Address Destination;
};