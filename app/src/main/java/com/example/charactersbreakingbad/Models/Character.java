package com.example.charactersbreakingbad.Models;

import java.util.List;

public class Character {

    private int char_id;
    private String name;
    private List<String> occupation;
    private String img;
    private String nickname;
    private String portrayed;
    private String status;
    private String favStatus;

    public Character() {
    }

    public Character(int char_id, String name, List<String> occupation, String img, String nickname, String portrayed, String favStatus, String status) {
        this.char_id = char_id;
        this.name = name;
        this.occupation = occupation;
        this.img = img;
        this.nickname = nickname;
        this.portrayed = portrayed;
        this.favStatus = favStatus;
        this.status = status;
    }

    public int getChar_id() {
        return char_id;
    }

    public void setChar_id(int char_id) {
        this.char_id = char_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<String> occupatioh) {
        this.occupation = occupatioh;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPortrayed() {
        return portrayed;
    }

    public void setPortrayed(String portrayed) {
        this.portrayed = portrayed;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
