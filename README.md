# Test-Automation-Homework-Week-4

I am still trying to figure how to get Maven to work probably so that I can implement log4j in my project. Hopefully, I will have that done very soon. In the mean time, the next prompt is:

Practical tasks:
Add 5 collections to the hierarchy.
Create custom implementation of LinkedList with generic and use it in your project.

Since I've already added many collections to my project, I didn't necessarily need to add more, but surrendipitously there was a need for some HashSets to store the different objects created in the PoliceStation class. Additionaly, I decided to create a custom LinkedList with generic called "UnsolvedCases", which stores my case objects in order of priority (i.e. "severity"). My brand new code is as follows:

```
import java.util.*;

class PoliceStation {

    protected Set<Case> solvedCases = new HashSet<>();
    protected static Set<Person> persons = new HashSet<>();
    protected static Set<Officer> officers = new HashSet<>();
    protected static Set<Criminal> criminals = new HashSet<>();
    protected static Set<Victim> victims = new HashSet<>();

    public static void addPerson(Person p) {
        persons.add(p);
        if (p instanceof Officer) {
            officers.add((Officer) p);
        } else if (p instanceof Criminal) {
            criminals.add((Criminal) p);
        } else if (p instanceof Victim) {
            victims.add((Victim) p);
        }
    }

    public void addCase(Case c) {
        solvedCases.add(c);
    }

    public final void printReport() {
        for (Case c : solvedCases) {
            System.out.println("Case Report:");
            System.out.println("  Description: " + c.getDescription());
            System.out.println("  Officer: " + c.getAssignedOfficer().getName() + " (" + c.getAssignedOfficer().getRank() + ")");
            System.out.println("  Criminal: " + c.getSuspect().getName() + " (" + c.getSuspect().getAddress() + ")");
            System.out.println("  Victim: " + c.getVictim().getName() + " (" + c.getVictim().getAddress() + ")");
            System.out.println("  Outcome: " + (c.checkSolved() ? "Solved" : "Unsolved"));
        }
    }

    public class UnsolvedCases<T extends Case> {
        private Node head;
        private int size;
    
        private class Node {
            T data;
            Node next;
    
            Node(T data) {
                this.data = data;
                next = null;
            }
        }
    
        public void add(T data) {
            int priority = data.getSeverity();
            Node newNode = new Node(data);
            if (head == null || priority > head.data.getSeverity()) {
                newNode.next = head;
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null && priority <= current.next.data.getSeverity()) {
                    current = current.next;
                }
                newNode.next = current.next;
                current.next = newNode;
            }
            size++;
        }
    
        public T remove() {
            if (head == null) {
                return null;
            }
            T removedData = head.data;
            head = head.next;
            size--;
            return removedData;
        }
    
        public boolean isEmpty() {
            return head == null;
        }
    
        public int size() {
            return size;
        }
    }           

    public static void main(String[] args) {
        Officer officer1 = new Officer("John Doe", "06/12/1981", "123 Main St", 12345, "Patrol");
        List<String> trainings1 = Arrays.asList("Patrol");
        PoliceDog patroldog1 = new PoliceDog("Fido", "05/17/2019", true, "German Shepherd", trainings1);
        ArrayList<ICrime> crimes1 = new ArrayList<>();
        crimes1.add(new Crime(Crime.Type.JAVA_INSTR));
        Criminal criminal1 = new Criminal("Andrei Trukhanovich", "07/17/1991", "456 Elm St", crimes1);
        Victim victim1 = new Victim("Remy Newton", "05/22/1997", "789 Oak Ave", "9876");
        Case case1 = new Case("repeated Java instruction", officer1, criminal1, victim1, false);
        PoliceStation station = new PoliceStation();
        PoliceStation.UnsolvedCases<Case> unsolvedCases = station.new UnsolvedCases<>();
        unsolvedCases.add(case1);
        Jail jail1 = new Jail(50);
        PoliceStation.addPerson(officer1);
        PoliceStation.addPerson(criminal1);
        PoliceStation.addPerson(victim1);

        System.out.println("Officer " + officer1.getName() + " from " + officer1.getRank() + " department is investigating a case of " + case1.getDescription() + ". That's " + officer1.getProfile() + ".");
        System.out.println("The victim of the crime is " + victim1.getProfile() + ".");
        System.out.println("Officer " + officer1.getName() + " uses his trusty police dog " + patroldog1.getName() + " to patrol the area for the criminal.");
        patroldog1.patrol();
        System.out.println("The officer has apprehended the criminal. " + criminal1.getProfile());
        jail1.addInmate(criminal1);
        System.out.println(patroldog1.getName() + " gets a treat.");
        String verb;
        if (jail1.getInmates().size() > 1) {
            verb = "are";

        } else {
            verb = "is";
        }
        System.out.println("There " + verb + " now " + jail1.getInmates().size() + " inmate(s) in the jail. Number of jails: " + Jail.getTotalJails() + ".");
    }
}
```

