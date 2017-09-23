package one.rdj.com.smartnetworkdictionary;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by gentafatur on 29/10/16.
 */

public class DictionaryActivity extends AppCompatActivity {

    public TextView meaning, word;
    private TextToSpeech convertToSpeech;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        int dictionaryId = bundle.getInt("DICTIONARY_ID");
        int id = dictionaryId +1;

        word = (TextView)findViewById(R.id.words);
        //word = (TextView)findViewById(R.id.meanings);
        meaning = (TextView)findViewById(R.id.meanings);
        Button textToSpeech = (Button)findViewById(R.id.tts);

        DbBackend dbBackend = new DbBackend(DictionaryActivity.this);
        QuizObject allQuizQuestion = dbBackend.getQuizById(id);

        word.setText(allQuizQuestion.getWord());
        meaning.setText(allQuizQuestion.getDefinition());
        textToSpeech.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                final String convertTextToSpeech = meaning.getText().toString();
                convertToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            convertToSpeech.setLanguage(Locale.UK);
                            convertToSpeech.speak(convertTextToSpeech, TextToSpeech.QUEUE_FLUSH, null, null);

                        }
                    }
                });
            }
        });
        //get actionbar
        ActionBar actionBar = getSupportActionBar();
        //enabling back and up navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(word.getText().toString());
        actionBar.setLogo(R.drawable.icon);
    }

    //private ShareActionProvider mShareActionProvider;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menu resource
        getMenuInflater().inflate(R.menu.share_menu, menu);

        // locate menu item with shareactionprovider
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        // fetch and store shareactionprovider
        ShareActionProvider myShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, meaning.getText().toString());
        //shareIntent.putExtra(Intent.EXTRA_SUBJECT, "aubhec");

        myShareActionProvider.setShareIntent(shareIntent);
        // return true to display menu
        return true;
    }
    @Override
    protected void onPause() {
        if(convertToSpeech != null){
            convertToSpeech.stop();
            convertToSpeech.shutdown();
        }
        super.onPause();
    }
}
