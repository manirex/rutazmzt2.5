package kitaoka;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kitaoka castro marcos on 16/09/2016.
 */

public class SQLite{

    private static final String DATABASE_NAME = "petgotaxidb.sqlite";
    public  Cursor rs;
    private  DBHelper helper;
    private SQLiteDatabase db;

    public SQLite(Context context)
    {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public Boolean isOpen(){
        return db.isOpen();
    }

    public Boolean Query(String sql) {
        try {
            Boolean selec=true;
            if(sql.contains("DELETE FROM ")) selec=false;
            else{
                if(sql.contains("CREATE TABLE ")) selec=false;
                else{
                    if(sql.contains("INSERT INTO ")) selec=false;
                    else{
                        if(sql.contains("UPDATE ")) selec=false;
                    }
                }
            }
            if(selec){
                rs = db.rawQuery(sql, null);
                rs.moveToFirst();

            }
            else db.execSQL(sql);

            return true;
        }
        catch(SQLException e){
            //do something
            return false;
        }
    }

}
