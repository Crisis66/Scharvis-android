package com.example.michou.loginpage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

import java.io.BufferedReader;
import java.io.Console;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    }*/


   public String tryToConnect(View view) {
        String username, password;
        URL url;
        HttpURLConnection connection = null;
        EditText username_view = (EditText) findViewById(R.id.etUsername);
        EditText password_view = (EditText) findViewById(R.id.etPassword);

        String urlParameters = null;

        username = username_view.getText().toString();
        password = password_view.getText().toString();
        try {
            //Create connection
            //url = new URL("http://163.5.84.234:4567/login");
            //URL
            url = new URL("http://10.224.9.234:4567/login");
            //Parameters
            urlParameters = "username=" + URLEncoder.encode(username, "UTF-8") + "password=" + URLEncoder.encode(password, "UTF-8");
            //Open connection
            connection = (HttpURLConnection) url.openConnection();
            //Specify POST Method
            connection.setRequestMethod("POST");
            //Set Headers
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                        connection.setRequestProperty("Content-Length", "" +
                                Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = null;
                connection.setDoOutput(true);
                wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            //Get result
            Log.v("Success", response.toString());
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
