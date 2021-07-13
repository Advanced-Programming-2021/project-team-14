package graphic.component;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Bubble extends AnchorPane {


    private int id;
    private String text;
    private int p = 12;
    private int s = 2;
    private int pm = 10;
    private int sm = 1;
    private Paint textColor = Color.WHITE;

    private Paint metaColor = Color.LIGHTGRAY;

    private Rectangle r;

    private int edgeRadius = 15;
    private boolean isMine;
    private String meta;


    public Bubble(String text, boolean isMine, boolean edited, String time, int id) {
        super();
        this.text = text;
        this.isMine = isMine;
        this.meta = (edited ? "edited " : "") + time;
        this.id = id;
        init();
    }

    public int getMessageId() {
        return id;
    }

    private void init() {
        int y = 0;
        // temp for text
        Text temp = new Text(text);
        int textW = (int) temp.getLayoutBounds().getWidth();
        int textH = (int) temp.getLayoutBounds().getHeight();
        int w = textW + p * 2 + s * 2;
        int h = textH + p * 2;

        // tmp for meta
        Text tmp = new Text(meta);
        int metaW = (int) tmp.getLayoutBounds().getWidth();
        int metaH = (int) tmp.getLayoutBounds().getHeight();
        h += metaH;


        int x = isMine ? 530 - textW : 40;

        // label text
        Label l = new Label(text);
        l.setTextFill(textColor);
        l.setTranslateX(x + p + s);
        l.setTranslateY(y + p);

        // label meta
        Label m = new Label(meta);
        m.setTextFill(metaColor);
        m.setTranslateX(x + (w - (metaW + pm + sm)));
        m.setTranslateY(y + textH + pm * 2);

        // bubble
        r = new Rectangle();
        r.setTranslateX(x);
        r.setTranslateY(y);

        r.setWidth(w);
        r.setHeight(h);

        r.setArcHeight(this.edgeRadius);
        r.setArcWidth(this.edgeRadius);

        r.setFill((isMine ? Colors.THEME_COLOR : Colors.THEME_COLOR_LOWER).getColor());
        r.setStroke(Colors.THEME_COLOR.getColor());


        getChildren().addAll(r, l, m);
        if (!isMine) {
            Circle circle = new Circle(15, Color.TRANSPARENT);
            circle.setStroke(Colors.THEME_COLOR.getColor());
            circle.setTranslateX(10);
            circle.setTranslateY(10);
            getChildren().add(circle);
        }

    }

    public void update(String text) {
        System.out.println("updating");
        getChildren().remove(0, getChildren().size() - 1);
        this.text = text;
        init();
    }

    public String getText() {
        return text;
    }

}
