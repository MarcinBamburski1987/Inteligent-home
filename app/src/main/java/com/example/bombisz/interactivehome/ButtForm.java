package com.example.bombisz.interactivehome;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ButtForm extends MainActivity {

    private EditText buttonId,socketNumber;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_butt_form );
        spinner = (Spinner) findViewById( R.id.spinner );
        adapter = ArrayAdapter.createFromResource( this,R.array.typesInSpinner,android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" is selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
    }

    public void onClickNextButton(View view){
        buttonId = (EditText)findViewById( R.id.inputName );
        socketNumber = (EditText)findViewById( R.id.socketNr );
//        String strName = buttonId.getText().toString();
//        String strSocketNr = socketNumber.getText().toString();
        MainActivity.id = Integer.parseInt( buttonId.getText().toString() );
        MainActivity.idButtonList.add( id,id );

        Log.i( tag, "id value in click method " + id);
        Log.i( tag, "size of id button list " + MainActivity.idButtonList.size());
        finish();
    }

    public void onClickDeleteButton(View view){
        deleteButton = (Button) findViewById( R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = btnList.size();
                Log.i( tag,"size of btnList del method1 " + a);
                btnList.remove(index);
                int aa = btnList.size();
                Log.i( tag,"size of btnList del method2 " + aa);
                finish();
            }
        });
    }
}
