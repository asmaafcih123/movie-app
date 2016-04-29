package com.example.asmaa.topmovies.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.asmaa.topmovies.R;
import com.example.asmaa.topmovies.demo.MovieTypeDB;
import com.example.asmaa.topmovies.demo.ReviewsDB;
import com.example.asmaa.topmovies.demo.ServiceHandler;
import com.example.asmaa.topmovies.adapter.TrailerAdapter;
import com.example.asmaa.topmovies.adapter.ReviewAdapter;
import com.example.asmaa.topmovies.demo.TrailerDB;
import com.example.asmaa.topmovies.model.Movie;
import com.example.asmaa.topmovies.model.MyApplication;
import com.example.asmaa.topmovies.model.Reviews;
import com.example.asmaa.topmovies.model.Trailers;
import com.example.asmaa.topmovies.demo.movieDB;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailFragment extends Fragment {

    float divVote;
    private static final String TAG_RESULTSS = "results";
    private static final String TAG_ID = "id";
    private static final String TAG_KEY = "key";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_OCONTENT = "content";
    private static final String TAG_URL = "url";
    // contacts JSONArray
    JSONArray results = null;
    private Intent shareIntent;

    public static final String ARG_ITEM_ID = "item_id";

    movieDB movieDB;
    TrailerDB TrailerDB;
    ReviewsDB reviewsDB;
    String ReviewUrl;
    boolean internetConnection;
    String TrailerUrl;
    List<Reviews> reviewsList;
    List<Trailers> trailersList;
    private Movie.MovieItem mItem;
    private Movie.MovieItem movieObj;
    MovieTypeDB MovieTypeDB;
    ListView lvReview;
    ListView lvTrailer;
    Activity activity;
    TextView tv1, tv3, tv4,tv5;
    ImageView img,img1,img2,img3;
    String  []subtitle;
    String  []subOverView;
    int count;
     public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            int str= Integer.parseInt(getArguments().getString(ARG_ITEM_ID));
            Log.d("movie IDkkk XXXX", String.valueOf(str));
            activity = this.getActivity();
            movieDB=new movieDB(activity);
           TrailerDB=new TrailerDB(activity);
            reviewsDB=new ReviewsDB(activity);
           MovieTypeDB=new MovieTypeDB(activity);
            mItem=movieDB.getMovieobj(str);
          //  CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
//            if (appBarLayout != null) {
//               appBarLayout.setTitle(subtitle[0]);
//                //   Picasso.with(activity).load("http://image.tmdb.org/t/p/w500/" +mItem.getPoster_path()).into(img1);}
//            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        img2=(ImageView)rootView.findViewById(R.id.fab);
        img3=(ImageView)rootView.findViewById(R.id.shareIcon);
        img1=(ImageView)rootView.findViewById(R.id.image);
        subtitle=mItem.getOriginal_title().split(":");
            img3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    /// button click event
                    shareMovieTitle();
                }
            });

            img2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    // button click event
        count= MovieTypeDB.CheckFavorite(mItem.getId());
                    if(count>0) {
                        MovieTypeDB.delete_Type(mItem.getId());
                        img2.setBackgroundResource(R.drawable.ssars_emphty);
                        Snackbar.make(v,mItem.getOriginal_title()+" Deleted from Favorite", Snackbar.LENGTH_LONG)
                               .setAction("Action", null).show();

                    }else{
                        movieObj = new Movie.MovieItem(mItem.getPoster_path(), mItem.getOverview(),mItem.getRelease_date(),mItem.getId()
                                ,mItem.getOriginal_title(),mItem.getVote_average());
                        MovieTypeDB.Add_MovieType(mItem.getId(),2);
                        img2.setBackgroundResource(R.drawable.staricon);
                        Snackbar.make(v,mItem.getOriginal_title()+" Added To Favorite", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }     }
            });
        Picasso.with(activity).load("http://image.tmdb.org/t/p/w500/" +mItem.getPoster_path()).into(img1);
        RatingBar rr=(RatingBar) rootView.findViewById(R.id.MyRating);
        Drawable drawable = rr.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#0073e5"), PorterDuff.Mode.SRC_ATOP);
        lvReview=(ListView) rootView.findViewById(R.id.review_list);
        lvTrailer=(ListView) rootView.findViewById(R.id.triler_list);
        internetConnection=((MyApplication) activity.getApplicationContext()).isIntenetConnection();
        Log.d("internetConnection: ", "> " +internetConnection);
        if(internetConnection){
    ReviewUrl="http://api.themoviedb.org/3/movie/"+mItem.getId()+"/reviews?api_key=912664a49b2f49e8cddc86e5d523ea5e";
    TrailerUrl="http://api.themoviedb.org/3/movie/"+mItem.getId()+"/videos?api_key=912664a49b2f49e8cddc86e5d523ea5e";
    new asyncReview().execute();
    new asyncTrailers().execute();
}else{
    reviewsList=reviewsDB.getAllReview(mItem.getId());
    if(  reviewsList.isEmpty()){
        Toast.makeText(activity," CONNECTION ERR", Toast.LENGTH_SHORT).show();

    }else{
        lvReview.setAdapter(new ReviewAdapter(activity, reviewsList));
        Log.d("Response size review: ", "> " + reviewsList.size());
        setListViewHeightBasedOnItems(lvReview);

    }
    trailersList=TrailerDB.getAllTrailer(mItem.getId());
    if(  trailersList.isEmpty()){
        Toast.makeText(activity," CONNECTION ERR", Toast.LENGTH_SHORT).show();

    }else {
        lvTrailer.setAdapter(new TrailerAdapter(activity, trailersList));
        Log.d("Response trailer: ", "> " + trailersList.size());
        setListViewHeightBasedOnItems(lvTrailer);
    }
}

        tv1=(TextView) rootView.findViewById(R.id.date);
        tv3=(TextView) rootView.findViewById(R.id.vote);
        subOverView=mItem.getOverview().split(",");
        tv5=(TextView) rootView.findViewById(R.id.suboverview);
        tv4=(TextView) rootView.findViewById(R.id.orignalTitle);
        divVote= (float) ((mItem.getVote_average())/2);
        if (mItem != null) {
            tv1.setText( mItem.getRelease_date());
            tv4.setText(mItem.getOriginal_title());
            tv3.setText("Vote\n"+String.valueOf(mItem.getVote_average()));
            rr.setRating(divVote);
            tv5.setText(mItem.getOverview());

        }

        return rootView;
    }
    private class asyncReview extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(ReviewUrl, ServiceHandler.GET);
           reviewsList=new ArrayList<Reviews>();
            Log.d("Response Review: ", "> " + jsonStr);

            if (jsonStr != null ) {
                try {
                  reviewsDB.delete_Review(mItem.getId());
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    results = jsonObj.getJSONArray(TAG_RESULTSS);
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);
                        String id = c.getString(TAG_ID);
                        String author = c.getString(TAG_AUTHOR);
                        String content = c.getString(TAG_OCONTENT);
                        String url = c.getString(TAG_URL);
                        Reviews reviewObj = new Reviews(author, url, content, id,mItem.getId());
                         reviewsDB.Add_Review(reviewObj);
                        reviewsList.add(reviewObj);
                        Log.e("ServiceHandler1", String.valueOf(reviewsList.size()));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(reviewsList.size()==0)
            {
                lvReview.setVisibility(ListView.INVISIBLE);
            }
            else{
                lvReview.setAdapter(new ReviewAdapter(activity, reviewsList));
                Log.d("Response size review: ", "> " + reviewsList.size());
                setListViewHeightBasedOnItems(lvReview);
               // reviewsList.clear();
            }
        }

    }

    private class asyncTrailers extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
             ServiceHandler sh = new ServiceHandler();
            trailersList=new ArrayList<Trailers>();
            String jsonStr = sh.makeServiceCall(TrailerUrl, ServiceHandler.GET);

            Log.d("Response Trailer: ", "> " + jsonStr);

            if (jsonStr != null ) {
                try {
                    TrailerDB.delete_Trailer(mItem.getId());
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    results = jsonObj.getJSONArray(TAG_RESULTSS);
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);
                        String id = c.getString(TAG_ID);
                        String key= c.getString(TAG_KEY);
                        Trailers trailerObj = new Trailers( id, key,mItem.getId());
                        TrailerDB.Add_Trailer(trailerObj);
                        trailersList.add(trailerObj);
                        Log.e("ServiceHandler1", String.valueOf(trailersList.size()));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(trailersList.size()==0)
            {
                lvTrailer.setVisibility(ListView.INVISIBLE);
            }
            else{
                lvTrailer.setAdapter(new TrailerAdapter(activity, trailersList));
                Log.d("Response trailer: ", "> " + trailersList.size());
                setListViewHeightBasedOnItems(lvTrailer);
               // trailersList.clear();
            }

        }

    }
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
    private void shareMovieTitle() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Share Movie");
        share.putExtra(Intent.EXTRA_TEXT, "Movie Title : "+mItem.getOriginal_title()+"\n Overview : "+mItem.getOverview()+"\n trailer url \n"+
                ((MyApplication) activity.getApplicationContext()).getUrlTrailer());
        Log.d("share: ", "> " + mItem.getOriginal_title());

        startActivity(Intent.createChooser(share, "Share Movie "));
    }
}


