package org.csu.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.csu.app.domain.Item;
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

public class ItemActivity extends AppCompatActivity {

    private ListView item_list_view;
    private TextView u_view;
    private String items_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        item_list_view = findViewById(R.id.item_list);
        u_view = findViewById(R.id.u);

        String productId = getIntent().getStringExtra("productId");
        items_url = URLCollection.ITEMS_URL + productId;

        AppSession session = (AppSession) getApplication();
        User user = session.getUser();

        u_view.setText(user.getUsername());
        u_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        asyncTask.execute();

    }

    //异步任务
    @SuppressLint("StaticFieldLeak")
    private final AsyncTask<Void, Void, ArrayList<Item>> asyncTask = new AsyncTask<Void, Void, ArrayList<Item>>() {

        @Override
        protected ArrayList<Item> doInBackground(Void... voids) {
            return getItems();
        }

        @Override
        protected void onPostExecute(ArrayList<Item> itemList) {
            String[] is = new String[itemList.size()];
            for (int i = 0; i < itemList.size(); i++) {
                is[i] = itemList.get(i).toString();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(ItemActivity.this, android.R.layout.simple_list_item_1, is);
            item_list_view.setAdapter(adapter);

            //为item_listView注册单击监听
            item_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CharSequence text = ((TextView) view).getText();
                    String itemId = text.toString().split(",")[0];
                    Intent intent = new Intent(ItemActivity.this, PurchaseActivity.class);
                    intent.putExtra("itemId", itemId);
                    startActivity(intent);
                }

            });
        }
    };

    private ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        try {
            HttpURLConnection conn = HttpRequest.httpGet(items_url);
            String json = StreamTool.getJsonString(conn);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String itemId = jsonObject.getString("itemId");
                String productId = jsonObject.getString("productId");
                String listPrice = jsonObject.getString("listPrice");
                String unitCost = jsonObject.getString("unitcost");
                int supplier = jsonObject.getInt("supplier");
                String status = jsonObject.getString("status");
                items.add(new Item(itemId, productId, listPrice, unitCost, supplier, status));
            }
            return items;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return items;
    }
}
