package com.example.user.jamstone;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2019-05-08.
 */

public class NameRequest extends StringRequest {

    final static private String URL = "http://wlsdn9721.cafe24.com/Foodupdate.php";
    private Map<String, String> parameters;

    public NameRequest(String nname, String Name, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("nname", nname);
        parameters.put("Name", Name);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}

