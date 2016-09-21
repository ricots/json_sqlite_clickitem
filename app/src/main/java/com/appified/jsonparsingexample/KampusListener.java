package com.appified.jsonparsingexample;

import java.util.ArrayList;

/**
 * Created by PP on 6/14/2015.
 */
public interface KampusListener {

    public void addCity(Kampus kampus);
    public void addjur(Jurusan jur);

    public ArrayList<Kampus> getAllCity();
    public ArrayList<Jurusan> getAlljur();


    public int getCityCount();
    public int getjur();
}
