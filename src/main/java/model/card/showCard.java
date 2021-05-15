package model.card;

import model.card.enums.ShowCardTags;

public abstract class showCard {


    public static String getHorizontalLine() {
        return ShowCardTags.HORIZONTAL_LINE.getLabel();
    }

    public static String getNameLine(String name) {
        return getString(ShowCardTags.NAME.getLabel(), name, 27) + ShowCardTags.NEXT_LINE.getLabel();
    }

    public static String getDescriptionLine(String description) {
        StringBuilder buildDescription = new StringBuilder(description);
        StringBuilder response = new StringBuilder(ShowCardTags.DESCRIPTION.getLabel() + getSpace(13)
                + ShowCardTags.NEXT_LINE.getLabel());

        while (buildDescription.length() > 0) {
            if (buildDescription.length() < 27) {
                response.append(ShowCardTags.FIRST_CHARACTER.getLabel())
                        .append(buildDescription.substring(0, buildDescription.length() - 1))
                        .append(getSpace(27 - buildDescription.length())).append(ShowCardTags.NEXT_LINE.getLabel());
                break;
            }
            int lastIndexOfSpace = buildDescription.substring(0, 26).lastIndexOf(ShowCardTags.FREE_SPACE.getLabel());
            response.append(ShowCardTags.FIRST_CHARACTER.getLabel())
                    .append(buildDescription.substring(0, lastIndexOfSpace))
                    .append(getSpace(26 - lastIndexOfSpace)).append(ShowCardTags.NEXT_LINE.getLabel());
            buildDescription.replace(0, lastIndexOfSpace + 1, ShowCardTags.NULL.getLabel());
        }
        return response.toString();
    }


    public static String getTypeLine(String type) {
        return getString(ShowCardTags.TYPE.getLabel(), type, 27) + ShowCardTags.NEXT_LINE.getLabel();
    }

    public static String getMonsterTypeLine(String type) {
        return getString(ShowCardTags.MONSTER_TYPE.getLabel(), type, 27) + ShowCardTags.NEXT_LINE.getLabel();
    }

    public static String getPriceLine(int price) {
        return getString(ShowCardTags.PRICE.getLabel(), Integer.toString(price), 27) + ShowCardTags.NEXT_LINE.getLabel();
    }

    public static String getFreeLine() {
        return ShowCardTags.FIRST_CHARACTER.getLabel() + getSpace(26) + ShowCardTags.NEXT_LINE.getLabel();
    }


    public static String getPriceAndLevelLine(int price, int level) {
        return getString(ShowCardTags.PRICE.getLabel(), Integer.toString(price), 13)
                + getString(ShowCardTags.LEVEL.getLabel(), Integer.toString(level), 14)
                + (ShowCardTags.NEXT_LINE.getLabel());

    }

    public static String getAttackAndDefenseLine(int attack, int defense) {
        return getString(ShowCardTags.ATTACK.getLabel(), Integer.toString(attack), 13) +
                getString(ShowCardTags.DEFENSE.getLabel(), Integer.toString(defense), 14) +
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
