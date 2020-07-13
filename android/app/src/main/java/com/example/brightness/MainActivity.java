package com.example.brightness;

import androidx.annotation.NonNull;
import io.flutter.app.FlutterActivity;
//import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.app.FlutterPluginRegistry;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.Settings;


public class MainActivity extends FlutterActivity {

    private static final String CHANNEL = "daveat/brightness";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
            new MethodChannel.MethodCallHandler() {
                @Override
                public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {

                    if (!canWrite()){
                        allowWritePermission();
                    }

                    Object brightness = getBrightnessLevel();
                    if (canWrite()){
                        if( call.method.equals("getBrightnessLevel")){
                            setModeBrightness(0);
                            setBrightnessLevel(200);
                            result.success(brightness);
                        } else {
                            result.notImplemented();
                        }
                    }
                }
            }
        );
    }

    boolean canWrite(){
        if (VERSION.SDK_INT >= VERSION_CODES.M){
            return Settings.System.canWrite(this);
        } else {
            return true;
        }
    }

    void allowWritePermission(){
        if (VERSION.SDK_INT >= VERSION_CODES.M){
            Intent intent = new Intent(
                    Settings.ACTION_MANAGE_WRITE_SETTINGS
            );
            startActivity(intent);
        }
    }

    void setModeBrightness(int mode){
        Settings.System.putInt(
                this.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                mode
        );
    }

    void setBrightnessLevel(int level){
        Settings.System.putInt(
                this.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,
                level
        );
    }

    int getBrightnessLevel(){
        return Settings.System.getInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
//        return Settings.System.getInt(
//            this.contentResolver,
//            Settings.System.SCREEN_BRIGHTNESS
//        );
        // Int brightnessLevel;
        // if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP){

        // }
    }
}
