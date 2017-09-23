package one.rdj.com.smartnetworkdictionary;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by gentafatur on 28/10/16.
 */

public class DbObject {

    private static DictionaryDatabase dbHelper;
    private SQLiteDatabase db;

    public DbObject(Context context) {

        dbHelper = new DictionaryDatabase(context);
        this.db = dbHelper.getReadableDatabase();
    }

    public void closeDbConnection(){
        if(this.db != null){
            this.db.close();
        }
    }

    public SQLiteDatabase getDbConnection() {
        return this.db;
    }
}
