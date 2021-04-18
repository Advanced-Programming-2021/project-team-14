package view;


import view.enums.Menus;

public abstract class Menu {

    String command, currentMenu;

    public void setCurrentMenu(Menus currentMenu) {
        this.currentMenu = currentMenu.getLabel();
        Request.addData("view", currentMenu.getLabel());
    }


}
