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
}