package inftel.easyprojectandroid.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import inftel.easyprojectandroid.R;

public class NewTaskActivity extends AppCompatActivity {

    AutoCompleteTextView text;
    MultiAutoCompleteTextView text1;
    String[] languages={"Android ","java","IOS","SQL","JDBC","Web services"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text1=(MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView2);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, languages);

        text1.setAdapter(adapter);
        text1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

}
