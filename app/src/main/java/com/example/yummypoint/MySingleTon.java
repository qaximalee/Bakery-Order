package com.example.yummypoint;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleTon {

    private static MySingleTon singleTon;
    private RequestQueue requestQueue;
    private static Context context;

    private MySingleTon(Context context)
    {
        MySingleTon.context =context;
        this.requestQueue=getRequestQueue();


    }


    public RequestQueue getRequestQueue() {
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(context.getApplicationContext());


        }
        return requestQueue;

    }
    public static synchronized MySingleTon getInstance(Context context)

    {
        if(singleTon==null)
        {
            singleTon=new MySingleTon(context);

        }
        return singleTon;

        }

        public <T> void addTooRequestQueue(Request<T> request)
        {
            getRequestQueue().add(request);

        }
}


