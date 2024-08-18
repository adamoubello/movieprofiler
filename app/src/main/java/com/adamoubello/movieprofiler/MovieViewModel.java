package com.adamoubello.movieprofiler;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Movie;
import util.Constants;

public class MovieViewModel extends AndroidViewModel {

    private final String TAG = MovieViewModel.class.getSimpleName();
    private MutableLiveData<List<Movie>> mMovies;
    private Constants constants = new Constants();

    public MovieViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<List<Movie>> getLiveMovies(ArrayList<Movie> movies, String query) {
        if (mMovies == null) {
            mMovies = new MutableLiveData<>();
        }
        httpMovieRequesting(movies, constants.urlBuilder(query, null));
        return mMovies;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "On cleared called");
    }

    public void httpMovieRequesting(ArrayList<Movie> movies, String url){
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET
                , url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v("The whole response ::: ", response.toString());

                            String boolMovie = response.getString("Response");

                            if ("False".equals(boolMovie)){
                                Toast.makeText(getApplication().getApplicationContext(),
                                        response.getString("Error") + " TRY ANOTHER SEARCH."
                                        , Toast.LENGTH_LONG).show();
                            }

                            if ("True".equals(boolMovie)){
                                movies.clear();
                                JSONArray movieArray = response.getJSONArray("Search");
                                Log.v("Data : ", movieArray.toString());

                                for (int i=0; i < movieArray.length(); i++){
                                    JSONObject movieObject = movieArray.getJSONObject(i);

                                    String titleMovieObject = movieObject.getString("Title");
                                    String yearMovieObject = movieObject.getString("Type")
                                            + " | " + movieObject.getString("Year");
                                    String imdbIDMovieObject = movieObject.getString("imdbID");
                                    String typeMovieObject = movieObject.getString("Type");
                                    String posterMovieObject = movieObject.getString("Poster");

                                    if ("N/A".equals(posterMovieObject)){
                                        posterMovieObject = String.valueOf(Constants.POSTER_CINEMA);
                                    }

                                    Movie movie = new Movie();
                                    movie.setImdbID(imdbIDMovieObject);
                                    movie.setYear(yearMovieObject);
                                    movie.setTitle(titleMovieObject);
                                    movie.setType(typeMovieObject);
                                    movie.setPoster(posterMovieObject);

                                    movies.add(movie);
                                }

                                mMovies.setValue(movies);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}
