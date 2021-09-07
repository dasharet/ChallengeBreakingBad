package com.example.charactersbreakingbad.API.APIServices;

import com.example.charactersbreakingbad.Models.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CharacterService {
    @GET("characters")
    Call<List<Character>> getCharacter();
}
