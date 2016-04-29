package com.example.asmaa.topmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmaa.topmovies.R;
import com.example.asmaa.topmovies.model.MyApplication;
import com.example.asmaa.topmovies.model.Trailers;
import java.util.List;

/**
 * Created by asmaa on 4/22/2016.
 */


    /**
     * Created by asmaa on 4/22/2016.
     */
    public class TrailerAdapter extends BaseAdapter {
        Context context;
        List<Trailers> trailerList;
        public TrailerAdapter(Context contect1, List<Trailers> trailerList) {
            this.context=contect1;
            this.trailerList=trailerList;
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return trailerList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        public class Holder
        {
            TextView tv;
            ImageButton img;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder;
            int p=position+1;
            if(convertView == null){
                holder=new Holder();
                convertView = LayoutInflater.from(context).inflate(R.layout.trailerrow,parent, false);
                holder.tv=(TextView) convertView.findViewById(R.id.trailerNo);
                holder.img=(ImageButton) convertView.findViewById(R.id.youTubBtn);

                convertView.setTag(holder);
            }
            else{
              holder = (Holder) convertView.getTag();
            }
            ((MyApplication) context.getApplicationContext()).setUrlTrailer("https://www.youtube.com/watch?v=" + trailerList.get(0).getMovieKey());

            holder.tv.setText("Trailer Play "+p);
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean internetConnection=((MyApplication) context.getApplicationContext()).isIntenetConnection();
                    if(internetConnection) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailerList.get(position).getMovieKey()));
                        context.startActivity(i);
                    } else {
                        Toast.makeText(context,"CONNECTION ERR", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            return convertView;
        }

    }