Additionally, I greatly simplified and improved the logic for a custom exception in Jail.java. I also made a list of an ArrayList of jails and made it so totalJails was the set to the size of that list. Here's my new and improved code for the Jail class:

```
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;

public class Jail {
    private ArrayList<Criminal> inmates = new ArrayList<>();
    private int capacity;
    protected static List<Jail> jails = new ArrayList<>();
    protected static int totalJails;

    public Jail(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be greater than zero!");
        }
        this.capacity = capacity;
        jails.add(this);
        totalJails = jails.size();
    }

    public static int getTotalJails() {
        return totalJails;
    }

    public static List<Jail> getJails() {
        return jails;
    }

    public ArrayList<Criminal> getInmates() {
        return inmates;
    }

    public void setInmates(ArrayList<Criminal> inmates) {
        this.inmates = inmates;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addInmate(Criminal criminal) {
        try {
            if (inmates.size() < capacity) {
                inmates.add(criminal);
            } else {
                throw new JailFullException();
            }
        } catch (JailFullException e) {
            System.out.println(e.getMessage());
            HoldingCell.addInmate(criminal);
        }
    }    

    public boolean removeInmate(Criminal criminal) throws InmateNotFoundException {
        if (inmates.remove(criminal)) {
            return true;
        }
    
        System.out.println("The specified inmate was not found in this jail.");
        System.out.print("Do you want to remove that inmate from all jails, including the holding cell? (yes/no): ");
    
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toLowerCase();
        scanner.close();
    
        if (input.equals("yes")) {
            for (Jail jail : jails) {
                if (jail.getInmates().contains(criminal)) {
                    jail.removeInmate(criminal);
                }
            }
            Jail.HoldingCell.removeInmate(criminal);
            throw new InmateNotFoundException("The inmate has been removed from all jails, including the holding cell.");
        } else if (input.equals("no")) {
            return false;
        } else {
            System.out.println("Invalid input. Please enter either 'yes' or 'no'.");
            return removeInmate(criminal);
        }
    }
    
    public class InmateNotFoundException extends Exception {
        public InmateNotFoundException(String message) {
            super(message);
        }
    }            

    public class JailFullException extends Exception {
        public JailFullException() {
            super("This jail is at full capacity! The inmate will be kept in holding for now.");
        }
    }    

    public static final class HoldingCell {
        private static ArrayList<Criminal> inmates = new ArrayList<>();
        private static int capacity;
    
        public static ArrayList<Criminal> getInmates() {
            return inmates;
        }
    
        public static void setInmates(ArrayList<Criminal> inmates) {
            HoldingCell.inmates = inmates;
        }
    
        public int getCapacity() {
            return capacity;
        }
    
        public void setCapacity(int capacity) {
            HoldingCell.capacity = capacity;
        }
    
        public static boolean addInmate(Criminal criminal) {
            if (inmates.size() < capacity) {
                inmates.add(criminal);
                return true;
            }
            return false;
        }
    
        public static boolean removeInmate(Criminal criminal) {
            return inmates.remove(criminal);
        }
    }
}
```
