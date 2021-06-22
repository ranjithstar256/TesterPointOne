package com.am.testerpointone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import org.apache.http.NameValuePair;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class tLogin extends AppCompatActivity {
    TextInputEditText edit_username,edit_password;
    Button btn_sign_in;

    AppCompatCheckBox check_keepsigned;

    PrefManager prefManager;

    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlogin);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

         edit_username = (TextInputEditText) findViewById(R.id.username);
        edit_password = (TextInputEditText) findViewById(R.id.password);
        btn_sign_in = (Button) findViewById(R.id.btn_sign_in);
        check_keepsigned = (AppCompatCheckBox) findViewById(R.id.check_keepsigned);
        prefManager = new PrefManager(this);

        String username1= prefManager.getusername();
        String password1= prefManager.getpassword();
        if((!(username1 == null) )){
            check_keepsigned.setChecked(true);
            edit_username.setText(username1);
            edit_password.setText(password1);
        }
         check_keepsigned.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {


                    prefManager.putusername(edit_username.getText().toString(),  edit_password.getText().toString());


                    Log.e("checked", edit_username.getText().toString() +"/////"+ edit_password.getText().toString());


                }

            }
        });
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI  || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE ) {
                // connected to wifi

                btn_sign_in.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        checkLogin(view);
                    }
                });

            }
        } else {
            Intent i = new Intent(tLogin.this, NoItemInternetImage.class);
            startActivity(i);
        }

    }

    private void checkLogin(View view) {
        final String username = edit_username.getText().toString();

        final String pass = edit_password.getText().toString();
        if (!isValidPassword(pass)) {
            //Set error message for password field
            edit_password.setError("Enter valid password");
        }
         String type = "login";
        if(isValidPassword(pass))
    {

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, pass);
        Log.i("123useracb",type+"\n"+username+"\n"+pass);
    }
}
    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }
        return false;
    }

    public void regr(View view) {
        startActivity(new Intent(tLogin.this,Registration.class));
    }
}