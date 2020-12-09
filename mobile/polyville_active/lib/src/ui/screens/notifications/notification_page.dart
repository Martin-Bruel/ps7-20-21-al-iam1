import 'package:flutter/material.dart';
import 'package:flutter_blue/flutter_blue.dart';

class NotificationPage extends StatefulWidget {
  static const route = '/notifications';
  NotificationPage({Key key}) : super(key: key);

  @override
  _NotificationPageState createState() => _NotificationPageState();
}

class _NotificationPageState extends State<NotificationPage> {
  FlutterBlue flutterBlue = FlutterBlue.instance;

  List<String> devices = ['devices :'];
  bool scanDone = false;

  void scan() {
    setState(() => devices.clear());
    // Start scanning
    flutterBlue.startScan(timeout: Duration(seconds: 4));

    // Listen to scan results
    var subscription = flutterBlue.scanResults.listen((results) {
      // do something with scan results
      for (ScanResult r in results) {
        print(
            '##################################################################');
        print('${r.device.name} found! rssi: ${r.rssi}');
        print(
            '##################################################################');
        if (r.device.name != ' ')
          setState(
              () => devices.add('${r.device.name} found! rssi: ${r.rssi}'));
      }
    });

    // Stop scanning
    flutterBlue.stopScan();
    setState(() => scanDone = true);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Notifications reçues'),
      ),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Column(
            children: [
              Text(
                'Page des notifications reçues',
              ),
              ElevatedButton(
                onPressed: scan,
                child: Text('scan'),
              ),
              scanDone
                  ? Expanded(
                      child: SingleChildScrollView(
                        child: Column(
                          children: [for (var item in devices) Text(item)],
                        ),
                      ),
                    )
                  : Container(),
            ],
          ),
        ),
      ),
    );
  }
}
