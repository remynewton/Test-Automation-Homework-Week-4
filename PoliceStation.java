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