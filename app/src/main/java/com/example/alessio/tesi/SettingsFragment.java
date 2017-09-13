package com.example.alessio.tesi;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.alessio.tesi.Database.AppDB;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends PreferenceFragment {

    ListPreference deleteCourse, deleteLocation, deleteAll, logout;
    CheckBoxPreference checkPomodoro;
    Fragment frg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        frg = this;

        AppDB db = new AppDB(getActivity());

        //ottengo il riferimento ai ListPreference
        deleteCourse = new ListPreference(getActivity());
        deleteLocation = new ListPreference(getActivity());
        deleteAll = new ListPreference(getActivity());
        logout = new ListPreference(getActivity());
        checkPomodoro = new CheckBoxPreference(getActivity());

        deleteCourse = (ListPreference)findPreference("delete_course_preference");
        deleteLocation = (ListPreference)findPreference("delete_location_preference");
        deleteAll = (ListPreference)findPreference("delete_all_preference");
        logout = (ListPreference)findPreference("logout_button");
        checkPomodoro = (CheckBoxPreference)findPreference("use_pomodoro_mode");

       checkPomodoro.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean pomodoroChecked = checkPomodoro.isChecked();
                // Passo !pomodoroChecked (cioè il suo opposto) perché il suo valore quando chiamo updatePomodoro() è ancora
                // quello vecchio (cioè quello prima di cliccarlo), perché si aggiorna dopo il 'return true' qua sotto.
                updatePomodoro(!pomodoroChecked);
                return true;
            }
        });

        //valori di base del pulsante che permette di eliminare tutti i dati
        CharSequence[] deleteAllEntries = {"No","Si"};
        CharSequence[] deleteAllEntriesValues = {"false","true"};

        //valori base degli altri due: prelevati dal db
        ArrayList<String> entries1 = db.getSubjects();
        entries1.add(0,getActivity().getResources().getString(R.string.none));
        ArrayList<String> entries2 = db.getLocations();
        entries2.add(0,getActivity().getResources().getString(R.string.none));
        CharSequence[] deleteCourseEntries = entries1.toArray(new CharSequence[entries1.size()]);
        CharSequence[] deleteLocationEntries = entries2.toArray(new CharSequence[entries2.size()]);

        //setto questi valori
        deleteAll.setEntries(deleteAllEntries);
        deleteAll.setEntryValues(deleteAllEntriesValues);
        deleteAll.setDefaultValue("false");

        deleteCourse.setEntries(deleteCourseEntries);
        deleteCourse.setEntryValues(deleteCourseEntries);
        deleteCourse.setDefaultValue(getActivity().getResources().getString(R.string.none));

        deleteLocation.setEntries(deleteLocationEntries);
        deleteLocation.setEntryValues(deleteLocationEntries);
        deleteLocation.setDefaultValue(getActivity().getResources().getString(R.string.none));

        logout.setEntries(deleteAllEntries);
        logout.setEntryValues(deleteAllEntriesValues);
        logout.setDefaultValue(getActivity().getResources().getString(R.string.none));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater,container,savedInstanceState);

        int color = ContextCompat.getColor(getActivity(), android.R.color.white);
        view.setBackgroundColor(color);

        //listener per vedere se l'utente preme su "si" nel pulsante per eliminare tutto
        deleteAll.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //query al db per eliminare i dati e reimposto il valore standard del pulsante su "no"
                //altrimenti elimina i dati in loop
                deleteAll.setDefaultValue("false");
                if(newValue.toString().equals("true")) {
                    AppDB appDB = new AppDB(getActivity());
                    appDB.deleteAll();
                    //Cancella le sharedPref
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove("subj");
                    editor.remove("th");
                    editor.remove("ex");
                    editor.remove("pr");
                    editor.apply();
                    //Chiama il metodo in Main che aggiorna la TextView
                    ((MainActivity)getActivity()).updateTimer(true);
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.allDataDeleted), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        //listener per vedere quale corso è stato selezionato per l'eliminazione
        deleteCourse.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                deleteCourse.setDefaultValue(getActivity().getResources().getString(R.string.none));
                String newVal = newValue.toString();
                if(!newVal.equals(getActivity().getResources().getString(R.string.none))) {
                    AppDB db = new AppDB(getActivity());
                    db.deleteCourse(newVal);
                    Toast.makeText(getActivity(),newVal+" "+getActivity().getResources().getString(R.string.deleted),Toast.LENGTH_SHORT).show();
                    if(db.getTrophies()[10].getUnlocked() == 0){
                        db.unlockTrophy(11);
                        Toast t = Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.unlockTrophy)+"11",Toast.LENGTH_LONG);
                        t.show();
                    }
                }
                return false;
            }
        });

        //listener per vedere quale posto è stato selezionato per l'eliminazione
        deleteLocation.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                deleteLocation.setDefaultValue(getActivity().getResources().getString(R.string.none));
                String newVal = newValue.toString();
                if(!newVal.equals(getActivity().getResources().getString(R.string.none))) {
                    AppDB db = new AppDB(getActivity());
                    db.deleteLocation(newVal);
                    Toast.makeText(getActivity(),newVal+" "+getActivity().getResources().getString(R.string.deleted),Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        //listener per vedere se è stato premuto il tasto di logout
        logout.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(newValue.toString().equals("true")) {
                    logout.setDefaultValue("false");
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("logged", false);
                    editor.putString("loggedAs", "");
                    editor.commit();
                    //riavvio l'app
                    Intent intent = frg.getActivity().getPackageManager().getLaunchIntentForPackage(frg.getActivity().getPackageName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return false;
            }
        });

        return view;
    }

    private void updatePomodoro(boolean pomodoroChecked){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("pomodoro", pomodoroChecked);
        editor.apply();
        ((MainActivity)getActivity()).updateTimer(true);
    }
}
