package model.card;

public abstract class showCard {

    public static String getHorizontalLine() {
        return "----------------------------\n";
    }

    public static String getNameLine(String name) {
        return getString("|name: ", name, 27) + "|\n";
    }

    public static String getDescriptionLine(String description) {
        StringBuilder buildDescription = new StringBuilder(description);
        StringBuilder response = new StringBuilder("|description: " + getSpace(13) + "|\n");

        while (buildDescription.length() > 0) {
            if (buildDescription.length() < 27) {
                response.append("|").append(buildDescription.substring(0, buildDescription.length() - 1))
                        .append(getSpace(27 - buildDescription.length())).append("|\n");
                break;
            }
            int lastIndexOfSpace = buildDescription.substring(0, 26).lastIndexOf(" ");
            response.append("|").append(buildDescription.substring(0, lastIndexOfSpace))
                    .append(getSpace(26 - lastIndexOfSpace)).append("|\n");
            buildDescription.replace(0, lastIndexOfSpace + 1, "");
        }
        return response.toString();
    }


    public static String getTypeLine(String type) {
        return getString("|type: ", type, 27) + "|\n";
    }

    public static String getMonsterTypeLine(String type) {
        return getString("|monster type: ", type, 27) + "|\n";
    }

    public static String getPriceLine(int price) {
        return getString("|price: ", Integer.toString(price), 27) + "|\n";
    }

    public static String getFreeLine() {
        return "|" + getSpace(26) + "|\n";
    }


    public static String getPriceAndLevelLine(int price, int level) {
        return getString("|price: ", Integer.toString(price), 13) +
                getString("|level: ", Integer.toString(level), 14) + ("|\n");

    }

    public static String getAttackAndDefenseLine(int attack, int defense) {
        return getString("|attack: ", Integer.toString(attack), 13) +
                getString("|defense: ", Integer.toString(defense), 14) + ("|\n");

    }


    private static String getString(String tag, String value, int m) {
        StringBuilder string = new StringBuilder(tag + value);
        string.append(getSpace(m - string.length()));
        return string.toString();
    }

    private static String getSpace(int n) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < n; i++) {
            string.append(" ");
        }
        return string.toString();
    }


}
