package org.csu.app;

import android.annotation.SuppressLint;
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
import com.google.gson.JsonArray;
import org.csu.app.domain.Product;
import org.csu.app.util.HttpRequest;
import org.csu.app.util.StreamTool;
import org.csu.app.util.URLCollection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    private ListView product_list_view;
    private String products_url = URLCollection.PRODUCTS_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        product_list_view = findViewById(R.id.product_list_view);

        String catId = getIntent().getStringExtra("catId");
        products_url = products_url + catId;

        asyncTask.execute();

    }

    @SuppressLint("StaticFieldLeak")
    private final AsyncTask<Void, Void, ArrayList<Product>> asyncTask = new AsyncTask<Void, Void, ArrayList<Product>>() {

            @Override
            protected ArrayList<Product> doInBackground(Void... voids) {
                return getProducts();
            }

            @Override
            protected void onPostExecute(ArrayList<Product> products) {
                String[] ps = new String[products.size()];
                for (int i = 0; i < products.size(); i++) {
                    ps[i] = products.get(i).getProductId();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ProductActivity.this, android.R.layout.simple_list_item_1, ps);
                product_list_view.setAdapter(adapter);

                //为product_listView注册单击监听
                product_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CharSequence text = ((TextView) view).getText();
                        String productId = text.toString().toUpperCase();
                        Intent intent = new Intent(ProductActivity.this, ItemActivity.class);
                        intent.putExtra("productId", productId);
//                        intent.putExtra("user", user);
                        startActivity(intent);
                    }

                });
            }
    };

    private ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            HttpURLConnection conn = HttpRequest.httpGet(products_url);
            String json = StreamTool.getJsonString(conn);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String productId = jsonObject.getString("productId");
                String catId = jsonObject.getString("category");
                String productName = jsonObject.getString("name");
                products.add(new Product(productId, catId, productName));
            }

            return products;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return products;
    }
}
