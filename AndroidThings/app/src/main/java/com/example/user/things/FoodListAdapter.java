package com.example.user.things;

/**
 * Created by user on 2019-05-14.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by user on 2019-05-14.
 */

public class FoodListAdapter extends BaseAdapter {

    private Context context;
    private List<Food> foodList;
    private Activity parentActivity;

    public FoodListAdapter(Context context, List<Food> foodList, Activity parentActivity) {
        this.context = context;
        this.foodList = foodList;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.activity_food, null);
        final TextView Name = (TextView) v.findViewById(R.id.Name);
        final TextView day = (TextView) v.findViewById(R.id.day);

        Name.setText(foodList.get(i).getName());
        day.setText(foodList.get(i).getday());

        v.setTag(foodList.get(i).getName());
        final Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                foodList.remove(i);
                                notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteRequest deleteRequest = new DeleteRequest(Name.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActivity);
                queue.add(deleteRequest);
            }
        });
        return v;

    }
}
