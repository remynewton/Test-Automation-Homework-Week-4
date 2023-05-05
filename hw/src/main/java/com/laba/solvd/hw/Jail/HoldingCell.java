package com.laba.solvd.hw.Jail;

import com.laba.solvd.hw.Person.Criminal;

import java.util.ArrayList;

public final class HoldingCell implements IJail {
    private ArrayList<Criminal> inmates = new ArrayList<>();

    HoldingCell() {
        // private constructor to prevent instantiation
    }

    @Override
    public void addInmate(Criminal criminal) {
        inmates.add(criminal);
    }

    @Override
    public boolean removeInmate(Criminal criminal) {
        return inmates.remove(criminal);
    }

    @Override
    public ArrayList<Criminal> getInmates() {
        return inmates;
    }

    @Override
    public void setInmates(ArrayList<Criminal> inmates) {
        this.inmates = inmates;
    }
}
