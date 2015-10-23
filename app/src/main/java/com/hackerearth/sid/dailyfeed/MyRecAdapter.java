package com.hackerearth.sid.dailyfeed;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;


class MyRecAdapter extends RecyclerView.Adapter<MyRecAdapter.ViewHolder> {
    public static final String DATA_ARRAY ="datarray" ;
    ArrayList<DataSet> mDataset= new ArrayList<>();
    ImageLoader mImageLoader;
    VolleySingleton volley;
    Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageView mImageView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.rvtv);
            mImageView=(ImageView)v.findViewById(R.id.rviv);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,ReadActivity.class);
            intent.putExtra(DATA_ARRAY, mDataset.get(getAdapterPosition()));
            context.startActivity(intent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRecAdapter(Context context,ArrayList<DataSet> myDataset){
        mDataset = myDataset;
        notifyItemRangeChanged(0, mDataset.size());
        this.context=context;
        volley = VolleySingleton.getsInstance();
        mImageLoader = volley.getImageLoader();


    }

    public MyRecAdapter(Context context,DataSet dataSet){
        mDataset.add(dataSet);
        this.context=context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyRecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DataSet current = mDataset.get(position);


        holder.mTextView.setText(current.data);
        String urlThumbnail = current.thumbnail;
        loadImages(urlThumbnail, holder);
    }

    private void loadImages(String urlThumbnail,final ViewHolder holder) {
        if (urlThumbnail!=null) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.mImageView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}