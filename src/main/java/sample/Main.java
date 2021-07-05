package sample;


import model.Database;
import view.RegistrationMenu;


public class Main{

    public static void main(String[] args) {
        Database.prepareDatabase();
        new RegistrationMenu().run();
    }


}
