package org.csu.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.JsonArray;
import org.csu.app.domain.Product;
import org.csu.app.domain.User;
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
import java.util.*;

public class ProductActivity extends AppCompatActivity {

    private ListView product_list_view;
    private TextView u_view;
    private String products_url;
    private String catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        product_list_view = findViewById(R.id.product_list_view);
        u_view = findViewById(R.id.u);

        catId = getIntent().getStringExtra("catId");
        products_url = URLCollection.PRODUCTS_URL + catId;

        AppSession session = (AppSession) getApplication();
        User user = session.getUser();

        u_view.setText(user.getUsername());
        u_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        asyncTask.execute();

    }

    @SuppressLint("StaticFieldLeak")
    private final AsyncTask<Void, Void, ArrayList<Product>> asyncTask = new AsyncTask<Void, Void, ArrayList<Product>>() {

            @Override
            protected ArrayList<Product> doInBackground(Void... voids) {
                return getProducts();
            }

            @SuppressLint("ResourceType")
            @Override
            protected void onPostExecute(ArrayList<Product> products) {
                int[] images = new int[products.size()];
                String[] ps = new String[products.size()];
                List<Map<String, Object>> listItems = new ArrayList<>();

                switch (catId) {
                    case "BIRDS":
                        Arrays.fill(images, R.drawable.bird1);
                        break;
                    case "FISH":
                        Arrays.fill(images, R.drawable.fish1);
                        break;
                    case "CATS":
                        Arrays.fill(images, R.drawable.cat1);
                        break;
                    case "DOGS":
                        Arrays.fill(images, R.drawable.dog1);
                        break;
                    case "REPTILES":
                        Arrays.fill(images, R.drawable.lizard1);
                        break;
                }

                for (int i = 0; i < products.size(); i++) {
                    Map<String, Object> listItem = new HashMap<>();
                    ps[i] = products.get(i).getProductId();

                    listItem.put("header", images[i]);
                    listItem.put("productId", ps[i]);
                    listItems.add(listItem);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(ProductActivity.this, listItems, R.layout.simple_item,
                        new String[] {"productId", "header"}, new int[] {R.id.name, R.id.header});
                product_list_view.setAdapter(simpleAdapter);

                //为product_listView注册单击监听
                product_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CharSequence text = ((TextView) view.findViewById(R.id.name)).getText();
                        String productId = text.toString().toUpperCase();
                        Intent intent = new Intent(ProductActivity.this, ItemActivity.class);
                        intent.putExtra("productId", productId);
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
