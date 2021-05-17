package org.feezor.androidapalooza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** Called when the user taps the Send button */
    public void sendMessage( View view) {
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.textBox);
        String inp = editText.getText().toString().trim();
        TextView tf = (TextView) findViewById(R.id.textView3);
        tf.setText(inp);
    }

}