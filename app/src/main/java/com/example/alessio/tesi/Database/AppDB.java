package com.example.alessio.tesi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.renderer.scatter.ChevronUpShapeRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AppDB {

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("Creating db","");
            db.execSQL(CREATE_SESSION_TABLE);
            db.execSQL(CREATE_LOCATION_TABLE);
            db.execSQL(CREATE_COURSE_TABLE);
            db.execSQL(CREATE_TROPHY_TABLE);

            //inserting all trophies
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (1, "+Trophy.BRONZE+", 1)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (2, "+Trophy.BRONZE+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (3, "+Trophy.SILVER+", 1)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (4, "+Trophy.BRONZE+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (5, "+Trophy.SILVER+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (6, "+Trophy.GOLD+", 1)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (7, "+Trophy.BRONZE+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (8, "+Trophy.SILVER+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (9, "+Trophy.SILVER+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (10, "+Trophy.BRONZE+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (11, "+Trophy.BRONZE+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (12, "+Trophy.BRONZE+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (13, "+Trophy.BRONZE+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (14, "+Trophy.BRONZE+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (15, "+Trophy.SILVER+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (16, "+Trophy.GOLD+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (17, "+Trophy.SILVER+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (18, "+Trophy.BRONZE+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (19, "+Trophy.SILVER+", 0)");
            db.execSQL("INSERT INTO "+TROPHY_TABLE+" VALUES (20, "+Trophy.PLATINUM+", 1)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Dropping tables","");
            db.execSQL(DROP_SESSION_TABLE);
            db.execSQL(DROP_LOCATION_TABLE);
            db.execSQL(DROP_COURSE_TABLE);
            db.execSQL(DROP_TROPHY_TABLE);
            onCreate(db);
        }
    }

    //database constants
    public static final String DB_NAME = "appDB.db";
    public static final int DB_VERSION = 7;

    //session constants
    public static final String SESSION_TABLE = "session";

    public static final String SESSION_ID = "_id";
    public static final int SESSION_ID_COL = 1;

    public static final String SESSION_YEAR = "year";
    public static final int SESSION_YEAR_COL = 2;

    public static final String SESSION_MONTH = "month";
    public static final int SESSION_MONTH_COL = 3;

    public static final String SESSION_DAY = "day";
    public static final int SESSION_DAY_COL = 4;

    public static final String SESSION_DURATION = "duration";
    public static final int SESSION_DURATION_COL = 5;

    public static final String SESSION_THEORY = "theory";
    public static final int SESSION_THEORY_COL = 6;

    public static final String SESSION_EXERCISE = "exercise";
    public static final int SESSION_EXERCISE_COL = 7;

    public static final String SESSION_PROJECT = "project";
    public static final int SESSION_PROJECT_COL = 8;

    public static final String SESSION_LOCATION_NAME = "location_name";
    public static final int SESSION_LOCATION_NAME_COL = 9;

    public static final String SESSION_COURSE_NAME = "course_name";
    public static final int SESSION_COURSE_ID_COL = 10;

    //location constants
    public static final String LOCATION_TABLE = "location";

    public static final String LOCATION_NAME = "_name";
    public static final int LOCATION_NAME_COL = 1;

    public static final String LOCATION_LATITUDE = "latitude";
    public static final int LOCATION_LATITUDE_COL = 2;

    public static final String LOCATION_LONGITUDE = "longitude";
    public static final int LOCATION_LONGITUDE_COL = 3;

    //course constants
    public static final String COURSE_TABLE = "course";

    public static final String COURSE_NAME = "_name";
    public static final int COURSE_NAME_COL = 1;

    //trophy constants
    public static final String TROPHY_TABLE = "trophy";

    public static final String TROPHY_ID = "_id";
    public static final int TROPHY_ID_COL = 1;

    public static final String TROPHY_COLOR = "color";
    public static final int TROPHY_COLOR_COL = 2;

    public static final String TROPHY_UNLOCKED = "unlocked";
    public static final int TROPHY_UNLOCKED_COL = 3;

    //counter constants
    public static final String COUNTER_TABLE = "counter";

    public static final String COUNTER_NAME = "_name";
    public static final int COUNTER_NAME_COL = 1;

    public static final String COUNTER_VALUE = "value";
    public static final int COUNTER_VALUE_COL = 2;

    //CREATE TABLE statements
    public static final String CREATE_SESSION_TABLE =
            "CREATE TABLE " + SESSION_TABLE + " ( " +
                    SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SESSION_YEAR + " INTEGER, " +
                    SESSION_MONTH + " INTEGER, " +
                    SESSION_DAY + " INTEGER, " +
                    SESSION_DURATION + " INTEGER, " +
                    SESSION_THEORY + " INTEGER, " +
                    SESSION_EXERCISE + " INTEGER, " +
                    SESSION_PROJECT + " INTEGER, " +
                    SESSION_LOCATION_NAME + " TEXT, " +
                    SESSION_COURSE_NAME + " TEXT);";

    public static final String CREATE_LOCATION_TABLE =
            "CREATE TABLE " + LOCATION_TABLE + " ( " +
                    LOCATION_NAME + " TEXT PRIMARY KEY, " +
                    LOCATION_LATITUDE + " REAL, " +
                    LOCATION_LONGITUDE + " REAL);";

    public static final String CREATE_COURSE_TABLE =
            "CREATE TABLE " + COURSE_TABLE + " ( " +
                    COURSE_NAME + " TEXT PRIMARY KEY);";

    public static final String CREATE_TROPHY_TABLE =
            "CREATE TABLE " + TROPHY_TABLE + " ( " +
                    TROPHY_ID + " INTEGER PRIMARY KEY, " +
                    TROPHY_COLOR + " TEXT, " +
                    TROPHY_UNLOCKED + " INTEGER);";

    public static final String CREATE_COUNTER_TABLE =
            "CREATE TABLE " + COUNTER_TABLE + " ( " +
                    COUNTER_NAME + " TEXT PRIMARY KEY, " +
                    COUNTER_VALUE + " INTEGER);";

    //DROP TABLE statements
    public static final String DROP_SESSION_TABLE =
            "DROP TABLE IF EXISTS " + SESSION_TABLE;

    public static final String DROP_LOCATION_TABLE =
            "DROP TABLE IF EXISTS " + LOCATION_TABLE;

    public static final String DROP_COURSE_TABLE =
            "DROP TABLE IF EXISTS " + COURSE_TABLE;

    public static final String DROP_TROPHY_TABLE =
            "DROP TABLE IF EXISTS " + TROPHY_TABLE;

    public static final String DROP_COUNTER_TABLE =
            "DROP TABLE IF EXISTS " + COUNTER_TABLE;

    //attributes and methods
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public AppDB(Context context) {
        dbHelper = new DBHelper(context,DB_NAME,null,DB_VERSION);
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if(db != null)
            db.close();
    }

    //METODI PER ESEGUIRE SPECIFICHE QUERY: NON SPECIFICO I DETTAGLI DI OGNI SINGOLO METODO
    //query per inserire nuova materia
    public void insertSubject(Course course) {
        ContentValues cv = new ContentValues();
        cv.put(COURSE_NAME,course.getName());
        this.openWriteableDB();
        long rowID = db.insert(COURSE_TABLE,null,cv);
        this.closeDB();
    }

    //query per inserire nuovo posto
    public void insertLocation(Location location) {
        ContentValues cv = new ContentValues();
        cv.put(LOCATION_NAME,location.getName());
        cv.put(LOCATION_LATITUDE,location.getLatitude());
        cv.put(LOCATION_LONGITUDE,location.getLongitude());
        this.openWriteableDB();
        long rowID = db.insert(LOCATION_TABLE,null,cv);
        this.closeDB();
    }

    //query per inserire dati di sessione appena finita
    public void insertSession(Session session) {
        ContentValues cv = new ContentValues();
        cv.put(SESSION_YEAR,session.getYear());
        cv.put(SESSION_MONTH,session.getMonth());
        cv.put(SESSION_DAY,session.getDay());
        cv.put(SESSION_DURATION,session.getDuration());
        cv.put(SESSION_THEORY,session.getTheory());
        cv.put(SESSION_EXERCISE,session.getExercise());
        cv.put(SESSION_PROJECT,session.getProject());
        cv.put(SESSION_LOCATION_NAME,session.getLocation_name());
        cv.put(SESSION_COURSE_NAME,session.getCourse_name());
        this.openWriteableDB();
        long rowId = db.insert(SESSION_TABLE,null,cv);
        this.closeDB();
    }

    //query per ottenere le materie (per relativo spinner)
    public ArrayList<String> getSubjects() {
        this.openReadableDB();
        String[] args = new String[] {COURSE_NAME};
        Cursor cursor = db.query(COURSE_TABLE,args,null,null,null,null,null);
        ArrayList<String> subjects = new ArrayList<String>();
        while(cursor.moveToNext())
           subjects.add(cursor.getString(cursor.getColumnIndex(COURSE_NAME)));
        if(cursor != null)
            cursor.close();
        this.closeDB();
        return subjects;
    }

    //query per ottenere i posti (per relativo spinner)
    public ArrayList<String> getLocations() {
        this.openReadableDB();
        String[] args = new String[] {LOCATION_NAME};
        Cursor cursor = db.query(LOCATION_TABLE,args,null,null,null,null,null);
        ArrayList<String> locations = new ArrayList<String>();
        while(cursor.moveToNext())
            locations.add(cursor.getString(cursor.getColumnIndex(COURSE_NAME)));
        if(cursor != null)
            cursor.close();
        this.closeDB();
        return locations;
    }

    //query per ottenere i trofei (per avere i dati nel relatico fragment)
    public Trophy[] getTrophies() {
        this.openReadableDB();
        String[] args = new String[] {TROPHY_COLOR,TROPHY_UNLOCKED};
        Cursor cursor = db.query(TROPHY_TABLE,args,null,null,null,null,null);
        Trophy[] trophies = new Trophy[20];
        int i = 0;
        while(cursor.moveToNext()) {
            Trophy temp = new Trophy(i+1,cursor.getString(cursor.getColumnIndex(TROPHY_COLOR)),cursor.getInt(cursor.getColumnIndex(TROPHY_UNLOCKED)));
            trophies[i] = temp;
            ++i;
        }
        if(cursor !=null)
            cursor.close();
        this.closeDB();
        return trophies;
    }

    //query per ottenere i dati da inserire nel grafico a torta dei corsi
    public ArrayList<PieEntry> getSubjectsPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        this.openReadableDB();
        String[] args = new String[] {"SUM("+SESSION_DURATION+")"};
        //1) ottengo la somma delle durate totali
        Cursor cursor = db.query(SESSION_TABLE,args,null,null,null,null,null);
        cursor.moveToFirst();
        int totalDuration = cursor.getInt(0);
        //2) ottengo la lista dei corsi, uso un iteratore per comodità
        ArrayList<String> subjects = this.getSubjects();
        Iterator<String> iterator = subjects.iterator();
        //3)itero per ottenere la somma delle durate dei singoli corsi e calcolare le singole percentuali
        while(iterator.hasNext()) {
            String course = iterator.next();
            String where = SESSION_COURSE_NAME + "= ?";
            String[] whereArgs = {course};
            if(!db.isOpen())
                this.openReadableDB();
            cursor = db.query(SESSION_TABLE,args,where,whereArgs,null,null,null);
            cursor.moveToFirst();
            float percent = (float)cursor.getInt(0)/(float)totalDuration*100;
            //4)aggiungo ogni percentuale calcolata e il nome del corso ai dati del grafico
            entries.add(new PieEntry(percent,course));
        }
        if(cursor != null)
            cursor.close();
        this.closeDB();
        return entries;
    }

    //query per ottenere i dati da inserire nel grafico a torta dei tipi
    public ArrayList<PieEntry> getTypesPieChartData() {
        float[] percents = new float[3];
        ArrayList<PieEntry> entries = new ArrayList<>();
        this.openReadableDB();
        String[] args = new String[] {"COUNT("+SESSION_ID+")"};
        //1) ottengo il numero di sessioni
        Cursor cursor = db.query(SESSION_TABLE,args,null,null,null,null,null);
        cursor.moveToFirst();
        float numberOfSessions = (float)cursor.getInt(0);
        Log.d("Sessioni totali",String.valueOf(numberOfSessions));
        //2) itero sui tre tipi di sessione e conto le occorrenze per ognuno
        String[] types = {SESSION_THEORY,SESSION_EXERCISE,SESSION_PROJECT};
        for(int i=0;i<3;i++) {
            String where = types[i] + "= ?";
            String[] whereArgs = {"1"};
            if(!db.isOpen())
                this.openReadableDB();
            cursor = db.query(SESSION_TABLE,args,where,whereArgs,null,null,null);
            cursor.moveToFirst();
            //3) calcolo le percentuali assolute per ogni tipo
            percents[i] = ((float)cursor.getInt(0))/numberOfSessions;
            Log.d(types[i],String.valueOf(percents[i]));
        }
        //4) trovo il coefficiente di normalizzazione
        float k = 1/(percents[0] + percents[1] + percents[2]);
        Log.d("k",String.valueOf(k));
        //5) calcolo le percentuali relative per ogni tipo e le inserisco nell'ArrayList
        entries.add(new PieEntry(k*percents[0]*100,SESSION_THEORY));
        entries.add(new PieEntry(k*percents[1]*100,SESSION_EXERCISE));
        entries.add(new PieEntry(k*percents[2]*100,SESSION_PROJECT));
        if(cursor != null)
            cursor.close();
        this.closeDB();
        return entries;
    }

    public String getMostFrequentLocation() {
        String result = "";
        this.openReadableDB();
        String[] args = {SESSION_LOCATION_NAME,"COUNT("+SESSION_LOCATION_NAME+") AS cont"};
        String order = "cont DESC";
        Cursor cursor = db.query(SESSION_TABLE,args,null,null,SESSION_LOCATION_NAME,null,order,"1");
        while(cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndex(SESSION_LOCATION_NAME));
            Log.println(Log.DEBUG, "cursor: ", result);
        }
        if(cursor != null)
            cursor.close();
        this.closeDB();
        return result;
    }

    public int getTotalTime() {
        int totalDuration = 0;
        this.openReadableDB();
        String[] args = new String[] {"SUM("+SESSION_DURATION+")"};
        Cursor cursor = db.query(SESSION_TABLE,args,null,null,null,null,null);
        while(cursor.moveToNext())
            totalDuration = cursor.getInt(0);
        if(cursor != null)
            cursor.close();
        this.closeDB();
        return totalDuration;
    }

    public void deleteAll() {
        this.openWriteableDB();
        db.execSQL(DROP_SESSION_TABLE);
        db.execSQL(DROP_LOCATION_TABLE);
        db.execSQL(DROP_COURSE_TABLE);
        db.execSQL(CREATE_SESSION_TABLE);
        db.execSQL(CREATE_LOCATION_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
        this.closeDB();
    }

    public int deleteCourse(String name) {
        String where = COURSE_NAME + "= ?";
        String[] whereArgs = {name};
        this.openWriteableDB();
        int rowCount = db.delete(COURSE_TABLE,where,whereArgs);
        this.closeDB();
        return rowCount;
    }

    public int deleteLocation(String name) {
        String where = LOCATION_NAME + "= ?";
        String[] whereArgs = {name};
        this.openWriteableDB();
        int rowCount = db.delete(LOCATION_TABLE,where,whereArgs);
        this.closeDB();
        return rowCount;
    }
}