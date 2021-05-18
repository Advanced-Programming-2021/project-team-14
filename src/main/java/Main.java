import model.Database;
import view.RegistrationMenu;

public class Main {
    public static void main(String[] args) {
        Database.prepareDatabase();
//        System.out.println("\u001B[36m" + new Game(User.getUserByName("mainUser"), User.getUserByName("rivalUser"), 1).getBoard() + "\033[0m"); //testing board printing
        Database.readDataLineByLine("Resources\\Cards\\SpellTrap.csv");
        Database.readDataLineByLine("Resources\\Cards\\Monster.csv");
        new RegistrationMenu().run();   //  running the registration menu
    }
}

