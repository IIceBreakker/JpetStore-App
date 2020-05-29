package org.csu.app;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

public class CartActivity extends AppCompatActivity {

    private TextView u_view;
    private ListView order_list_view;
    private String cart_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        u_view = findViewById(R.id.u);
        order_list_view = findViewById(R.id.order_list);

        AppSession session = (AppSession) getApplication();
        User user = session.getUser();

        cart_url = URLCollection.CART_URL + user.getUsername();
        u_view.setText(user.getUsername());

        asyncTask.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private final AsyncTask<Void, Void, String[]> asyncTask = new AsyncTask<Void, Void, String[]>() {
        @Override
        protected String[] doInBackground(Void... voids) {
            return getOrders();
        }

        @Override
        protected void onPostExecute(String[] orderList) {
            if (orderList != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(CartActivity.this, android.R.layout.simple_list_item_1, orderList);
                order_list_view.setAdapter(adapter);
            }
        }
    };

    private String[] getOrders() {
        String[] orderList = null;
        try {
            HttpURLConnection conn = HttpRequest.httpGet(cart_url);
            String json = StreamTool.getJsonString(conn);
            JSONArray jsonArray = new JSONArray(json);

            orderList = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                orderList[i] = jsonObject.getString("itemId");
            }
            return orderList;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
