package data;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.adamoubello.movieprofiler.AppController;
import com.adamoubello.movieprofiler.MovieProfileActivity;
import com.adamoubello.movieprofiler.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Movie;
import model.MovieProfile;
import util.Constants;

public class CustomListviewAdapter extends ArrayAdapter<Movie> {

    private LayoutInflater inflater;
    private ArrayList<Movie> data;
    private Activity mContext;
    private int layoutResourceId;
    private MovieProfile movieProfile = new MovieProfile();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListviewAdapter(Activity context, int resource, ArrayList<Movie> objs) {
        super(context, resource, objs);
        data = objs;
        mContext = context;
        layoutResourceId = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public Movie getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(Movie item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = null;
        if (row == null){
            inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.posterTextview = row.findViewById(R.id.movieImage);
            viewHolder.titleTextview = row.findViewById(R.id.titleText);
            viewHolder.yearTextview = row.findViewById(R.id.yearText);
            //viewHolder.typeTextview = row.findViewById(R.id.typeText);
            row.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.movie = data.get(position);

        viewHolder.titleTextview.setText(viewHolder.movie.getTitle());
        viewHolder.yearTextview.setText(viewHolder.movie.getYear());
        //viewHolder.typeTextview.setText(R.string.typeAdapter + viewHolder.movie.getType());
        viewHolder.posterTextview.setImageUrl(viewHolder.movie.getPoster(), imageLoader);

        final ViewHolder finalViewHolder = viewHolder;

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, MovieProfileActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("movieProfileObj", finalViewHolder.movie);
                intent.putExtras(mBundle);
                mContext.startActivity(intent);
            }
        });

        return row;
    }

    public class ViewHolder{
        Movie movie;
        TextView titleTextview;
        TextView yearTextview;
        TextView imdbIDTextview;
        TextView typeTextview;
        NetworkImageView posterTextview;
    }

}
