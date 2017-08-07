package com.example.alessio.tesi;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alessio.tesi.Database.AppDB;
import com.example.alessio.tesi.Database.Session;

import java.io.Serializable;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private int timeVal=90;
    private long mytime;
    private TextView timerValue;
    private ImageButton imageB;
    private SeekBar seekBar;
    private TextView currentSubject;
    private boolean isOn;
    String[] sessionData;

    CountDownTimer cTimer = null;

    //Menu
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

        Toast msg;  // va tolto poi   <-------------------

        switch(id){
            case R.id.menu_trophies:
                Fragment trophiesFragment = new TrophiesFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.mainActivity, trophiesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                msg = Toast.makeText(this, "Trophies menu clicked", Toast.LENGTH_LONG);
                msg.show();

                break;
            case R.id.menu_settings:
                msg = Toast.makeText(this, "Settings menu clicked", Toast.LENGTH_LONG);
                msg.show();
                break;
            case R.id.menu_results:
                msg = Toast.makeText(this, "Results menu clicked", Toast.LENGTH_LONG);
                msg.show();
                break;
            case R.id.menu_calendar:
                msg = Toast.makeText(this, "Calendar menu clicked", Toast.LENGTH_LONG);
                msg.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to widgets
        imageB = (ImageButton)findViewById(R.id.startTimerButton);
        timerValue = (TextView)findViewById(R.id.timerValue);
        seekBar = (SeekBar)findViewById(R.id.setTimerSeekBar);
        currentSubject = (TextView)findViewById(R.id.currentSubjectLabel);

        // set listeners
        imageB.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(seekBarListener);

        timerValue.setText(String.valueOf(timeVal));

        //floating button
        fab = (FloatingActionButton) findViewById(R.id.setButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SessionSettingsActivity.class);
                intent.putExtra("dataToPass","");
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(data != null) {
            sessionData = data.getStringArrayExtra("currentSessionData");
            String subj = sessionData[3];
            String locat = sessionData[4];
            if(subj != null)
                currentSubject.setText(subj);
        }
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startTimerButton:
                if (isOn) {
                    stop();
                }
                else {
                    if(sessionData != null)
                        start();
                    else
                        Toast.makeText(this,"Devi selezionare corso e tipo della sessione",Toast.LENGTH_SHORT);
                }
                break;
        }
    }

    private void start(){
        isOn = true;
        imageB.setImageResource(R.drawable.only_stop1);
        mytime = timeVal*1000;
        seekBar.setEnabled(false);
        fab.setClickable(false);
        fab.setVisibility(View.INVISIBLE);

        //start timer function
        cTimer = new CountDownTimer(mytime, 1000) {
            public void onTick(long millisUntilFinished) {
                String s = String.valueOf( millisUntilFinished / 1000);
                timerValue.setText(s);
            }
            public void onFinish() {
                timerValue.setText("OK!");
                saveSession(timeVal);
            }
        };
        cTimer.start();
    }

    private void stop(){
        saveSession(timeVal-Integer.parseInt(timerValue.getText().toString()));
    }

    //*****************************************************
    // Event handler for the SeekBar
    //*****************************************************
    private OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            timeVal = seekBar.getProgress();
            timerValue.setText(String.valueOf(timeVal));
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void saveSession(int duration) {
        Session session = new Session(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,duration,
                                      Session.stringToInt(sessionData[0]),Session.stringToInt(sessionData[1]),
                                      Session.stringToInt(sessionData[2]),sessionData[4]/*luogo*/,sessionData[3]/*corso*/);
        AppDB db = new AppDB(this);
        if(session != null)
            db.insertSession(session);

        imageB.setImageResource(R.drawable.only_play);
        isOn = false;
        seekBar.setEnabled(true);
        fab.setClickable(true);
        fab.setVisibility(View.VISIBLE);

        timerValue.setText(String.valueOf(timeVal));
        if(cTimer!=null){
            cTimer.cancel();
        }
    }

}
