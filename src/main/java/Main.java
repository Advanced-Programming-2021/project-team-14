import model.Database;
import model.card.Card;
import view.RegistrationMenu;

public class Main {
    public static void main(String[] args) {
        Database.prepareDatabase();
        new RegistrationMenu().run();   //  running the registration menu
    }
}
