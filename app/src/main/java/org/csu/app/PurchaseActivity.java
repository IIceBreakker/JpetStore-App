package org.csu.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.csu.app.util.HttpRequest;
import org.csu.app.util.StreamTool;
import org.csu.app.util.URLCollection;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class PurchaseActivity extends AppCompatActivity {

    private String add_order_url;
    private TextView result_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        result_view = findViewById(R.id.result);

        String itemId = getIntent().getStringExtra("itemId");
        add_order_url = URLCollection.ADD_ORDER_URL;

        asyncTask.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private final AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
        @Override
        protected String doInBackground(Void... voids) {
            return getResult();
        }

        @Override
        protected void onPostExecute(String result) {
            result_view.setText(result);
        }
    };

    private String getResult() {
        String result = "";
        try {
            AppSession session = (AppSession) getApplication();
            Intent intent = getIntent();
            String userId = session.getUser().getUsername();
            String itemId = intent.getStringExtra("itemId");
            String data = "userId=" + userId +"&&itemId=" + itemId;

            HttpURLConnection conn = HttpRequest.httpPost(add_order_url, data);
            String json = StreamTool.getJsonString(conn);
            JSONObject jo = new JSONObject(json);
            result = jo.getString("message");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
