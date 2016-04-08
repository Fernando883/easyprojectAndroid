package inftel.easyprojectandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import inftel.easyprojectandroid.R;

public class NewTaskActivity extends Activity implements AdapterView.OnItemSelectedListener {

    MultiAutoCompleteTextView text1;
    String[] languages={"Android ","java","IOS","SQL","JDBC","Web services"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text1=(MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView2);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, languages);

        text1.setAdapter(adapter);
        text1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        Spinner spinner = (Spinner) findViewById(R.id.status_spinner);
        ArrayAdapter adapterSpinner = ArrayAdapter.createFromResource(this,R.array.status_arrays,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterSpinner);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
