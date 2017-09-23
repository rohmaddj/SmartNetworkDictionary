package one.rdj.com.smartnetworkdictionary;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by gentafatur on 29/10/16.
 */

public class DbBackend extends DbObject {

    public DbBackend(Context context){
        super(context);
    }

    public String[] dictionaryWords(){
        String query = "select * from dictionary order by 'word' asc";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> wordTerms = new ArrayList<String>();

        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndexOrThrow("word"));
                wordTerms.add(word);
            }while(cursor.moveToNext());
        }
        cursor.close();
        String[] dictionaryWords = new String[wordTerms.size()];
        dictionaryWords = wordTerms.toArray(dictionaryWords);
        return dictionaryWords;
    }

    public QuizObject getQuizById(int quizId){
        QuizObject quizObject = null;
        String query = "select * from dictionary where _id = " + quizId;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndexOrThrow("word"));
                String meaning = cursor.getString(cursor.getColumnIndexOrThrow("meaning"));
                quizObject = new QuizObject(word, meaning);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return quizObject;
    }
}
