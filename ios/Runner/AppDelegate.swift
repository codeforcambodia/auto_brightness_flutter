import UIKit
import Flutter

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    
    let controller : FlutterViewController = window?.rootViewController as! FlutterViewController
    
    let batteryChannel = FlutterMethodChannel( // Register Your Platform Specific
        name: "daveat/brightness",
        binaryMessenger: controller.binaryMessenger
    )
    
    batteryChannel.setMethodCallHandler({
        (call: FlutterMethodCall, result: @escaping FlutterResult) -> Void in
        // Note: this method is invoked on the UI thread.
        // Handle battery messages.
        
        // Implementation
//        guard call.method == "getBrightnessLevel" else {
//            result(FlutterMethodNotImplemented)
//            return
//        }
        
        if (call.method == "getBrightnessLevel"){
            result(self.getBrightness())
        } else if (call.method == "setBrightnessLevel"){
            UIScreen.main.brightness = CGFloat(1.0)
        } else if (call.method == "setDefaultBrightness"){
            let args = call.arguments as! Double
//                args["value"] as Int
//            let toDefaultLevel = Double( args["value"].Int() / 100)
            // Default value 0.0 to 1.0
            
            self.setBrightness(brightness: args)
            result(args)
        }
//        self.receiveBatteryLevel(result: result)
    })
    
    GeneratedPluginRegistrant.register(with: self)
    
    return super.application(
        application, didFinishLaunchingWithOptions: launchOptions
    )
  }
//    private func enableMonitorBrightness(result: FlutterResult, device: var){
//        if (device.batteryState == UIDevice.BatteryState.unknown){
//            result(FlutterError(
//                code: "UNAVIALABLE",
//                message: "Battery info unavailable",
//                details: nil
//                )
//            )
//        } else {
//            setBrightness()
//            result(getBrightness())
//        }
//    }
    
    func setBrightness(brightness: Double = 1.0) -> Void {
        UIScreen.main.brightness = CGFloat(brightness)
    }
    
    func getBrightness() -> Int {
        return Int(UIScreen.main.brightness*100)
    }
    
    

}
