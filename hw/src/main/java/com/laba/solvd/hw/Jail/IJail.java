package com.laba.solvd.hw.Jail;

import com.laba.solvd.hw.Person.Criminal;

import java.util.ArrayList;

public interface IJail {
    void addInmate (Criminal criminal);
    boolean removeInmate(Criminal criminal) throws Jail.InmateNotFoundException;
    ArrayList<Criminal> getInmates();
    void setInmates(ArrayList<Criminal> inmates);

}
