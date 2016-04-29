package com.example.asmaa.topmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout;

import com.example.asmaa.topmovies.R;
import com.example.asmaa.topmovies.activity.MovieDetailActivity;
import com.example.asmaa.topmovies.activity.MovieDetailFragment;
import com.example.asmaa.topmovies.demo.MovieTypeDB;
import com.example.asmaa.topmovies.demo.movieDB;
import com.example.asmaa.topmovies.model.Movie;
import com.example.asmaa.topmovies.model.MyApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by asmaa on 4/27/2016.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>  {

       List<Movie.MovieItem> mValues;
        Context mcontext;
        movieDB movieDB;
        MovieTypeDB MovieTypeDB;
        boolean mTwoPane;
    public class MyViewHolder extends RecyclerView.ViewHolder {
            public  View mView;
            public TextView title;
            public ImageView img;
            public ImageButton imgStar;
            public Movie.MovieItem mItem;

            public MyViewHolder(View view) {
                super(view);
                mView = view;
                img = (ImageView) view.findViewById(R.id.imageView1);
                imgStar = (ImageButton) view.findViewById(R.id.imgstar);
            }
        }


        public MoviesAdapter(List<Movie.MovieItem> moviesList,Context mcontext) {
            mValues = moviesList;
            this.mcontext= mcontext;
            movieDB =new movieDB(mcontext);
            MovieTypeDB=new MovieTypeDB(mcontext);
            mTwoPane=((MyApplication) mcontext.getApplicationContext()).isTwoPane();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_content, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            int count = MovieTypeDB.CheckFavorite(holder.mItem.getId());
            //0 IS NOT FAVORITE ,1 IS FAVORITE
            if (count > 0) {
                holder.imgStar.setBackgroundResource(R.drawable.staricon);
            } else {
                holder.imgStar.setBackgroundResource(R.drawable.ssars_emphty);
            }
            Picasso.with(mcontext).load("http://image.tmdb.org/t/p/w185/" + mValues.get(position).getPoster_path()).into(holder.img);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }




}
