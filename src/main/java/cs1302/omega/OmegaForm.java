package cs1302.omega;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class OmegaForm {
    Rectangle a;
    Rectangle b;
    Rectangle c;
    Rectangle d;
    Color color;
    private String name;
    public int form = 1;
    Text lines ;
    Text leveltext ;
    Text scoretext ;
    Text nextLevelText;

    private void textLabels(){

        this.scoretext = new Text ( "Score: " );
        scoretext.setFont ( Font.font ( "Verdana", 20 ) );

        this.lines = new Text ( "Lines: " );
        lines.setFont ( Font.font ( "Verdana", 20 ) );

        lines.setFill ( Color.GREEN );
        this.leveltext = new Text ( "Level: " );
        leveltext.setFont ( Font.font ( "Time New Roman", 30 ) );

        leveltext.setFill ( Color.FIREBRICK );
        this.nextLevelText = new Text ();
        nextLevelText.setFont ( Font.font ( "Time New Roman", FontWeight.BOLD, 14 ) );

        nextLevelText.setFill ( Color.MEDIUMPURPLE );
    }

    /**
     * @param a
     * @param b
     * @param c
     * @param d
     */
    public OmegaForm(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * @param a
     * @param b
     * @param c
     * @param d
     * @param name
     */
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



    /**
     * @return
     */
    public String getName() {
        return name;
    }


    /**
     *
     */
    public void changeForm() {
        if (form != 4) {
            form++;
        } else {
            form = 1;
        }
    }
}
