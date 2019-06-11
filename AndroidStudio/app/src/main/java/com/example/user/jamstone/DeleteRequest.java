package com.example.user.jamstone;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2019-05-07.
 */

public class DeleteRequest extends StringRequest {

    final static private String URL = "http://wlsdn9721.cafe24.com/FoodDelete.php";
    private Map<String, String> parameters;

    public DeleteRequest(String name, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("name", name);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}


