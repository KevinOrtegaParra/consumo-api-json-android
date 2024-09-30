package com.example.posts.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.posts.R;
import com.example.posts.model.Noticia;
import com.example.posts.my_interface.INoticiaService;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter arrayAdapter;
    private ListView list;
    private ArrayList<String> titles = new ArrayList<>();
    private static final String URL_BASE = "https://jsonplaceholder.typicode.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        list = findViewById(R.id.list);
        list.setAdapter(arrayAdapter);
        getPosts();
    }

    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        INoticiaService postService = retrofit.create(INoticiaService.class);
        Call<List<Noticia>> call = postService.getPost();
        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>>
                    response) {
                for (Noticia post : response.body()) {
                    titles.add(post.getTitle());
                }
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
            }
        });
    }
}