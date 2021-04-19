package view;


import view.enums.Menus;

public abstract class Menu {

    protected String command, currentMenu, response;

    public void setCurrentMenu(Menus currentMenu) {
        this.currentMenu = currentMenu.getLabel();
        Request.addData("view", currentMenu.getLabel());
    }


}
