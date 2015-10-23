package com.hackerearth.sid.dailyfeed;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class ReadActivity extends AppCompatActivity implements View.OnClickListener{
    ImageLoader mImageLoader;
    VolleySingleton volley;

    TextView textView;
    ImageView imageView;
    TextView textViewContent;
    Button bookmark;
    Button link;
    Button share;
    DataSet dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Toolbar toolbar= (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        volley = VolleySingleton.getsInstance();
        mImageLoader = volley.getImageLoader();
        bookmark= (Button) findViewById(R.id.bookmark);
        link= (Button) findViewById(R.id.link);
        share= (Button) findViewById(R.id.share);
        textView= (TextView) findViewById(R.id.textViewTitle);
         imageView= (ImageView) findViewById(R.id.imageViewContent);
        textViewContent= (TextView) findViewById(R.id.textViewContent);
        Intent i=getIntent();
         dataSet= (DataSet) i.getExtras().getSerializable(MyRecAdapter.DATA_ARRAY);
        textView.setText(dataSet.data + " under " + dataSet.category + " by " + dataSet.source);
        String Thumburl=dataSet.thumbnail;
        loadImages(Thumburl);
        textViewContent.setText(dataSet.content);

        link.setOnClickListener(this);

    }


    private void loadImages(String urlThumbnail) {
        if (urlThumbnail!=null) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                   imageView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read, menu);
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.link){
            Uri uri=Uri.parse(dataSet.link);
            Intent i=new Intent(Intent.ACTION_VIEW,uri);
            startActivity(i);
        }
    }
}
