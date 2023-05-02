import java.util.ArrayList;

public interface IJail {
    void addInmate (Criminal criminal);
    boolean removeInmate(Criminal criminal) throws Jail.InmateNotFoundException;
    ArrayList<Criminal> getInmates();
    void setInmates(ArrayList<Criminal> inmates);

}
