import 'package:flutter/material.dart';

class NotificationPage extends StatefulWidget {
  static const route = '/notifications';
  NotificationPage({Key key}) : super(key: key);

  @override
  _NotificationPageState createState() => _NotificationPageState();
}

class _NotificationPageState extends State<NotificationPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Notifications reçues'),
      ),
      body: Center(
        child: Text(
          'Page des notifications reçues',
        ),
      ),
    );
  }
}
