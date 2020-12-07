import 'package:flutter/material.dart';

class SettingsPage extends StatefulWidget {
  static const route = '/settings';
  SettingsPage({Key key}) : super(key: key);

  @override
  _SettingsPageState createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Paramètres'),
      ),
      body: Center(
        child: Text(
          'Page des paramètres',
        ),
      ),
    );
  }
}
