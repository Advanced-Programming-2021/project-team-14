package model.card;

import model.card.enums.ShowCardTags;

public abstract class showCard {


    public static String getHorizontalLine() {
        return ShowCardTags.HORIZONTAL_LINE.getLabel();
    }

    public static String getNameLine(String name) {
        return getString(ShowCardTags.NAME.getLabel(), name, 37) + ShowCardTags.NEXT_LINE.getLabel();
    }

    public static String getDescriptionLine(String description) {
        StringBuilder buildDescription = new StringBuilder(description);
        StringBuilder response = new StringBuilder(ShowCardTags.DESCRIPTION.getLabel() + getSpace(23)
                + ShowCardTags.NEXT_LINE.getLabel());

        while (buildDescription.length() > 0) {
            if (buildDescription.length() < 37) {
                response.append(ShowCardTags.FIRST_CHARACTER.getLabel())
                        .append(buildDescription.substring(0, buildDescription.length() - 1))
                        .append(getSpace(37 - buildDescription.length())).append(ShowCardTags.NEXT_LINE.getLabel());
                break;
            }
            int lastIndexOfSpace = buildDescription.substring(0, 36).lastIndexOf(ShowCardTags.FREE_SPACE.getLabel());
            response.append(ShowCardTags.FIRST_CHARACTER.getLabel())
                    .append(buildDescription.substring(0, lastIndexOfSpace))
                    .append(getSpace(36 - lastIndexOfSpace)).append(ShowCardTags.NEXT_LINE.getLabel());
            buildDescription.replace(0, lastIndexOfSpace + 1, ShowCardTags.NULL.getLabel());
        }
        return response.toString();
    }


    public static String getDescriptionGraphic(String description) {

        StringBuilder stringBuilder = new StringBuilder(description);

        int i = 0;
        while ((i = stringBuilder.indexOf(" ", i + 30)) != -1) {
            stringBuilder.replace(i, i + 1, "\n");
        }
        return stringBuilder.toString();
    }


    public static String getTypeLine(String type) {
        return getString(ShowCardTags.TYPE.getLabel(), type, 37) + ShowCardTags.NEXT_LINE.getLabel();
    }

    public static String getMonsterTypeLine(String type) {
        return getString(ShowCardTags.MONSTER_TYPE.getLabel(), type, 37) + ShowCardTags.NEXT_LINE.getLabel();
    }

    public static String getPriceLine(int price) {
        return getString(ShowCardTags.PRICE.getLabel(), Integer.toString(price), 37) + ShowCardTags.NEXT_LINE.getLabel();
    }

    public static String getFreeLine() {
        return ShowCardTags.FIRST_CHARACTER.getLabel() + getSpace(36) + ShowCardTags.NEXT_LINE.getLabel();
    }


    public static String getPriceAndLevelLine(int price, int level) {
        return getString(ShowCardTags.PRICE.getLabel(), Integer.toString(price), 18)
                + getString(ShowCardTags.LEVEL.getLabel(), Integer.toString(level),19)
                + (ShowCardTags.NEXT_LINE.getLabel());

    }

    public static String getAttackAndDefenseLine(int attack, int defense) {
        return getString(ShowCardTags.ATTACK.getLabel(), Integer.toString(attack), 18) +
                getString(ShowCardTags.DEFENSE.getLabel(), Integer.toString(defense), 19) +
                (ShowCardTags.NEXT_LINE.getLabel());

    }


    private static String getString(String tag, String value, int m) {
        StringBuilder string = new StringBuilder(tag + value);
        string.append(getSpace(m - string.length()));
        return string.toString();
    }

    private static String getSpace(int n) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < n; i++) {
            string.append(ShowCardTags.FREE_SPACE.getLabel());
        }
        return string.toString();
    }


}
