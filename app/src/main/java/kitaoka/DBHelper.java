package kitaoka;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kitaoka castro marcos on 16/09/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "petgotaxidb.sqlite";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
