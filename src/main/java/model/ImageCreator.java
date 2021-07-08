package model;

import model.card.Card;
import model.card.Monster;
import model.card.SpellTrap;
import model.card.enums.CardType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class ImageCreator {
    //    private static final String createdImageFolder = "Resources\\Images\\createdImages\\";
//    private static final String rawImageFolder = "Resources\\Images\\rawImages\\";
    private static final int MAX_LINE_LENGTH = 45;

    public static void createCardImage(Card card, String path) {
        BufferedImage rawImage = null;
        try {

            if (card.getCardType().equals(CardType.MONSTER)) {
                rawImage = getBufferedImage("2");
                Graphics2D graphics = getGraphics(rawImage);
                drawMonsterCard(graphics, (Monster) card);

            } else {
                rawImage = getBufferedImage("1");
                Graphics2D graphics = getGraphics(rawImage);
                drawSpellAndTrapCard(graphics, (SpellTrap) card);
            }


            saveBufferedImage(path, rawImage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBufferedImage(String path, BufferedImage rawImage) throws IOException {
        File file = new File(path);
        ImageIO.write(rawImage, "png", file);
    }

    public static Graphics2D getGraphics(BufferedImage rawImage) {
        return rawImage.createGraphics();
    }

    public static BufferedImage getBufferedImage(String i) throws IOException {
        BufferedImage rawImage;
        File f = new File("Resources\\CardImages\\" + i + ".png");
        rawImage = ImageIO.read(f);
        return rawImage;
    }


    private static void drawMonsterCard(Graphics2D graphics, Monster card) {
        createRectangle(graphics, Colors.DARK_RED);
//          graphics.setFont(new Font(Fonts.LALEZAR.getFont().getFontName(), Font.TRUETYPE_FONT, 80));
        createCircle(graphics);
        setName(graphics, card.getName());
        setLevel(graphics, String.valueOf(card.getLevel()));
        setAttackAneDefence(graphics, String.valueOf(card.getAttack()), String.valueOf(card.getDefence()));
        setMonsterTypeAndCardType(graphics, card.getMonsterType().getLabel(), card.getCardType().getLabel());

        setDescription(graphics, card.getDescription());
        graphics.dispose();
    }

    public static void setMonsterTypeAndCardType(Graphics2D graphics, String monsterType, String cardType) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.PLAIN, 35));
        graphics.drawString(monsterType + " | " + cardType, 50, 950);
    }

    public static void setAttackAneDefence(Graphics2D graphics, String attack, String defence) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.PLAIN, 35));
        graphics.drawString("attack:" + attack + " | defence:" + defence, 5, 780);
    }

    public static void setLevel(Graphics2D graphics, String level) {
        graphics.setColor(Colors.DARK_GRAY.getColorForGraphic());
        graphics.drawString(level, 770, 820);
    }

    public static void setName(Graphics2D graphics, String name) {
        graphics.setColor(Color.WHITE);
        graphics.drawString(name, 50, 900);
    }

    public static void createCircle(Graphics2D graphics) {
        graphics.setColor(Colors.GOLD.getColorForGraphic());
        graphics.fillOval(730, 730, 130, 130);
    }

    public static void createRectangle(Graphics2D graphics, Colors darkRed) {
        graphics.setColor(darkRed.getColorForGraphic());
        graphics.fillRect(0, 800, 920, 400);
    }

    private static void drawSpellAndTrapCard(Graphics2D graphics, SpellTrap card) {
        createRectangle(graphics, Colors.GREEN);
//          graphics.setFont(new Font(Fonts.LALEZAR.getFont().getFontName(), Font.TRUETYPE_FONT, 80));

        setName(graphics, card.getName());

        setPropertyAndCardType(graphics, card.getProperty().label, card.getCardType().getLabel());

        setDescription(graphics, card.getDescription());
        graphics.dispose();
    }

    public static void setDescription(Graphics2D graphics, String description) {
        graphics.setColor(Colors.LIGHT_GRAY.getColorForGraphic());
        graphics.setFont(new Font("Arial", Font.PLAIN, 35));


        int i = 0;
        for (String s : addLinebreaks(description).split("\n")) {
            graphics.drawString(s, 50, 1020 + i++ * 50);
        }
    }

    public static void setPropertyAndCardType(Graphics2D graphics, String property, String cardType) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.PLAIN, 35));
        graphics.drawString(cardType + " | " + property, 50, 950);
    }


    public static String addLinebreaks(String input) {
        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            if (lineLen + word.length() > MAX_LINE_LENGTH) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word + " ");
            lineLen += word.length();
        }
        return output.toString();
    }
}
