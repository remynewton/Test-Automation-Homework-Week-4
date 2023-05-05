package com.laba.solvd.hw;
import com.laba.solvd.hw.Case.*;
import com.laba.solvd.hw.Person.*;

import java.util.*;

public class PoliceStation {

    protected Set<Case> solvedCases = new HashSet<>();
    protected static Set<Person> persons = new HashSet<>();
    protected static Set<Officer> officers = new HashSet<>();
    protected static Set<Criminal> criminals = new HashSet<>();
    protected static Set<Victim> victims = new HashSet<>();

    public static void addPerson(Person p) {
        persons.add(p);
        switch (p.getClass().getSimpleName()) {
            case "Officer":
                officers.add((Officer) p);
                break;
            case "Criminal":
                criminals.add((Criminal) p);
                break;
            case "Victim":
                victims.add((Victim) p);
                break;
            default:
                break;
        }
    }

    public void addCase(Case c) {
        solvedCases.add(c);
    }

    public final void printReport() {
        for (Case c : solvedCases) {
            System.out.println("Case Report:");
            System.out.println(c.toString());
        }
    }
}