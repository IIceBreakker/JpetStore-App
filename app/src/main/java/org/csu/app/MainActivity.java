package org.csu.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.csu.app.domain.User;
import org.csu.app.util.HttpRequest;
import org.csu.app.util.StreamTool;
import org.csu.app.util.URLCollection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView u_view;
    private User user;
    private ListView cat_list_view;
    private final String category_url = URLCollection.CATEGORY_URL;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cat_list_view = findViewById(R.id.cat_list);
        u_view = findViewById(R.id.u);

        user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            u_view.setText(user.getUsername());
        }

        //异步任务进行Http请求
        asyncTask.execute();

    }

    //异步任务
    @SuppressLint("StaticFieldLeak")
    private final AsyncTask<Void, Void, String[]> asyncTask = new AsyncTask<Void, Void, String[]>() {

            @Override
            protected String[] doInBackground(Void... voids) {
                return getCategory();
            }

            @Override
            protected void onPostExecute(String[] catList) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, catList);
                cat_list_view.setAdapter(adapter);

                //为listView注册监听
                cat_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CharSequence text = ((TextView) view).getText();
                        String catId = text.toString().toUpperCase();
                        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                        intent.putExtra("catId", catId);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }

                });
            }
        };

    //实际的HTTP请求方法
    private String[] getCategory() {
        String[] catList = new String[0];
        try {
            HttpURLConnection conn = HttpRequest.httpGet(category_url);
            String json = StreamTool.getJsonString(conn);
            JSONArray jsonArray = new JSONArray(json);

            catList = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                catList[i] = jsonObject.getString("catname");
            }
            return catList;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return catList;
    }

}
