import model.Database;
import view.RegistrationMenu;

public class Main {
    public static void main(String[] args) {
        Database.prepareDatabase();
        Database.readDataLineByLine("Resources\\Cards\\SpellTrap.csv");
        Database.readDataLineByLine("Resources\\Cards\\Monster.csv");
        new RegistrationMenu().run();   //  running the registration menu
    }
}
