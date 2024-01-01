package com.example.weatherapp;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class movieList extends Fragment {





    private RequestQueue mQueue;
    MyAdapter ma;
    ListView listView;
    ArrayList<Movie> data;
    private View fragmentView; // Add a member variable to hold the fragment's view



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        Log.d("myApp","min info");
        listView = fragmentView.findViewById(R.id.my_list);
        if (data == null) {
            data = new ArrayList<>();
            mQueue = Volley.newRequestQueue(getContext());
            jsonParse(fragmentView);
        } else if (ma != null) {
            listView.setAdapter(ma);
        }


        return fragmentView;
    }
    private void jsonParse(View fragmentView) {
        String url = "https://api.myjson.online/v1/records/746ec80c-5b17-4295-aad8-b1c899249e4a";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("myApp","first info");
                    JSONArray jsonArray = response.getJSONArray("data");
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i=0 ; i< jsonArray.length();i++){
                        Log.d("myApp","second info");
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        JSONArray genres = jsonObject.getJSONArray("genres");
                        String poster = jsonObject.getString("poster");
                        double rating = jsonObject.getDouble("rating");
                        String overview = jsonObject.getString("overview");
                        long releaseDate = jsonObject.getLong("release_date");
                        Log.d("myApp","thered info");
                        Movie move = new Movie(id,title,genres,poster,rating,overview,releaseDate);
                        data.add(move);
                        Log.d("myApp","forth info");
                    }
                    if (ma == null) {
                        ma = new MyAdapter(fragmentView.getContext(), data);
                        listView.setAdapter(ma);
                    } else {
                        ma.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

    }


}