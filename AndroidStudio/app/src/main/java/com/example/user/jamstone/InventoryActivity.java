package com.example.user.jamstone;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    private ListView listView;
    private FoodListAdapter adapter;
    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        final Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(InventoryActivity.this, RegisterActivity.class);
                InventoryActivity.this.startActivity(registerIntent);
                finish();
            }
        });


        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listView);
        foodList = new ArrayList<Food>();

        adapter = new FoodListAdapter(getApplicationContext(), foodList,this);
        listView.setAdapter(adapter);

        //new BackgroundTask().execute();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("FoodList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String Name, day;
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                Name = object.getString("Name");
                day = object.getString("day");
                Food food = new Food(Name, day);
                foodList.add(food);
                count++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://wlsdn9721.cafe24.com/FoodList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String Name, day;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    Name = object.getString("Name");
                    day = object.getString("day");
                    Food food = new Food(Name, day);
                    foodList.add(food);
                    count++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
