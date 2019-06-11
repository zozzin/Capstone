package com.example.user.things;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends Activity {

    private ListView listView;
    private FoodListAdapter adapter;
    private List<Food> foodList;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        intent = getIntent();

        listView = (ListView) findViewById(R.id.listView);
        foodList = new ArrayList<Food>();

        adapter = new FoodListAdapter(getApplicationContext(), foodList, this);
        listView.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("FoodList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String day, Name;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                Name = object.getString("Name");
                day = object.getString("day");
                Food food = new Food(Name, day);
                foodList.add(food);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}