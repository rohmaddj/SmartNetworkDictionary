package one.rdj.com.smartnetworkdictionary;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by gentafatur on 28/10/16.
 */

public class DictionaryDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAMES = "dictionary";
    private static final int DATABASE_VERSION = 5;

    public DictionaryDatabase(Context context) {

        super(context, DATABASE_NAMES, null, DATABASE_VERSION);

    }
}
