package org.csu.app;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.csu.app.domain.User;
import org.csu.app.util.HttpRequest;
import org.csu.app.util.StreamTool;
import org.csu.app.util.URLCollection;
import org.json.JSONObject;

import java.net.HttpURLConnection;


public class LoginActivity extends AppCompatActivity {

    private EditText usernameIn;
    private EditText passwordIn;
    private Button login;
    private String login_url;
    private String register_url;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameIn = findViewById(R.id.username);
        passwordIn = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login_url = URLCollection.LOGIN_URL;
        register_url = URLCollection.REGISTER_URL;

    }

    @SuppressLint("StaticFieldLeak")
    public void login(View view) {

        username = usernameIn.getText().toString();
        password = passwordIn.getText().toString();

        new AsyncTask<Void, Void, User>() {

            @Override
            protected User doInBackground(Void... voids) {
                return httpLogin(username, password);
            }

            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    AppSession session = (AppSession) getApplication();
                    session.setUser(user);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Account Not Exists, Register?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    asynTask2.execute();
                                }

                            })
                            .setNegativeButton("No", null);
                    builder.show();
                }
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    private final AsyncTask<Void, Void, String> asynTask2 = new AsyncTask<Void, Void, String>() {

        @Override
        protected String doInBackground(Void... voids) {
            return httpRegister(username, password);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("SUCCESS"))
                Toast.makeText(LoginActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(LoginActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
        }
    };

    private String httpRegister(String u, String p) {
        String result = "";
        try {
            String data = "username=" + u +"&&password=" + p;
            HttpURLConnection conn = HttpRequest.httpPost(register_url, data);
            String json = StreamTool.getJsonString(conn);
            JSONObject jo = new JSONObject(json);
            result = jo.getString("message");

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    private User httpLogin(String u, String p) {
        User user = new User();
        try {
            String data = "username=" + u +"&&password=" + p;
            HttpURLConnection conn = HttpRequest.httpPost(login_url, data);
            String json = StreamTool.getJsonString(conn);

            JSONObject jo = new JSONObject(json);
            user.setUsername(jo.getString("username"));
            user.setPassword(jo.getString("password"));
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
