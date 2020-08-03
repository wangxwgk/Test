package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    public String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        Pair[] data = new Pair[5];
        data[0]= new Pair("https://www.ckmov.vip/api.php?url=","信号1（无广告）");
        data[1]= new Pair("https://jx.elwtc.com/vip/?url=","信号2（无广告）");
        data[2] = new Pair("https://api.8bjx.cn/?url=","信号3（无广告）");
        data[4]= new Pair("https://jqaaa.com/jx.php?url=","信号5");
        data[3]= new Pair("http://jx.drgxj.com/?url=","信号4");
        ArrayAdapter<Pair> adapter = new ArrayAdapter<Pair>(this,R.layout.support_simple_spinner_dropdown_item,data);
        spinner.setAdapter(adapter);

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair pair = (Pair)spinner.getSelectedItem();
                str = pair.getKey();
                EditText editText1 = (EditText)findViewById(R.id.editText);
                str += editText1.getText().toString();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", str);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
