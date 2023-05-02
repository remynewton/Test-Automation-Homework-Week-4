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