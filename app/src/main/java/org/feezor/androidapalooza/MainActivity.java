package org.feezor.androidapalooza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.feezor.androidapalooza.domain.Person;
import org.feezor.utils.FileUtils;
import org.feezor.utils.GsonUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }
    /** Called when the user taps the Send button */
    public void sendMessage( View view) {
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.textBox);
        String inp = editText.getText().toString().trim();
        TextView tf = (TextView) findViewById(R.id.textView3);
        tf.setText(inp);
        hideKeyboard(this);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initialize() {
        try {
            String json = FileUtils.getFileFromResourceAsString(this.getResources().openRawResource(R.raw.sample));
            System.out.println(json);
            Person p = GsonUtils.fromJson(json, Person.class);
            System.out.println(p.getName()+" - "+p.getAge());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}