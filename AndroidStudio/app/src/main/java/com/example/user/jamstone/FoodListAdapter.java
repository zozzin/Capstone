package com.example.user.jamstone;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.scheme.HostNameResolver;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by user on 2019-04-29.
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
        View v = View.inflate(context, R.layout.food, null);
        final TextView Name = (TextView) v.findViewById(R.id.Name);
        final TextView day = (TextView) v.findViewById(R.id.day);

        Name.setText(foodList.get(i).getName());
        day.setText(foodList.get(i).getday());
        v.setTag(foodList.get(i).getName());

        final Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        final Button updateButton = (Button) v.findViewById(R.id.updateButton);


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


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);

               final EditText et = new EditText(parentActivity);

                builder.setTitle("식재료명을 입력해주세요.")        // 제목 설정
                        .setCancelable(false)
                        .setView(et)   // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String nname = et.getText().toString();

                                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                                            //결과에대한 값을 받아들일수있는 Listener를 만들어줌
                                            @Override
                                            public void onResponse(String response) {
                                            }
                                        };
                                        NameRequest nameRequest = new NameRequest (nname, Name.getText().toString(), responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(parentActivity);
                                        queue.add(nameRequest);

                                        Intent intent = new Intent(context, MainActivity.class);
                                        context.startActivity(intent);
                                        parentActivity.finish();
                                    }
                                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return v;
    }
}
