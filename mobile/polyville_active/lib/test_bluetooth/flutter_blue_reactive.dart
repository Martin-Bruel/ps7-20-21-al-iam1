import 'package:flutter/material.dart';
import 'package:flutter_reactive_ble/flutter_reactive_ble.dart';

class FlutterBlueReactive extends StatefulWidget {
  FlutterBlueReactive({Key key}) : super(key: key);

  @override
  _FlutterBlueReactiveState createState() => _FlutterBlueReactiveState();
}

class _FlutterBlueReactiveState extends State<FlutterBlueReactive> {
  final _ble = FlutterReactiveBle();

  void scan() {
    _ble.scanForDevices(withServices: [], scanMode: ScanMode.lowLatency).listen(
        (device) {
      if (device.name.length != 0)
        print('found : ${device.name}, rssi: ${device.rssi}');
    }, onError: (error) {
      print('error : $error');
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Flutter Reactive BLE'),
        ),
        body: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Column(
            children: [
              ElevatedButton(
                onPressed: scan,
                child: Text('scan'),
              ),
            ],
          ),
        ));
  }
}
