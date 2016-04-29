package com.example.asmaa.topmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmaa.topmovies.R;
import com.example.asmaa.topmovies.model.MyApplication;
import com.example.asmaa.topmovies.model.Reviews;
import java.util.List;

/**
 * Created by asmaa on 4/24/2016.
 */
public class ReviewAdapter  extends BaseAdapter{

    /**
     * Created by asmaa on 4/22/2016.
     */
        int lenghtReview;
        Context context;
        List<Reviews> reviewsList;
        String[] subReview;
        private static LayoutInflater inflater=null;
        public ReviewAdapter(Context context1, List<Reviews> reviewsList) {

            this.context=context1;
            this.reviewsList=reviewsList;
            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return reviewsList.size();
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
            TextView tv1;
            Button BtnImg;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=new Holder();
            if(convertView == null) {
                holder = new Holder();
                convertView = inflater.inflate(R.layout.reviewrow, null);
                holder.tv = (TextView) convertView.findViewById(R.id.authorname);
                holder.tv1 = (TextView) convertView.findViewById(R.id.content);
                holder.BtnImg = (Button) convertView.findViewById(R.id.readmore);
            }else {
                holder = (Holder) convertView.getTag();
            }
            holder.tv.setText(reviewsList.get(position).getAuthor());
            lenghtReview=reviewsList.get(position).getContent().length();
            subReview=reviewsList.get(position).getContent().split("\n");
            holder.tv1.setText(subReview[0]+".......");
            holder.BtnImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                                    boolean internetConnection=((MyApplication) context.getApplicationContext()).isIntenetConnection();
                if(internetConnection) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewsList.get(position).getUrl()));
                    context.startActivity(i);
                } else {
                    Toast.makeText(context,"CONNECTION ERR", Toast.LENGTH_SHORT).show();
                }
                }
            });
                convertView.setTag(holder);

                return convertView;
        }

    }



