package com.example.bombisz.interactivehome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import bluetooth.BluetoothModule;

public class MainActivity extends AppCompatActivity{

    private Button but, buttonOpenServo,buttonCloseServo,buttonStandBy;
    public LinearLayout ll;
    public LinearLayout.LayoutParams lp;
    public int id_but;
    public static int index,id,counter, counterRedLed,counterGreenLed,counterBlueLed,i;//i before change nonstatic
    public static List<Button> btnList = new ArrayList<Button>();
    public static ArrayList<Integer> idButtonList = new ArrayList<Integer>( Collections.nCopies(10, 0));
    private FloatingActionButton addButton;
    public BluetoothModule bluetoothModule = new BluetoothModule();
    public static String tag = "ih";
    private int k=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (new Thread(bluetoothModule)).start();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initButtonLayout();
        buttonOpenServo = (Button)findViewById( R.id.openServo );
        buttonCloseServo = (Button)findViewById( R.id.closeServo );
        buttonStandBy = (Button)findViewById( R.id.standByServo );
        servoControl();
    }

    private void servoControl(){
        openServo();
        standByServo();
        closeServo();
    }

    private void standByServo() {
        buttonStandBy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothModule.offServo();
            }
        } );
    }

    private void closeServo(){

        buttonCloseServo.setOnTouchListener( new View.OnTouchListener() {

            final Handler h = new Handler();
            Runnable r = new Runnable(){
                public void run(){
                    h.postDelayed(this, 1000);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    final int delay = 1000;
                    BluetoothModule.openServoBackward();
                    h.postDelayed(r, delay);
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    BluetoothModule.offServo();
                    h.removeCallbacks(r);
                    return true;
                }

                return false;

            }
        } );
    }

    private void openServo() {
        buttonOpenServo.setOnTouchListener( new View.OnTouchListener() {

            final Handler h = new Handler();
            Runnable r = new Runnable(){
                public void run(){
                    h.postDelayed(this, 1000);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    BluetoothModule.openServoInc();
                        final int delay = 1000;
                        h.postDelayed(r, delay);
                        return true;
                    }

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        BluetoothModule.offServo();
                        h.removeCallbacks(r);
                        return true;
                    }

                    return false;

            }
        } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void initButtonLayout() {
        i=0;
        ll = (LinearLayout) findViewById(R.id.tableButtons);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addButton = (FloatingActionButton) findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtons();
            }
        });
    }

    
    @SuppressLint("DefaultLocale")
     public void addButtons() {
        final Toast toast = Toast.makeText(getApplicationContext(), "button number: " + i + " has been added", Toast.LENGTH_LONG);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 700);
        but = new Button(this);
        Log.i( tag,"i in addButtons " + i);
        but.setId(i);
        btnList.add(i, but);
        id_but = but.getId();
        but.setText(String.format("button id %d", id_but));
        ll.addView(btnList.get(i),lp);
        i++;
        Log.i( tag,"button been added");
        getCreatedButton();
        buttonClicked();
    }

    public void refreshView(View view){
        int a = btnList.size();
        Log.i( tag,"size of btnList in refresh method1 " + a);
        final Toast toast = Toast.makeText( getApplicationContext(),"Number of buttons " + a,Toast.LENGTH_LONG );
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 700);
        ll.removeViewAt(index+2);
        int aa = btnList.size();
        Log.i( tag,"size of btnList in refresh method2 " + aa);
        initButtonLayout();
    }

    public void openServo(View view){
        BluetoothModule.openServoInc();
    }
    public void redLed(View view){
        if((counterRedLed % 2)==0){
            Log.i( tag,"counter red led is even" );
            Log.i( tag,"red led is on" );
            BluetoothModule.turnOnRgbRedOn();
        }else {
            BluetoothModule.turnOnRgbRedOff();
            Log.i( tag,"counter red led is odd" );
            Log.i( tag,"red led is off" );
        }
        counterRedLed++;
    }

    public void greenLed(View view){
        if((counterGreenLed % 2)==0){
            Log.i( tag,"counter green led is even!!!" );
            Log.i( tag,"green led is on" );
            BluetoothModule.turnOnRgbGreenOn();
        }else {
            BluetoothModule.turnOnRgbGreenOff();
            Log.i( tag,"counter green led is oddAAAAA" );
            Log.i( tag,"green led is off" );
        }
        counterGreenLed++;
    }

    public void blueLed(View view){
        if((counterBlueLed % 2)==0){
            Log.i( tag,"counter blue led is even" );
            Log.i( tag,"blue led is on" );
            BluetoothModule.turnOnRgbBlueOn();
        }else {
            BluetoothModule.turnOnRgbBlueOff();
            Log.i( tag,"counter blue led is odd" );
            Log.i( tag,"blue led is off" );
        }
        counterBlueLed++;
    }

    public void switcher(){
        if((counter % 2)==0){
            Log.i( tag,"counter is even" );
            BluetoothModule.switchOn();
        }else {
            Log.i( tag,"counter is odd" );
            BluetoothModule.switchOff();
        }
    }

    public void buttonClicked(){
        for (i = 0; i < btnList.size(); i++)  {
            //btnList.get(i).setTag(i);  // Use index of btnList as the tag
            btnList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indexButton=0;

                    for (i = 0; i < btnList.size(); i++) {

                        if (btnList.get(i).getId() == v.getId()) {
                            indexButton = i;
                            //index = i;
                            final Toast toast = Toast.makeText(getApplicationContext(), "index " + indexButton, Toast.LENGTH_LONG);
                            toast.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel();
                                }
                            }, 700);

                            Log.i( tag, "clicked button id  " + btnList.get( i ).getId());
                            Log.i( tag, "id value in click method " + idButtonList.get( id ));

//                            if((btnList.get( i ).getId()) == id){
                            if((btnList.get( i ).getId()) == idButtonList.get( id )){
                                switcher();//odkomentuj
                                counter++;
                            }
                        }
                    }
                }
            });

        }
    }

    public void getCreatedButton(){
        for (i = 0; i < btnList.size(); i++)  {
            //btnList.get(i).setTag(i);  // Use index of btnList as the tag
            btnList.get(i).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int indexButton=0;
                    for (i = 0; i < btnList.size(); i++) {
                        if (btnList.get(i).getId() == v.getId()) {
                            indexButton=i;
                            index = i;
                            final Toast toast = Toast.makeText(getApplicationContext(), "index " + indexButton, Toast.LENGTH_LONG);
                            toast.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel();
                                }
                            }, 700);

                          try {
                                BluetoothModule.bluetoothSocket.close();
                                Log.i( tag, "socket closed" );
                            } catch (IOException e) {
                                Log.i( tag, "closing socket failed" );
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(MainActivity.this, ButtForm.class);
                            startActivity(intent);
                        }
                    }
                    return true;
                }
            });

        }
    }
}
