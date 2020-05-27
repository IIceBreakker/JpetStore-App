package org.csu.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private final String login_url = URLCollection.LOGIN_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameIn = findViewById(R.id.username);
        passwordIn = findViewById(R.id.password);
        login = findViewById(R.id.login);

    }

    @SuppressLint("StaticFieldLeak")
    public void login(View view) {

        final String username = usernameIn.getText().toString();
        final String password = passwordIn.getText().toString();

        new AsyncTask<Void, Void, User>() {

            @Override
            protected User doInBackground(Void... voids) {
                return httpLogin(username, password);
            }

            @Override
            protected void onPostExecute(User user) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        }.execute();
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
            e.printStackTrace();
        }
        return user;
    }
}
