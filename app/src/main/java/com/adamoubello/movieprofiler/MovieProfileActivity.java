package com.adamoubello.movieprofiler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manuelpeinado.fadingactionbar.view.ObservableScrollable;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Movie;
import model.MovieProfile;
import model.Rating;
import util.Constants;

public class MovieProfileActivity extends AppCompatActivity {

    private Movie movie;
    private MovieProfile movieProfile = new MovieProfile();
    private Constants constants = new Constants();

    private TextView titleMovieProfile;
    private TextView yearMovieProfile;
    private TextView ratedMovieProfile;
    private TextView releasedMovieProfile;
    private TextView runtimeMovieProfile;
    private TextView genreMovieProfile;
    private TextView directorMovieProfile;
    private TextView writerMovieProfile;
    private TextView actorsMovieProfile;
    private TextView plotMovieProfile;
    private TextView languageMovieProfile;
    private TextView countryMovieProfile;
    private TextView awardsMovieProfile;
    //private TextView ratingsMovieProfile;
    private TextView metaScoreMovieProfile;
    private TextView imdbRatingMovieProfile;
    private TextView imdbVotesMovieProfile;
    private TextView imdbIDMovieProfile;
    private TextView typeMovieProfile;
    private TextView totalSeasonsMovieProfile;
    private TextView responseMovieProfile;
    private TextView dvdMovieProfile;
    private TextView boxOfficeMovieProfile;
    private TextView productionMovieProfile;
    private NetworkImageView posterMovieProfile;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private AsymmetricGridView listView;
    private ListAdapter adapter;
    private Toolbar mToolbar;
    private Drawable mActionBarBackgroundDrawable;
    private NetworkImageView mHeader;
    private int mLastDampedScroll;
    private int mInitialStatusBarColor;
    private int mFinalStatusBarColor;
    private SystemBarTintManager mStatusBarManager;
    private String websiteMovieObject;
    private Chip chipMetaScore;
    private Chip chipRating;
    private Chip chipVote;
    private TextView movieProfileRatings1;
    private TextView movieProfileRatings2;
    private TextView movieProfileRatings3;
    private RatingBar ratingBar;
    private Chip chipRating2;
    private Chip chipRating3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.Theme_Light_TranslucentActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_profile);

        Toast.makeText(getApplication().getApplicationContext(),
                "Scroll up to the movie profile.", Toast.LENGTH_LONG).show();

        mToolbar = findViewById(R.id.toolbar);
        mActionBarBackgroundDrawable = mToolbar.getBackground();
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        mStatusBarManager = new SystemBarTintManager(this);
        mStatusBarManager.setStatusBarTintEnabled(true);
        mInitialStatusBarColor = Color.BLACK;
        mFinalStatusBarColor = getResources().getColor(R.color.teal_200);

        mHeader = findViewById(R.id.header);
        //posterMovieProfile = findViewById(R.id.header);

        ObservableScrollable scrollView = (ObservableScrollable) findViewById(R.id.scrollview);
        scrollView.setOnScrollChangedCallback(this::onScroll);

        onScroll(-1, 0);

        /*FlexboxLayout flexboxLayout = findViewById(R.id.flexbox_layout);
        flexboxLayout.setFlexDirection(FlexDirection.ROW);

        View view = flexboxLayout.getChildAt(0);
        FlexboxLayout.LayoutParams lp = (FlexboxLayout.LayoutParams) view.getLayoutParams();
        lp.setOrder(-1);
        lp.setFlexGrow(2);
        view.setLayoutParams(lp);*/

        //posterMovieProfile = findViewById(R.id.header);
        titleMovieProfile = findViewById(R.id.movieProfileName);
        yearMovieProfile = findViewById(R.id.movieProfileYear);
        ratedMovieProfile = findViewById(R.id.movieProfileRated);
        releasedMovieProfile = findViewById(R.id.movieProfileReleased);
        runtimeMovieProfile = findViewById(R.id.movieProfileRuntime);
        genreMovieProfile = findViewById(R.id.movieProfileGenre);
        directorMovieProfile = findViewById(R.id.movieProfileDirector);
        writerMovieProfile = findViewById(R.id.movieProfileWriter);
        actorsMovieProfile = findViewById(R.id.movieProfileActors);
        plotMovieProfile = findViewById(R.id.movieProfilePlot);
        languageMovieProfile = findViewById(R.id.movieProfileLanguage);
        countryMovieProfile = findViewById(R.id.movieProfileCountry);
        awardsMovieProfile = findViewById(R.id.movieProfileAwards);
        metaScoreMovieProfile = findViewById(R.id.movieProfileMetaScore);
        imdbRatingMovieProfile = findViewById(R.id.movieProfileImdbRating);
        imdbVotesMovieProfile = findViewById(R.id.movieProfileImdbVotes);
        //imdbIDMovieProfile = findViewById(R.id.im);
        dvdMovieProfile = findViewById(R.id.movieProfileDvd);
        boxOfficeMovieProfile = findViewById(R.id.movieProfileBoxOffice);
        productionMovieProfile = findViewById(R.id.movieProfileProduction);

        typeMovieProfile = findViewById(R.id.movieProfileType);
        totalSeasonsMovieProfile = findViewById(R.id.movieProfileTotalSeasons);
        //responseMovieProfile = findViewById(R.id.movieProfileResponse);
        movieProfileRatings1 = findViewById(R.id.movieProfileRatings1);
        movieProfileRatings2 = findViewById(R.id.movieProfileRatings2);
        movieProfileRatings3 = findViewById(R.id.movieProfileRatings3);

        chipMetaScore = findViewById(R.id.chipMetaScore);
        chipRating = findViewById(R.id.chipRating);
        chipVote = findViewById(R.id.chipVote);
        ratingBar = findViewById(R.id.ratingBar);
        //chipRating2 = findViewById(R.id.chipRating2);
        //chipRating3 = findViewById(R.id.chipRating3);

        ////////////////////////////////////////////////////////////////

        movie = (Movie) getIntent().getSerializableExtra("movieProfileObj");
        Log.v("Show me the way :::", movie.toString());

        ////////////////////////////////////////////////////////////////

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET
                , constants.urlBuilder(null, movie.getImdbID()), null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v("The one and only ::: ", response.toString());

                            String titleMovieObject = response.getString("Title");
                            String yearMovieObject = response.getString("Year");
                            //String imdbIDMovieObject = response.getString("imdbID");
                            String typeMovieObject = response.getString("Type");
                            String posterMovieObject = response.getString("Poster");
                            String ratedMovieObject = response.getString("Rated");
                            String releasedMovieObject = response.getString("Released");
                            String runtimeMovieObject = response.getString("Runtime");
                            String genreMovieObject = response.getString("Genre");
                            String directorMovieObject = response.getString("Director");
                            String writerMovieObject = response.getString("Writer");
                            String actorsMovieObject = response.getString("Actors");
                            String plotMovieObject = response.getString("Plot");
                            String languageMovieObject = response.getString("Language");
                            String countryMovieObject = response.getString("Country");
                            String awardsMovieObject = response.getString("Awards");
                            String metaScoreMovieObject = response.getString("Metascore");
                            String imdbRatingMovieObject = response.getString("imdbRating");
                            String imdbVotesMovieObject = response.getString("imdbVotes");
                            //String responseMovieObject = response.getString("Response");
                            String ratings = response.getString("Ratings");

                            if ("N/A".equals(posterMovieObject)){
                                posterMovieObject = String.valueOf(Constants.POSTER_CINEMA);
                            }

                            movieProfile.setTitle(titleMovieObject);

                            mHeader.setImageUrl(posterMovieObject, imageLoader);
                            //mHeader.setBackgroundResource();
                            //Picasso.with(MovieProfileActivity.this).load(posterMovieObject).into(mHeader);
                            titleMovieProfile.setText(titleMovieObject);
                            yearMovieProfile.setText("Year : " + yearMovieObject);
                            ratedMovieProfile.setText("Rated : " + ratedMovieObject);
                            releasedMovieProfile.setText("Release : " + releasedMovieObject);
                            runtimeMovieProfile.setText("Runtime : " + runtimeMovieObject);
                            genreMovieProfile.setText("Genre : " + genreMovieObject);
                            directorMovieProfile.setText("Director : " + directorMovieObject);
                            writerMovieProfile.setText("Writer : " + writerMovieObject);
                            actorsMovieProfile.setText("Actors : " + actorsMovieObject);
                            plotMovieProfile.setText("Plot : " + plotMovieObject);
                            languageMovieProfile.setText("Language : " + languageMovieObject);
                            countryMovieProfile.setText("Country : " + countryMovieObject);
                            awardsMovieProfile.setText("Awards : " + awardsMovieObject);
                            metaScoreMovieProfile.setText("Metascore : " + metaScoreMovieObject);
                            imdbRatingMovieProfile.setText("Rating : " + imdbRatingMovieObject);
                            imdbVotesMovieProfile.setText("Votes : " + imdbVotesMovieObject);
                            typeMovieProfile.setText("Type : " + typeMovieObject);
                            //responseMovieProfile.setText("response + " + responseMovieObject);
                            chipMetaScore.setText(metaScoreMovieObject);
                            chipRating.setText(imdbRatingMovieObject);
                            chipVote.setText(imdbVotesMovieObject);

                            JSONArray ratingsArray = new JSONArray(ratings);
                            Log.v("Array 1 : ", ratingsArray.toString());
                            if (ratingsArray.length() == 0){
                                movieProfileRatings1.setVisibility(View.GONE);
                                movieProfileRatings2.setVisibility(View.GONE);
                                movieProfileRatings3.setVisibility(View.GONE);
                                ratingBar.setIsIndicator(true);
                            } else {
                                String value0 = parseRatings(ratingsArray, 0, movieProfileRatings1);
                                String value1 = parseRatings(ratingsArray, 1, movieProfileRatings2);
                                String value2 = parseRatings(ratingsArray, 2, movieProfileRatings3);
                                String[] numStars = value0.split("/");
                                ratingBar.setNumStars(Integer.parseInt(numStars[1]));
                                ratingBar.setRating(Float.parseFloat(numStars[0]));

                                /*chipRating2.setText(value1);
                                chipRating3.setText(value2);*/
                            }

                            if ("movie".equals(typeMovieObject)){
                                websiteMovieObject = response.getString("Website");

                                totalSeasonsMovieProfile.setVisibility(View.GONE);

                                dvdMovieProfile.setText("DVD : " + response.getString("DVD"));
                                boxOfficeMovieProfile.setText("Box office : " + response
                                        .getString("BoxOffice"));
                                productionMovieProfile.setText("Production : " + response
                                        .getString("Production"));
                            }

                            if ("series".equals(typeMovieObject)){
                                String totalSeasonsMovieObject = response
                                        .getString("totalSeasons");
                                totalSeasonsMovieProfile
                                 .setText("Total seasons : " + totalSeasonsMovieObject);

                                dvdMovieProfile.setVisibility(View.GONE);
                                boxOfficeMovieProfile.setVisibility(View.GONE);
                                productionMovieProfile.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("What is wrong my dude ?", error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-RapidAPI-Key"
                        , "3c67451418mshe39efddd63499c1p1b3cc6jsn50774751c07e");
                params.put("X-RapidAPI-Host", "movie-database-alternative.p.rapidapi.com");

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonRequest);
    }

    @SuppressLint("SetTextI18n")
    private String parseRatings(JSONArray array, int i, TextView textView){
        JSONObject jsonObject;

        try {
            if (array.getJSONObject(i) != null){
                jsonObject = array.getJSONObject(i);

                Log.v("Object 1 : ", jsonObject.toString());
                Log.v("Object 1 inside 1: "
                        , jsonObject.getString("Source"));
                Log.v("Object 1 inside 2: "
                        , jsonObject.getString("Value"));

                textView.setText(jsonObject.getString("Source")
                        + " : " + jsonObject.getString("Value"));
                textView.setVisibility(View.VISIBLE);

                return jsonObject.getString("Value");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareOnSocialMedia();
                return true;
//            case R.id.action_email:
//                sendEmail();
//                return true;
            case R.id.action_website:
                if ((websiteMovieObject != null) && (!"N/A".equals(websiteMovieObject))){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteMovieObject));
                    startActivity(intent);
                }
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void sendEmail(){

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        //emailIntent.setType("text/html");
        // You can use "mailto:" if you don't know the address beforehand.
        //emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"adamou.bello@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Check this movie profile");
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(constants.formatMail(movieProfile)));

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MovieProfileActivity.this, "No email clients installed."
                    , Toast.LENGTH_SHORT).show();
        }

    }

    private void shareOnSocialMedia(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Download the android movie app: Movie profiler");
        startActivity(Intent.createChooser(intent, "Share"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        menuItem.setVisible(false);
        MenuItem menuItemActionShare = menu.findItem(R.id.action_share);
        menuItemActionShare.setVisible(true);
//        MenuItem menuItemActionEmail = menu.findItem(R.id.action_email);
//        menuItemActionEmail.setVisible(true);

        if ((websiteMovieObject != null) && (!"N/A".equals(websiteMovieObject))) {
            MenuItem menuItemActionWebsite = menu.findItem(R.id.action_website);
            menuItemActionWebsite.setVisible(true);
        }
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    //@Override
    public void onScroll(int l, int scrollPosition) {
        int headerHeight = mHeader.getHeight() - mToolbar.getHeight();
        float ratio = 0;
        if (scrollPosition > 0 && headerHeight > 0)
            ratio = (float) Math.min(Math.max(scrollPosition, 0), headerHeight) / headerHeight;

        updateActionBarTransparency(ratio);
        updateStatusBarColor(ratio);
        updateParallaxEffect(scrollPosition);
    }

    private void updateActionBarTransparency(float scrollRatio) {
        int newAlpha = (int) (scrollRatio * 255);
        mActionBarBackgroundDrawable.setAlpha(newAlpha);
        mToolbar.setBackground(mActionBarBackgroundDrawable);
    }

    private void updateStatusBarColor(float scrollRatio) {
        int r = interpolate(Color.red(mInitialStatusBarColor), Color.red(mFinalStatusBarColor), 1 - scrollRatio);
        int g = interpolate(Color.green(mInitialStatusBarColor), Color.green(mFinalStatusBarColor), 1 - scrollRatio);
        int b = interpolate(Color.blue(mInitialStatusBarColor), Color.blue(mFinalStatusBarColor), 1 - scrollRatio);
        mStatusBarManager.setTintColor(Color.rgb(r, g, b));
    }

    private void updateParallaxEffect(int scrollPosition) {
        float damping = 0.5f;
        int dampedScroll = (int) (scrollPosition * damping);
        int offset = mLastDampedScroll - dampedScroll;
        mHeader.offsetTopAndBottom(-offset);

        mLastDampedScroll = dampedScroll;
    }

    private int interpolate(int from, int to, float param) {
        return (int) (from * param + to * (1 - param));
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        Log.v("Profile onResume", "Test");
        actionBar.hide();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }*/
}