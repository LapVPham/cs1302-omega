package omega;
import javafx.scene.shape.Rectangle;

public class OmegaController {
    // Getting the numbers and the MESH from Tetris
    public static final int MOVE = OmegaApp.MOVE;
    public static final int SIZE = OmegaApp.SIZE;
    public static int XMAX = OmegaApp.XMAX;
    public static int[][] BOARD  = OmegaApp.BOARD ;

    /**
     * @param form
     */
    public static void MoveRight(OmegaForm form) {
        if (form.a.getX() + MOVE <= XMAX - SIZE && form.b.getX() + MOVE <= XMAX - SIZE
                && form.c.getX() + MOVE <= XMAX - SIZE && form.d.getX() + MOVE <= XMAX - SIZE) {
            int moveA = BOARD [((int) form.a.getX() / SIZE) + 1][((int) form.a.getY() / SIZE)];
            int moveB = BOARD [((int) form.b.getX() / SIZE) + 1][((int) form.b.getY() / SIZE)];
            int moveC = BOARD [((int) form.c.getX() / SIZE) + 1][((int) form.c.getY() / SIZE)];
            int moveD = BOARD [((int) form.d.getX() / SIZE) + 1][((int) form.d.getY() / SIZE)];
            if (moveA == 0 && moveA == moveB && moveB == moveC && moveC == moveD) {
                form.a.setX(form.a.getX() + MOVE);
                form.b.setX(form.b.getX() + MOVE);
                form.c.setX(form.c.getX() + MOVE);
                form.d.setX(form.d.getX() + MOVE);
            }
        }
    }

    /**
     * @param form
     */
    public static void MoveLeft(OmegaForm form) {
        if (form.a.getX() - MOVE >= 0 && form.b.getX() - MOVE >= 0 && form.c.getX() - MOVE >= 0
                && form.d.getX() - MOVE >= 0) {
            int moveA = BOARD [((int) form.a.getX() / SIZE) - 1][((int) form.a.getY() / SIZE)];
            int moveB = BOARD [((int) form.b.getX() / SIZE) - 1][((int) form.b.getY() / SIZE)];
            int moveC = BOARD [((int) form.c.getX() / SIZE) - 1][((int) form.c.getY() / SIZE)];
            int moveD = BOARD [((int) form.d.getX() / SIZE) - 1][((int) form.d.getY() / SIZE)];
            if (moveA == 0 && moveA == moveB && moveB == moveC && moveC == moveD) {
                form.a.setX(form.a.getX() - MOVE);
                form.b.setX(form.b.getX() - MOVE);
                form.c.setX(form.c.getX() - MOVE);
                form.d.setX(form.d.getX() - MOVE);
            }
        }
    }

    /**
     * @return
     */
    public static OmegaForm makeRect() {
        int block = (int) (Math.random() * 100);
        String name;
        Rectangle a = new Rectangle(SIZE-1, SIZE-1),
                b = new Rectangle(SIZE-1, SIZE-1),
                c = new Rectangle(SIZE-1, SIZE-1),
                d = new Rectangle(SIZE-1, SIZE-1);
        if (block < 15) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE);
            name = "J";
        } else if (block < 30) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE);
            name = "L";
        } else if (block < 45) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2 - SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2);
            d.setY(SIZE);
            name = "O";
        } else if (block < 60) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 - SIZE);
            d.setY(SIZE);
            name = "S";
        } else if (block < 75) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            name = "T";
        } else if (block < 90) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2 + SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE + SIZE);
            d.setY(SIZE);
            name = "Z";
        } else {
            a.setX(XMAX / 2 - SIZE - SIZE);
            b.setX(XMAX / 2 - SIZE);
            c.setX(XMAX / 2);
            d.setX(XMAX / 2 + SIZE);
            name = "I";
        }
        return new OmegaForm(a, b, c, d, name);
    }

}
