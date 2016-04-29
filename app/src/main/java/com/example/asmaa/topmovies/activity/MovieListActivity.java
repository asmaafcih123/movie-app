package com.example.asmaa.topmovies.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.asmaa.topmovies.R;
import com.example.asmaa.topmovies.adapter.MoviesAdapter;
import com.example.asmaa.topmovies.demo.MovieTypeDB;
import com.example.asmaa.topmovies.demo.ServiceHandler;
import com.example.asmaa.topmovies.model.Movie;
import com.example.asmaa.topmovies.model.MyApplication;
import com.example.asmaa.topmovies.demo.movieDB;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private MoviesAdapter mAdapter;
    movieDB movieDB;
    MovieTypeDB MovieTypeDB;
    Movie movieObj;
    private ProgressDialog pDialog;
    String url = null;
    int MovieType;
    //0,populare;1,rate,2,favorite
    private static final String TAG_RESULTSS = "results";
    private static final String TAG_ID = "id";
    private static final String TAG_POSTER_PATH = "poster_path";
    private static final String TAG_OVERVIEW = "overview";
    private static final String TAG_ORIGINAL_TITLE = "original_title";
    private static final String TAG_VOTE_AVERAGE = "vote_average";
    private static final String TAG_RELEASE_DATE = "release_date";
    JSONArray results = null;
    boolean internet_connection;
    private boolean mTwoPane;
    List<Integer> movieId = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView1 = (RecyclerView) findViewById(R.id.movie_list);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        movieObj.ITEMS.clear();
        movieDB =new movieDB(MovieListActivity.this);
        MovieTypeDB=new MovieTypeDB(MovieListActivity.this);
        MovieType= ((MyApplication) MovieListActivity.this.getApplication()).getType();
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }
        if (isNetworkAvailable(this)) {
            if(MovieType==0) {
                url = "https://api.themoviedb.org/3/movie/popular?api_key=f422e13255a0c5243a1627cb14581228";
                // Calling async task to get json
                new GetContacts().execute();
            }else if(MovieType==1){
                url = "https://api.themoviedb.org/3/movie/top_rated?api_key=f422e13255a0c5243a1627cb14581228";
                // Calling async task to get json
                new GetContacts().execute();
            }else{
                    movieObj.ITEMS = movieDB.getAllmovies(MovieType);
                recycleviewFn();
                }
            internet_connection=true;
            ((MyApplication) MovieListActivity.this.getApplication()).setIntenetConnection(true);
            Log.d("internetConnection1: ", "> " +internet_connection);
            Log.d("movieType: ", "> " +MovieType);
            Toast.makeText(MovieListActivity.this," CONNECTED", Toast.LENGTH_SHORT).show();


        } else {
            Log.d("movieType: ", "> " +MovieType);
            internet_connection=false;
            Log.d("internetConnection12: ", "> " +internet_connection);
            ((MyApplication) MovieListActivity.this.getApplication()).setIntenetConnection(false);
            movieObj.ITEMS = movieDB.getAllmovies(MovieType);
            Toast.makeText(MovieListActivity.this," Not CONNECTED", Toast.LENGTH_SHORT).show();
            recycleviewFn();
        }

        recyclerView1.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView1, new ClickListener()
        {
            @Override
            public void onClick(View view, int position) {
                Movie.MovieItem movie = movieObj.ITEMS.get(position);
                Toast.makeText(getApplicationContext(), movie.getOriginal_title() + " is selected!", Toast.LENGTH_SHORT).show();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                //    Toast.makeText(MovieListActivity.this,"click **"+ String.valueOf(movie.getId()), Toast.LENGTH_SHORT).show();
                    ((MyApplication)MovieListActivity.this.getApplication()).setMovieId(movie.getId());
                    ((MyApplication) MovieListActivity.this.getApplication()).setMovieLis(movieObj.ITEMS);
                    arguments.putString(MovieDetailFragment.ARG_ITEM_ID, String.valueOf(movie.getId()));
                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);
                    //movieObj.ITEMS.clear();
                    getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_container, fragment).commit();

                } else {
                    Context context = view.getContext();
                    ((MyApplication)MovieListActivity.this.getApplication()).setMovieId(movie.getId());
                  //  Toast.makeText(MovieListActivity.this,"click from 1  _^^"+ String.valueOf(movie.getId()), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    //movieObj.ITEMS.clear();
                    intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, movie.getId());
                    context.startActivity(intent);

                }
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }
    void recycleviewFn(){
        mAdapter = new MoviesAdapter(movieObj.ITEMS,MovieListActivity.this);

        recyclerView1.setHasFixedSize(false);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(staggeredGridLayoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(mAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        movieObj.ITEMS.clear();
        if (id == R.id.popular) {
            MovieType=0;
            ((MyApplication) MovieListActivity.this.getApplication()).setType(MovieType);

            if(internet_connection) {
                url = "https://api.themoviedb.org/3/movie/popular?api_key=f422e13255a0c5243a1627cb14581228";
                new GetContacts().execute();
                ((MyApplication) MovieListActivity.this.getApplication()).setIntenetConnection(true);

            }
            else {
              //  new GetContacts().execute();
                   movieObj.ITEMS = movieDB.getAllmovies(MovieType);
                   if(movieObj.ITEMS.size()==0){
                    Toast.makeText(MovieListActivity.this,"connection err", Toast.LENGTH_SHORT).show();
                    ((MyApplication) MovieListActivity.this.getApplication()).setIntenetConnection(false);
                }
                else {
                       recycleviewFn();
                }
            }
            return true;
        }
        if (id == R.id.rate) {
            MovieType=1;
            movieObj.ITEMS.clear();
            ((MyApplication) MovieListActivity.this.getApplication()).setType(MovieType);
            if(internet_connection){
                url = "https://api.themoviedb.org/3/movie/top_rated?api_key=f422e13255a0c5243a1627cb14581228";
                ((MyApplication) MovieListActivity.this.getApplication()).setIntenetConnection(true);
                new GetContacts().execute();

            }
              else
            {
                movieObj.ITEMS = movieDB.getAllmovies(MovieType);
            if(movieObj.ITEMS.size()==0) {
                Toast.makeText(MovieListActivity.this, "connection err", Toast.LENGTH_SHORT).show();
                ((MyApplication) MovieListActivity.this.getApplication()).setIntenetConnection(false);
            }
            else{
                recycleviewFn();
            }
            }
            return true;
        }
        if (id == R.id.favorite) {
            MovieType=2;
            ((MyApplication) MovieListActivity.this.getApplication()).setType(MovieType);
            movieObj.ITEMS = movieDB.getAllmovies(MovieType);
            if(  movieObj.ITEMS.isEmpty()){
                Toast.makeText(MovieListActivity.this,"FAVORITE IS EMPTY", Toast.LENGTH_SHORT).show();
            }
           else {
                recycleviewFn();
            }return true;
        }
        if (id == R.id.logout){

            ((MyApplication) MovieListActivity.this.getApplication()).setLogoutFlage(0);
            Log.d("logout1: ", "> " +((MyApplication) MovieListActivity.this.getApplication()).getLogoutFlage());


            startActivity(new Intent(MovieListActivity.this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MovieListActivity.this);
            pDialog.setMessage("\t \t \t Data Loading.......");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance

            movieObj =new Movie();
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            movieId= MovieTypeDB.getMoviesIDs(MovieType);
            int size=movieId.size();
            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null && MovieType!=2 ) {
                try {
                    internet_connection=true;
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    results = jsonObj.getJSONArray(TAG_RESULTSS);
                    // looping through All results
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);
                        int id = c.getInt(TAG_ID);
                        String poster_path = c.getString(TAG_POSTER_PATH);
                        String overview = c.getString(TAG_OVERVIEW);
                        String original_title = c.getString(TAG_ORIGINAL_TITLE);
                        double vote_average = c.getDouble(TAG_VOTE_AVERAGE);
                        String release_date = c.getString(TAG_RELEASE_DATE);
                        // add jsonObj to movie
                        Movie.MovieItem movieObj1 = new Movie.MovieItem(poster_path, overview, release_date, id, original_title, vote_average);
                        //avoid duplication
                        if(size ==0){
                            movieDB.Add_Movie(movieObj1,MovieType);
                       }
                        movieObj.addItem(movieObj1);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                  Toast.makeText(MovieListActivity.this," CONNECTION ERR", Toast.LENGTH_SHORT).show();
              }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
            {
                if (pDialog != null)
                    pDialog.dismiss();
                pDialog = null;
        }
            recycleviewFn();

        }

    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager =
                ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() !=
                null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
  public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
  }

public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private MovieListActivity.ClickListener clickListener;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView,
                                 final MovieListActivity.ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
}
