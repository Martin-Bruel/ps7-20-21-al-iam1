import 'package:flutter/material.dart';
import 'package:polyville_active/src/core/shop/Shop.dart';

class SearchPage extends StatefulWidget {
  static const route = '/search';
  SearchPage({Key key}) : super(key: key);

  @override
  _SearchPageState createState() => _SearchPageState();
}

class _SearchPageState extends State<SearchPage> {
  Shop shop;

  void callApi(String v) {
    setState(() {
      print(v);
      Shop.fetchShop().then((value) => shop = value);
      print(shop.address);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Recherche de magasins'),
      ),
      body: Padding(
        padding: EdgeInsets.all(15),
        child: Column(
          children: [
            Text('Page de recherche'),
            TextFormField(
              onFieldSubmitted: (value) => callApi(value),
            ),
          ],
        ),
      ),
    );
  }
}
