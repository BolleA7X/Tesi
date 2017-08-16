package com.example.alessio.tesi;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.alessio.tesi.Database.AppDB;

import java.io.Serializable;

import static com.example.alessio.tesi.R.layout.session_settings_activity;

public class SessionSettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button endedConfig;
    private Button addSubjectButton;
    private Button addLocationButton;
    private Spinner subjectsSpinner, locationSpinner;
    private CheckBox theory, exercises, project;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_settings_activity);

        endedConfig = (Button) findViewById(R.id.endedConfig);
        endedConfig.setOnClickListener(this);
        addSubjectButton = (Button)findViewById(R.id.addSubjectButton);
        addSubjectButton.setOnClickListener(this);
        addLocationButton = (Button)findViewById(R.id.addLocationButton);
        addLocationButton.setOnClickListener(this);
        theory = (CheckBox)findViewById(R.id.theoryCheckBox);
        exercises = (CheckBox)findViewById(R.id.exercisesCheckBox);
        project = (CheckBox)findViewById(R.id.projectCheckBox);
        //Shared per i checkbox
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Boolean th = pref.getBoolean("th", false);
        Boolean ex = pref.getBoolean("ex", false);
        Boolean pr = pref.getBoolean("pr", false);

        theory.setChecked(th);
        exercises.setChecked(ex);
        project.setChecked(pr);
    }

    @Override
    protected void onResume() {
        updateSpinner();
        super.onResume();
    }
    @Override
    protected void onPause() {
       super.onPause();
    }
    @Override
    protected void onStop(){
        //salva gli Shared per i checkbox
        Boolean th = theory.isChecked();
        Boolean ex = exercises.isChecked();
        Boolean pr = project.isChecked();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("th", th);
        editor.putBoolean("ex", ex);
        editor.putBoolean("pr", pr);
        editor.apply();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //TODO il menu ora come ora non funziona in questa activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.menu_trophies:
                Fragment trophiesFragment = new TrophiesFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.sessionSettingsActivity, trophiesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.menu_settings:
                break;
            case R.id.menu_results:
                Intent intent = new Intent(this,ResultsActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_calendar:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //qua io prendo le informazioni da mandare nella MainActivity (vedi codice in MainActivity)
    //avendolo fatto di fretta non ho messo i controlli nel caso in cui negli spinner non ci sia niente
    //l'idea è prelevo i dati dagli spinner e checkbox, li metto in quell'array di stringhe "dataToSend" e uso l'intent per
    //rispedirli indietro alla MainActivity che li legge in un metodo specifico.
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //bottone OK
            case R.id.endedConfig:
                Intent resultIntent = new Intent();
                String locationName = null;
                if(locationSpinner != null && locationSpinner.getSelectedItem() !=null ) {
                    locationName = (String)locationSpinner.getSelectedItem();
                }

                String subjName = null;
                if(subjectsSpinner != null && subjectsSpinner.getSelectedItem() !=null ) {
                    subjName = (String)subjectsSpinner.getSelectedItem();
                }
                //costruisce la stringa da restituire in MainActivity
                String[] dataToSend = {Boolean.toString(theory.isChecked()),Boolean.toString(exercises.isChecked()),
                        Boolean.toString(project.isChecked()),subjName,locationName};
                resultIntent.putExtra("currentSessionData",dataToSend);
                setResult(Activity.RESULT_OK,resultIntent);
                finish();
                break;
            //Bottone aggiunta materia
            case R.id.addSubjectButton:
                setSubjectFragment subjDialogFragment = new setSubjectFragment ();
                openDialog(subjDialogFragment);
                break;
            //Bottone aggiunta location
            case R.id.addLocationButton:
                setLocationFragment locDialogfragment = new setLocationFragment ();
                openDialog(locDialogfragment);
                break;

        }
    }
    //Funzione che apre i dialog passando un DialogFragment
    public void openDialog(DialogFragment dialogFragment) {
        FragmentManager fm = getFragmentManager();

        dialogFragment.show(fm, "Sample Fragment");
    }

    public void updateSpinner(){
        subjectsSpinner = (Spinner)findViewById(R.id.subjectsSpinner);
        locationSpinner = (Spinner)findViewById(R.id.locationSpinner);

        AppDB db = new AppDB(this);

        //eseguo la query tramite il metodo getSubjects() per ottenere l'ArrayAdapter contenete le info sui corsi
        ArrayAdapter<String> subjects = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,db.getSubjects());
        if(subjects != null) {
            //se l'array è valido lo associo allo spinner.
            subjects.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subjectsSpinner.setAdapter(subjects);
            subjects.notifyDataSetChanged();
        }
        //come sopra ma con i posti
        ArrayAdapter<String> locations = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,db.getLocations());
        if(locations != null) {
            locations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            locationSpinner.setAdapter(locations);
            locations.notifyDataSetChanged();
        }
    }

}
