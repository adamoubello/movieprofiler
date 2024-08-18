package com.adamoubello.movieprofiler;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import data.CustomListviewAdapter;
import model.Movie;
import util.Constants;

public class MainActivity extends AppCompatActivity {

    private CustomListviewAdapter adapter;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ListView listView;
    private Constants constants = new Constants();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(android.R.style.Theme);
        //setTheme(R.style.Theme_MovieSearch);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#FFF498AB")));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.movieListview);
        progressBar = findViewById(R.id.progressBar);
        adapter = new CustomListviewAdapter(MainActivity.this, R.layout.list_row, movies);
        listView.setAdapter(adapter);

        constants.httpMovieRequesting(movies, Constants.REST_URL_TEST1);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu, menu);
        return true;*/

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem menuItemActionShare = menu.findItem(R.id.action_share);
        menuItemActionShare.setVisible(false);

        MenuItem menuItemActionWebsite = menu.findItem(R.id.action_website);
        menuItemActionWebsite.setVisible(false);

//        MenuItem menuItemActionEmail = menu.findItem(R.id.action_email);
//        menuItemActionEmail.setVisible(false);

        //getSupportMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
        searchView.setMaxWidth(650);
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

            public boolean onQueryTextChange(String newText) {
                // This is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                // **Here you can get the value "query" which is entered in the search box.**
                progressBar.setVisibility(View.VISIBLE);
                searchView.clearFocus();
                MovieViewModel movieModel = new ViewModelProvider(MainActivity.this)
                        .get(MovieViewModel.class);
                movieModel.getLiveMovies(movies, query)
                        .observe(MainActivity.this, movieList -> {
                    // update UI
                    Log.v("Check Movie List :::: ", movieList.toString());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                });
                return true;
            }
        };

        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(queryTextListener);

        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_bar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("Inside onResume", "test");
        /*getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#FFF498AB")));*/
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff00FFED));
        //ActionBar actionBar = getSupportActionBar();
        //mActionBar.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
        //setTheme(R.style.Theme_MovieSearch);
    }

   /* @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = super.getTheme();
        theme.applyStyle(R.style.Theme_MovieSearch, true);
        return theme;
    }*/
}