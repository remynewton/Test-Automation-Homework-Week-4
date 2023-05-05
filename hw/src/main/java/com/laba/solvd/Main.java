package com.laba.solvd;
import com.laba.solvd.hw.*;
import com.laba.solvd.hw.Beast.PoliceDog;
import com.laba.solvd.hw.Case.*;
import com.laba.solvd.hw.Jail.Jail;
import com.laba.solvd.hw.Person.*;

import java.net.URI;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends PoliceStation {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String logFilePath = "/Users/JamesRONewton/Documents/Programming/TestAutomation/hw/src/main/java/com/laba/solvd/hw/police-station.log";
        System.setProperty("logFilename", logFilePath);
        System.setProperty("logPattern", "%d [%t] %-5level %logger{36} - %msg%n");
        Officer officer1 = new Officer("John Doe", "06/12/1981", "123 Main St", 12345, "Patrol");
        List<String> trainings1 = Arrays.asList("Patrol");
        PoliceDog patroldog1 = new PoliceDog("Fido", "05/17/2019", true, "German Shepherd", trainings1);
        ArrayList<ICrime> crimes1 = new ArrayList<>();
        crimes1.add(new Crime(Crime.Type.JAVA_INSTR));
        Criminal criminal1 = new Criminal("Andrei Trukhanovich", "07/17/1991", "456 Elm St", crimes1);
        Victim victim1 = new Victim("Remy Newton", "05/22/1997", "789 Oak Ave", "9876");
        Case case1 = new Case("repeated Java instruction", officer1, criminal1, victim1, false);
        PoliceStation station = new PoliceStation();
        UnsolvedCases<Case> unsolvedCases = new UnsolvedCases<>();
        unsolvedCases.add(case1);
        Jail jail1 = new Jail(50);
        PoliceStation.addPerson(officer1);
        PoliceStation.addPerson(criminal1);
        PoliceStation.addPerson(victim1);

        logger.info("Officer {} from {} department is investigating a case of {}. That's {}.", officer1.getName(), officer1.getRank(), case1.getDescription(), officer1.getProfile());
        logger.info("The victim of the crime is {}.", victim1.getProfile());
        logger.info("Officer {} uses his trusty police dog {} to patrol the area for the criminal.", officer1.getName(), patroldog1.getName());
        logger.info(patroldog1.patrol());
        logger.info("The officer has apprehended the criminal. {}", criminal1.getProfile());
        jail1.addInmate(criminal1);
        logger.info("{} gets a treat.", patroldog1.getName());
        String verb;
        if (jail1.getInmates().size() > 1) {
            verb = "are";

        } else {
            verb = "is";
        }
        logger.info("There {} now {} inmate(s) in the jail. Number of jails: {}.", verb, jail1.getInmates().size(), Jail.getTotalJails());
        LogReader reader = new LogReader();
        reader.readLogFile(logFilePath);
    }
}