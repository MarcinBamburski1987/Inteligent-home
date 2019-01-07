package bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.bombisz.interactivehome.MainActivity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Bombisz on 2017-04-14.
 */

public class BluetoothModule extends Thread {
    private static final UUID uuid = UUID.fromString("44f6d5e4-1ed5-11e7-93ae-92361f002671");
    public BluetoothAdapter mBluetoothAdapter;
    static public BluetoothSocket bluetoothSocket;
    public BluetoothDevice device;
    String mac = "20:16:07:22:17:15";
    static String msgLedOn = "p13.1";
    static String msgLedOff = "p13.0";
    static String rgbRedON = "p9.255,0,0";
    static String rgbRedOff = "p9.0,0,0";
    static String rgbGreenON = "p10.0,255,0";
    static String rgbGreenOff = "p10.0,0,0";
    static String rgbBlueON = "p11.0,0,255";
    static String rgbBlueOff = "p11.0,0,0";

    static String servoOff = "p3.0";
    static String servoForward = "p3.+40";
    static String servoBackward = "p3.-40";


    public BluetoothModule() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Log.i( MainActivity.tag, "Bluetooth is not supported");
        } else {
            Log.i(MainActivity.tag, "Bluetooth is supported");
        }

        if (mBluetoothAdapter.isEnabled() == true) {
            Log.i(MainActivity.tag, "Bluetooth is enabled");
        }

        pairedDevice();

        try {
            device = mBluetoothAdapter.getRemoteDevice(mac);
            Log.i(MainActivity.tag, "get Device ok");
        } catch (IllegalArgumentException e) {
            Log.i(MainActivity.tag, "get Device failed");
        }

        try {
            Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
            bluetoothSocket = (BluetoothSocket) m.invoke(device, 1);
            Log.i( MainActivity.tag,"socket created" );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        String tagSock = "socket value in const";

        Log.i( tagSock, String.valueOf( bluetoothSocket ) );
    }

    @Override
    public void run() {
        Log.i( "Hello","Thread" );
        connectBt();
    }


    public void connectBt() {
        mBluetoothAdapter.cancelDiscovery();
        Log.i( MainActivity.tag, "socket value in connectBt method " + String.valueOf( bluetoothSocket ) );

        try {
            bluetoothSocket.connect();
            Log.i(MainActivity.tag,"Socket connected" );
        } catch (IOException e) {
            Log.i(MainActivity.tag, "Socket not connected " + String.valueOf(e));
        }
    }

    public static void switchOn(){
        try {
            bluetoothSocket.getOutputStream().write(msgLedOn.getBytes());
            Log.i(MainActivity.tag,"switch on" );
        } catch (IOException e) {
            Log.i(MainActivity.tag,"sending data failed" );
            e.printStackTrace();
        }
    }//LED

    public static void switchOff(){
        try {
            bluetoothSocket.getOutputStream().write(msgLedOff.getBytes());
            Log.i(MainActivity.tag,"switch off" );
        } catch (IOException e) {
            Log.i(MainActivity.tag,"sending data failed" );
            e.printStackTrace();
        }
    }//LED

    public static void turnOnRgbRedOn(){
        try {
            bluetoothSocket.getOutputStream().write(rgbRedON.getBytes());
            Log.i(MainActivity.tag,"switch on" );
        } catch (IOException e) {
            Log.i(MainActivity.tag,"sending data failed" );
            e.printStackTrace();
        }
    }

    public static void turnOnRgbRedOff(){
        try {
            bluetoothSocket.getOutputStream().write(rgbRedOff.getBytes());
            Log.i(MainActivity.tag,"switch on" );
        } catch (IOException e) {
            Log.i(MainActivity.tag,"sending data failed" );
            e.printStackTrace();
        }
    }

    public static void turnOnRgbGreenOn(){
        try {
            bluetoothSocket.getOutputStream().write(rgbGreenON.getBytes());
            Log.i(MainActivity.tag,"switch on!!!" );
        } catch (IOException e) {
            Log.i(MainActivity.tag,"sending data failed" );
            e.printStackTrace();
        }
    }

    public static void turnOnRgbGreenOff(){
        try {
            bluetoothSocket.getOutputStream().write(rgbGreenOff.getBytes());
            Log.i(MainActivity.tag,"switch on" );
        } catch (IOException e) {
            Log.i(MainActivity.tag,"sending data failed" );
            e.printStackTrace();
        }
    }

    public static void turnOnRgbBlueOn(){
        try {
            bluetoothSocket.getOutputStream().write(rgbBlueON.getBytes());
            Log.i(MainActivity.tag,"switch on" );
        } catch (IOException e) {
            Log.i(MainActivity.tag,"sending data failed" );
            e.printStackTrace();
        }
    }

    public static void turnOnRgbBlueOff(){
        try {
            bluetoothSocket.getOutputStream().write(rgbBlueOff.getBytes());
            Log.i(MainActivity.tag,"switch on" );
        } catch (IOException e) {
            Log.i(MainActivity.tag,"sending data failed" );
            e.printStackTrace();
        }
    }

    public static void openServoInc(){
        try {
            bluetoothSocket.getOutputStream().write(servoForward.getBytes());
            Log.i(MainActivity.tag,"servo forward" );
        } catch (IOException e) {
            Log.i(MainActivity.tag,"sending data failed" );
            e.printStackTrace();
        }
    }

    public static void offServo(){
        try {
            bluetoothSocket.getOutputStream().write(servoOff.getBytes());
            Log.i(MainActivity.tag,"servo off" );
        } catch (IOException e) {
            Log.i(MainActivity.tag,"sending data failed" );
            e.printStackTrace();
        }
    }

    public static void openServoBackward(){
        try {
            bluetoothSocket.getOutputStream().write(servoBackward.getBytes());
            Log.i(MainActivity.tag,"servo backward" );
        } catch (IOException e) {
            Log.i(MainActivity.tag,"sending data failed" );
            e.printStackTrace();
        }
    }

    public void pairedDevice(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            Log.i(MainActivity.tag,"Paired devices ");

            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                Log.i( MainActivity.tag,"Device name " + deviceName);
                String deviceHardwareAddress = device.getAddress();
                Log.i( MainActivity.tag,"Device MAC address " + deviceHardwareAddress);
            }

        }else{
            Log.i( MainActivity.tag,"devices not paired");
        }
    }
}
