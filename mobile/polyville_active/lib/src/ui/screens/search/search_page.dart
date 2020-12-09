import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:polyville_active/src/core/shop/Shop.dart';

class SearchPage extends StatefulWidget {
  static const route = '/search';
  SearchPage({Key key}) : super(key: key);

  @override
  _SearchPageState createState() => _SearchPageState();
}

class _SearchPageState extends State<SearchPage> {
  List<Shop> shops = <Shop>[];

  void callApi(String v) {
    Shop.fetchShop(int.parse(v)).then((value) {
      value != null
          ? setState(() => shops.add(value))
          : print(v + ' not found');
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
              keyboardType: TextInputType.number,
              inputFormatters: [
                FilteringTextInputFormatter.digitsOnly,
              ],
            ),
            Expanded(
              child: ListView.builder(
                itemCount: shops.length,
                itemBuilder: (context, index) => Text(shops[index].address),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
