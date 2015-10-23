package com.hackerearth.sid.dailyfeed;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;
    Spinner spinner;

    RequestQueue mRequestQueue;
    String url="https://dailyhunt.0x10.info/api/dailyhunt?type=json&query=list_news";

     ArrayList<DataSet> list=new ArrayList<>();
    ArrayList<DataSet> listF=new ArrayList<>();


    public void sort(String category){
        if(category.equals("All")){
            mAdapter = new MyRecAdapter(MainActivity.this, list);
            mRecyclerView.setAdapter(mAdapter);

        }
        else {
            listF.clear();
            for (DataSet a : list) {
                if (a.getCategory().equals(category)) {
                    listF.add(a);
                    mAdapter = new MyRecAdapter(MainActivity.this, listF);
                    mRecyclerView.setAdapter(mAdapter);

                }
            }
        }

    }


public void parse(){


        mRequestQueue = VolleySingleton.getsInstance().getmRequestQueue();


    try {
        Toast.makeText(MainActivity.this, "entered try json", Toast.LENGTH_LONG).show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this, "entered onResponse", Toast.LENGTH_LONG).show();

                JSONArray articles = null;
                try {
                    articles = response.getJSONArray("articles");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < articles.length(); i++) {


                    JSONObject obj = null;
                    try {
                        obj = articles.getJSONObject(i);

                        String title = obj.getString("title");
                        String image = obj.getString("image");
                        String categ=obj.getString("category");
                        String link=obj.getString("url");
                        String content=obj.getString("content");
                        String source=obj.getString("source");

                        DataSet object = new DataSet(title, image,categ,link,content,source);

                        list.add(object);

                        mAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "exception", Toast.LENGTH_LONG).show();

                    }


                }

                    mAdapter=new MyRecAdapter(MainActivity.this,list);
                    mRecyclerView.setAdapter(mAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }catch (Exception e){
        Toast.makeText(this,"outer exception",Toast.LENGTH_LONG).show();

    }
}




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        spinner= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> sAdapter=ArrayAdapter.createFromResource(this,R.array.categories,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        sort("All");
                        break;
                    case 1:
                        sort("World");
                        break;
                    case 2:
                        sort("Education");
                        break;
                    case 3:
                        sort("Science");
                        break;
                    case 4:
                        sort("Technology");
                        break;
                    case 5:
                        sort("Food");
                        break;
                    case 6:
                        sort("Sports");
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
       // list=getData();
       // mAdapter.notifyDataSetChanged();
        // specify an adapter (see also next example)
        mAdapter = new MyRecAdapter(this,new DataSet());
        mRecyclerView.setAdapter(mAdapter);

        parse();
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
