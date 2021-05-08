package cs1302.omega;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OmegaForm {
    Rectangle a;
    Rectangle b;
    Rectangle c;
    Rectangle d;
    Color color;
    private String name;
    public int form = 1;

    public OmegaForm(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public OmegaForm(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;

        if ( name == null) {
            return;
        }
        else if ( name == "J") {
            color = Color.GRAY;
        }
        else if ( name == "L") {
            color = Color.GOLD;
        }
        else if ( name == "O") {
            color = Color.RED;
        }
        else if ( name == "S") {
            color = Color.GREEN;
        }
        else if ( name == "T") {
            color = Color.BLUE;
        }
        else if ( name == "Z") {
            color = Color.PINK;
        }
        else if ( name == "I") {
            color = Color.BROWN;
        }
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);
    }


    public String getName() {
        return name;
    }


    public void changeForm() {
        if (form != 4) {
            form++;
        } else {
            form = 1;
        }
    }
}
