package org.csu.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.csu.app.domain.Cart;
import org.csu.app.domain.User;
import org.csu.app.util.HttpRequest;
import org.csu.app.util.StreamTool;
import org.csu.app.util.URLCollection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView u_view;
    private ListView cat_list_view;
    private String category_url;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cat_list_view = findViewById(R.id.cat_list);
        u_view = findViewById(R.id.u);

        category_url = URLCollection.CATEGORY_URL;

        final AppSession session = (AppSession) getApplication();
        User user = session.getUser();
        u_view.setText(user.getUsername());
        u_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

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
                int[] images = new int[] { R.drawable.birds_icon, R.drawable.cats_icon,
                                R.drawable.dogs_icon, R.drawable.fish_icon, R.drawable.reptiles_icon };
                List<Map<String, Object>> listItems = new ArrayList<>();
                for (int i = 0; i < images.length; i ++) {
                    Map<String, Object> listItem = new HashMap<>();
                    listItem.put("header", images[i]);
                    listItem.put("catId", catList[i]);
                    listItems.add(listItem);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, listItems, R.layout.simple_item,
                        new String[] {"catId", "header"}, new int[] {R.id.name, R.id.header});
                cat_list_view.setAdapter(simpleAdapter);
                cat_list_view.setOnItemClickListener(listViewListener);
            }
        };

    //listView的监听器
    private final AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CharSequence text = ((TextView) view.findViewById(R.id.name)).getText();
            String catId = text.toString().toUpperCase();
            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
            intent.putExtra("catId", catId);
            startActivity(intent);
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
