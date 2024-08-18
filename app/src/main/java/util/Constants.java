package util;

import android.util.Log;

import com.adamoubello.movieprofiler.AppController;
import com.adamoubello.movieprofiler.R;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.CustomListviewAdapter;
import model.Movie;
import model.MovieProfile;

public class Constants {

    public static final String REST_URL = "https://movie-database-alternative.p.rapidapi.com";
    public static final String X_RAPIDAPI_VALUE = "3c67451418mshe39efddd63499c1p1b3cc6jsn50774751c07e";
    public static final String X_RAPIDAPI_KEY = "X-RapidAPI-Key";
    public static final String X_RAPIDAPI_HOST_VALUE = "movie-database-alternative.p.rapidapi.com";
    public static final String X_RAPIDAPI_HOST = "X-RapidAPI-Host";
    public static final String REST_URL_TEST1 = "https://movie-database-alternative.p.rapidapi.com/?s=equalizer&r=json";
    //public static final String POSTER_CINEMA = "http://musicapp.adamoubello.com/images/cinema_poster1.jpg";
    public static final int POSTER_CINEMA = R.drawable.cinema_poster1;
    public static final String REST_URL_TEST2 = "https://movie-database-alternative.p.rapidapi.com/?r=json&i=tt0455944";
    public static final String BASE_REST_URL = "https://movie-database-alternative.p.rapidapi.com/?r=json";

    public String formatMail(MovieProfile movieProfile){

        String mail =
                "<table>" +
                "<tbody>" +
                "            <td>" +
                "              <img src=\"" + movieProfile.getPoster() + "\"/>" +
                "            </td>" +
                "            <td>" +
                "                Title : " + movieProfile.getTitle() + "<br><br>" +
                "                Year : " + movieProfile.getYear() + "<br><br>" +
                "                Rated : " + movieProfile.getRated() + "<br><br>" +
                "                Released : " + movieProfile.getReleased() + "<br><br>" +
                "                Type : " + movieProfile.getType() + "<br><br>" +
                "                Genre : " + movieProfile.getGenre() + "<br><br>" +
                "                Director : " + movieProfile.getDirector() + "<br><br>" +
                "                Country : " + movieProfile.getCountry() + "<br><br>" +
                "                Awards : " + movieProfile.getAwards() + "<br><br>" +
                "                Writer : " + movieProfile.getWriter() + "<br><br>" +
                "                Actors : " + movieProfile.getActors() + "<br><br>" +
                "                Plot : " + movieProfile.getPlot() + "<br><br>" +
                "                Language : " + movieProfile.getLanguage() + "<br><br>" +
                "                Runtime : " + movieProfile.getRuntime() + "<br><br>" +
                "                MetaScore : " + movieProfile.getMetaScore() + "<br><br>" +
                "                imdbRating : " + movieProfile.getImdbRating() + "<br><br>" +
                "                imdbVotes : " + movieProfile.getImdbVotes() + "<br><br>" +
                "            </td>" +
                "</tbody>" +
                "</table>";

        return mail;
    }


    public void httpMovieRequesting(ArrayList<Movie> movies, String url){
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET
                , url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v("The whole response ::: ", response.toString());
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
                            //adapter.notifyDataSetChanged();

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

        /*AppController.getInstance()
                .addToRequestQueue(requestWithSomeHttpHeaders(Constants.REST_URL_TEST1));*/
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    public MovieProfile httpMovieProfileRequesting(String imdbID, String url){

        MovieProfile movieProfile = new MovieProfile();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET
                , url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v("The one and only ::: ", response.toString());

                            String titleMovieObject = response.getString("Title");
                            String yearMovieObject = response.getString("Type")
                                        + " | " + response.getString("Year");
                            String imdbIDMovieObject = response.getString("imdbID");
                            String typeMovieObject = response.getString("Type");
                            String posterMovieObject = response.getString("Poster");

                            if ("N/A".equals(posterMovieObject)){
                                posterMovieObject = String.valueOf(Constants.POSTER_CINEMA);
                            }

                            movieProfile.setImdbID(imdbIDMovieObject);
                            movieProfile.setYear(yearMovieObject);
                            movieProfile.setTitle(titleMovieObject);
                            movieProfile.setType(typeMovieObject);
                            movieProfile.setPoster(posterMovieObject);


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
        return movieProfile;
    }

    public String urlBuilder(String s, String i){

        if (s != null){
            return BASE_REST_URL + "&s=" + s;
        }
        if (i != null){
            return BASE_REST_URL + "&i=" + i;
        }

        return BASE_REST_URL;
    }
}
