import 'dart:convert';

import 'package:http/http.dart' as http;

class Shop {
  int id;
  String name;
  String address;
  String shopType;

  Shop({this.id, this.shopType, this.name, this.address});

  factory Shop.fromJson(Map<String, dynamic> json) {
    return Shop(
      id: json['id'],
      address: json['address'],
      name: json['name'],
      shopType: json['type'],
    );
  }

  static Future<Shop> fetchShop() async {
    final response = await http.get('http://10.0.2.2:8080/hello-world');

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      return Shop.fromJson(jsonDecode(response.body));
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      print('failed');
      throw Exception('Failed to load shop');
    }
  }
}
