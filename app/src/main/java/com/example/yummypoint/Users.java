package com.example.yummypoint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.yummypoint.Adapters.MyListAdapter;
import com.example.yummypoint.Model.APIUser;
import com.example.yummypoint.Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Users extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView = findViewById(R.id.user_recycler);

        getUsers();
    }

    public void getUsers(){
        String url = "https://jsonplaceholder.typicode.com/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            List<APIUser> usersList = new ArrayList<>();

            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        APIUser apiUser = new Gson().fromJson(jsonObject.toString(), APIUser.class);
                        //usersList.add(new APIUser(jsonObject.getString("name"), jsonObject.getString("username"), jsonObject.getString("email")));
                        usersList.add(apiUser);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                MyListAdapter myListAdapter = new MyListAdapter(usersList);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Users.this));
                recyclerView.setAdapter(myListAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("User API Error", error.toString());
            }
        });

      MySingleTon.getInstance(this).getRequestQueue().add(jsonArrayRequest);
    }
}
