package com.crowdsourced_burrito.burrito;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by dawnkd on 11/12/2017.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void submitCred(View view)
    {
        ServerHandler handler = ServerHandler.get();
        String user = ((EditText)findViewById(R.id.username)).getText().toString();;
        String password = ((EditText)findViewById(R.id.password)).getText().toString();


        if(user.isEmpty())
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Please enter a username",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if(password.isEmpty())
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a password", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        String response = handler.login(user, password);
        Toast toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG);
        toast.show();

        this.finish();
    }
}
