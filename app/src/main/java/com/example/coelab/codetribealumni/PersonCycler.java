package com.example.coelab.codetribealumni;

/**
 * Created by Codetribe on 11/10/2017.
 */

public class PersonCycler
{
    private String name;
    private String surname;
    private int img_id;

    public PersonCycler() {
    }

    public PersonCycler(String name) {
        this.name = name;
    }

    public PersonCycler(String name, String surname, int img_id) {
        this.name = name;
        this.surname = surname;
        this.img_id = img_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }
}
