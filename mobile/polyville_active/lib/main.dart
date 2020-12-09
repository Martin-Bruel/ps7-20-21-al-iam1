import 'package:flutter/material.dart';
import 'package:polyville_active/src/ui/screens/home/home_page.dart';
import 'package:polyville_active/src/ui/screens/notifications/notification_page.dart';
import 'package:polyville_active/src/ui/screens/search/search_page.dart';

import 'src/ui/screens/settings/settings_page.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.lime,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(title: 'Polyville active'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _selectedIndex = 0;

  static List<Widget> _pages = <Widget>[
    NotificationPage(),
    HomePage(),
    SettingsPage(),
    SearchPage(),
  ];

  void onTabTapped(dynamic index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: _pages.elementAt(_selectedIndex),
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectedIndex,
        onTap: onTabTapped,
        type: BottomNavigationBarType.fixed,
        items: [
          BottomNavigationBarItem(
            icon: Icon(Icons.list),
            label: "Notifications",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: "Accueil",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.settings),
            label: "Paramètre",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.search),
            label: 'Recherches',
          ),
        ],
      ),
    );
  }
}
