package com.example.charactersbreakingbad.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.charactersbreakingbad.API.API;
import com.example.charactersbreakingbad.API.APIServices.CharacterService;
import com.example.charactersbreakingbad.Adapters.MyAdapter;
import com.example.charactersbreakingbad.Models.Character;
import com.example.charactersbreakingbad.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CharacterService service;
    private Call<List<Character>> characterCall;
    private ArrayList<Character> characterItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();

        service = API.getApi().create(CharacterService.class);
        load();
    }

    private void setUI() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void load() {
        characterCall = service.getCharacter();
        characterCall.enqueue(new Callback<List<Character>>() {

            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                List<Character> characters = response.body();
                setResult(characters);
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setResult(List<Character> characterList) {

        for (Character chr : characterList) {
            characterItems.add(new Character(chr.getChar_id(), chr.getName(), chr.getOccupation(),
                    chr.getImg(), chr.getNickname(), chr.getPortrayed(), "0", chr.getStatus()));
        }

        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mAdapter = new MyAdapter(characterItems, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

}

