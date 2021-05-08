package cs1302.omega;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  The main class for the game Tetris.
 */
public class OmegaApp extends Application {
    // The variables
    public static final int MOVE = 30;
    public static final int SIZE = 30;
    public static int XMAX = SIZE * 12;
    public static int YMAX = SIZE * 24;
    public static int[][] BOARD = new int[XMAX / SIZE][YMAX / SIZE];
    public static int score = 0;
    private static Pane group = new Pane ();
    private static OmegaForm object;
    private static Scene scene = new Scene ( group, XMAX + 150, YMAX );
    private static int endLine = 0;
    private static boolean game = true;
    private static OmegaForm nextObj = OmegaController.makeRect ();
    private static int linesNo = 0;
    private static int level = 0;
    private static int nextlevel = 0;

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch ( args );
    }

    /**
     * The stage and beginning of the game.
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        for (int[] a : BOARD) {
            Arrays.fill ( a, 0 );
        }
        BorderPane gameBoard = new BorderPane ();
        Line menu = new Line ( XMAX, 5, XMAX, YMAX );
        Text scoretext = new Text ( "Score: " );
        scoretext.setFont ( Font.font ( "Verdana", 20 ) );
        scoretext.setY ( 50 );
        scoretext.setX ( XMAX + 5 );
        Text lines = new Text ( "Lines: " );
        lines.setFont ( Font.font ( "Verdana", 20 ) );
        lines.setY ( 100 );
        lines.setX ( XMAX + 5 );
        lines.setFill ( Color.GREEN );
        Text leveltext = new Text ( "Level: " );
        leveltext.setFont ( Font.font ( "Time New Roman", 30 ) );
        leveltext.setY ( 450 );
        leveltext.setX ( XMAX + 5 );
        leveltext.setFill ( Color.FIREBRICK );
        Text nextLevelText = new Text ();
        nextLevelText.setFont ( Font.font ( "Time New Roman", FontWeight.BOLD, 14 ) );
        nextLevelText.setY ( 150 );
        nextLevelText.setX ( XMAX + 5 );
        nextLevelText.setFill ( Color.MEDIUMPURPLE );

        gameBoard.getChildren ().addAll ( scoretext, menu, lines, leveltext, nextLevelText );
        OmegaForm a = nextObj;
        gameBoard.getChildren ().addAll ( a.a, a.b, a.c, a.d );
        group.getChildren ().add ( gameBoard );
        moveOnKeyPress ( a );
        object = a;
        nextObj = OmegaController.makeRect ();
        stage.setScene ( scene );
        stage.setTitle ( "Tetris Game" );
        stage.show ();

        Timer fall = new Timer ();
        TimerTask task = new TimerTask () {
            public void run() {
                Platform.runLater ( () -> {
                    if (object.a.getY () == 0 || object.b.getY () == 0 || object.c.getY () == 0
                            || object.d.getY () == 0)
                        endLine++;
                    else {
                        endLine = 0;
                    }
                    if (endLine == 2) {
                        // GAME OVER
                        Text over = new Text ( "GAME OVER" );
                        over.setFill ( Color.RED );
                        over.setFont ( Font.font ( "Verdana", 45 ) );
                        over.setY ( 250 );
                        over.setX ( 10 );
                        game = false;
                        group.getChildren ().addAll ( over );
                    }
                    if (game) {
                        gravity ( object );
                        scoretext.setText ( "Score: " + score );
                        lines.setText ( "Lines: " + linesNo );
                        leveltext.setText ( "Level: " + level );
                        nextLevelText.setText ( "Next level in " + (nextlevel - linesNo) + " line" );
                    }
                } );
            }
        };
        if (level <= 15 && level >= 5) {
            fall.schedule ( task, 0, 350 );
            nextlevel += 15;
            level++;
        } else if (level > 15 && level <= 20) {
            fall.schedule ( task, 0, 300 );
            nextlevel += 20;
            level++;
        } else if (level > 20 && level <= 40) {
            fall.schedule ( task, 0, 250 );
            nextlevel += 40;
            level++;
        } else if (level > 40 && level <= 100) {
            fall.schedule ( task, 0, 150 );
            nextlevel += 100;
            level++;
        } else {
            fall.schedule ( task, 0, 400 );
            nextlevel += 5;
            level++;
        }
    }

    /**
     * @param form
     */
    private void moveOnKeyPress(OmegaForm form) {
        scene.setOnKeyPressed ( event -> {
            switch (event.getCode ()) {
                case RIGHT:
                    OmegaController.MoveRight ( form );
                    break;
                case DOWN:
                    gravity ( form );
                    score++;
                    break;
                case LEFT:
                    OmegaController.MoveLeft ( form );
                    break;
                case UP:
                    moveTurn ( form );
                    break;

            }
        } );
    }

    /**
     * @param form
     */
    private void moveTurn(OmegaForm form) {
        int f = form.form;
        Rectangle a = form.a;
        Rectangle b = form.b;
        Rectangle c = form.c;
        Rectangle d = form.d;
        switch (form.getName ()) {
            case "J":
                if (f == 1 && clearB ( a, 1, -1 ) && clearB ( c, -1, -1 ) && clearB ( d, -2, -2 )) {
                    moveRight ( form.a );
                    moveDown ( form.a );
                    moveDown ( form.c );
                    moveLeft ( form.c );
                    moveDown ( form.d );
                    moveDown ( form.d );
                    moveLeft ( form.d );
                    moveLeft ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 2 && clearB ( a, -1, -1 ) && clearB ( c, -1, 1 ) && clearB ( d, -2, 2 )) {
                    moveDown ( form.a );
                    moveLeft ( form.a );
                    moveLeft ( form.c );
                    moveUp ( form.c );
                    moveLeft ( form.d );
                    moveLeft ( form.d );
                    moveUp ( form.d );
                    moveUp ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 3 && clearB ( a, -1, 1 ) && clearB ( c, 1, 1 ) && clearB ( d, 2, 2 )) {
                    moveLeft ( form.a );
                    moveUp ( form.a );
                    moveUp ( form.c );
                    moveRight ( form.c );
                    moveUp ( form.d );
                    moveUp ( form.d );
                    moveRight ( form.d );
                    moveRight ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 4 && clearB ( a, 1, 1 ) && clearB ( c, 1, -1 ) && clearB ( d, 2, -2 )) {
                    moveUp ( form.a );
                    moveRight ( form.a );
                    moveRight ( form.c );
                    moveDown ( form.c );
                    moveRight ( form.d );
                    moveRight ( form.d );
                    moveDown ( form.d );
                    moveDown ( form.d );
                    form.changeForm ();
                    break;
                }
                break;
            case "L":
                if (f == 1 && clearB ( a, 1, -1 ) && clearB ( c, 1, 1 ) && clearB ( b, 2, 2 )) {
                    moveRight ( form.a );
                    moveDown ( form.a );
                    moveUp ( form.c );
                    moveRight ( form.c );
                    moveUp ( form.b );
                    moveUp ( form.b );
                    moveRight ( form.b );
                    moveRight ( form.b );
                    form.changeForm ();
                    break;
                }
                if (f == 2 && clearB ( a, -1, -1 ) && clearB ( b, 2, -2 ) && clearB ( c, 1, -1 )) {
                    moveDown ( form.a );
                    moveLeft ( form.a );
                    moveRight ( form.b );
                    moveRight ( form.b );
                    moveDown ( form.b );
                    moveDown ( form.b );
                    moveRight ( form.c );
                    moveDown ( form.c );
                    form.changeForm ();
                    break;
                }
                if (f == 3 && clearB ( a, -1, 1 ) && clearB ( c, -1, -1 ) && clearB ( b, -2, -2 )) {
                    moveLeft ( form.a );
                    moveUp ( form.a );
                    moveDown ( form.c );
                    moveLeft ( form.c );
                    moveDown ( form.b );
                    moveDown ( form.b );
                    moveLeft ( form.b );
                    moveLeft ( form.b );
                    form.changeForm ();
                    break;
                }
                if (f == 4 && clearB ( a, 1, 1 ) && clearB ( b, -2, 2 ) && clearB ( c, -1, 1 )) {
                    moveUp ( form.a );
                    moveRight ( form.a );
                    moveLeft ( form.b );
                    moveLeft ( form.b );
                    moveUp ( form.b );
                    moveUp ( form.b );
                    moveLeft ( form.c );
                    moveUp ( form.c );
                    form.changeForm ();
                    break;
                }
                break;
            case "O":
                break;
            case "S":
                if (f == 1 && clearB ( a, -1, -1 ) && clearB ( c, -1, 1 ) && clearB ( d, 0, 2 )) {
                    moveDown ( form.a );
                    moveLeft ( form.a );
                    moveLeft ( form.c );
                    moveUp ( form.c );
                    moveUp ( form.d );
                    moveUp ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 2 && clearB ( a, 1, 1 ) && clearB ( c, 1, -1 ) && clearB ( d, 0, -2 )) {
                    moveUp ( form.a );
                    moveRight ( form.a );
                    moveRight ( form.c );
                    moveDown ( form.c );
                    moveDown ( form.d );
                    moveDown ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 3 && clearB ( a, -1, -1 ) && clearB ( c, -1, 1 ) && clearB ( d, 0, 2 )) {
                    moveDown ( form.a );
                    moveLeft ( form.a );
                    moveLeft ( form.c );
                    moveUp ( form.c );
                    moveUp ( form.d );
                    moveUp ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 4 && clearB ( a, 1, 1 ) && clearB ( c, 1, -1 ) && clearB ( d, 0, -2 )) {
                    moveUp ( form.a );
                    moveRight ( form.a );
                    moveRight ( form.c );
                    moveDown ( form.c );
                    moveDown ( form.d );
                    moveDown ( form.d );
                    form.changeForm ();
                    break;
                }
                break;
            case "T":
                if (f == 1 && clearB ( a, 1, 1 ) && clearB ( d, -1, -1 ) && clearB ( c, -1, 1 )) {
                    moveUp ( form.a );
                    moveRight ( form.a );
                    moveDown ( form.d );
                    moveLeft ( form.d );
                    moveLeft ( form.c );
                    moveUp ( form.c );
                    form.changeForm ();
                    break;
                }
                if (f == 2 && clearB ( a, 1, -1 ) && clearB ( d, -1, 1 ) && clearB ( c, 1, 1 )) {
                    moveRight ( form.a );
                    moveDown ( form.a );
                    moveLeft ( form.d );
                    moveUp ( form.d );
                    moveUp ( form.c );
                    moveRight ( form.c );
                    form.changeForm ();
                    break;
                }
                if (f == 3 && clearB ( a, -1, -1 ) && clearB ( d, 1, 1 ) && clearB ( c, 1, -1 )) {
                    moveDown ( form.a );
                    moveLeft ( form.a );
                    moveUp ( form.d );
                    moveRight ( form.d );
                    moveRight ( form.c );
                    moveDown ( form.c );
                    form.changeForm ();
                    break;
                }
                if (f == 4 && clearB ( a, -1, 1 ) && clearB ( d, 1, -1 ) && clearB ( c, -1, -1 )) {
                    moveLeft ( form.a );
                    moveUp ( form.a );
                    moveRight ( form.d );
                    moveDown ( form.d );
                    moveDown ( form.c );
                    moveLeft ( form.c );
                    form.changeForm ();
                    break;
                }
                break;
            case "Z":
                if (f == 1 && clearB ( b, 1, 1 ) && clearB ( c, -1, 1 ) && clearB ( d, -2, 0 )) {
                    moveUp ( form.b );
                    moveRight ( form.b );
                    moveLeft ( form.c );
                    moveUp ( form.c );
                    moveLeft ( form.d );
                    moveLeft ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 2 && clearB ( b, -1, -1 ) && clearB ( c, 1, -1 ) && clearB ( d, 2, 0 )) {
                    moveDown ( form.b );
                    moveLeft ( form.b );
                    moveRight ( form.c );
                    moveDown ( form.c );
                    moveRight ( form.d );
                    moveRight ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 3 && clearB ( b, 1, 1 ) && clearB ( c, -1, 1 ) && clearB ( d, -2, 0 )) {
                    moveUp ( form.b );
                    moveRight ( form.b );
                    moveLeft ( form.c );
                    moveUp ( form.c );
                    moveLeft ( form.d );
                    moveLeft ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 4 && clearB ( b, -1, -1 ) && clearB ( c, 1, -1 ) && clearB ( d, 2, 0 )) {
                    moveDown ( form.b );
                    moveLeft ( form.b );
                    moveRight ( form.c );
                    moveDown ( form.c );
                    moveRight ( form.d );
                    moveRight ( form.d );
                    form.changeForm ();
                    break;
                }
                break;
            case "I":
                if (f == 1 && clearB ( a, 2, 2 ) && clearB ( b, 1, 1 ) && clearB ( d, -1, -1 )) {
                    moveUp ( form.a );
                    moveUp ( form.a );
                    moveRight ( form.a );
                    moveRight ( form.a );
                    moveUp ( form.b );
                    moveRight ( form.b );
                    moveDown ( form.d );
                    moveLeft ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 2 && clearB ( a, -2, -2 ) && clearB ( b, -1, -1 ) && clearB ( d, 1, 1 )) {
                    moveDown ( form.a );
                    moveDown ( form.a );
                    moveLeft ( form.a );
                    moveLeft ( form.a );
                    moveDown ( form.b );
                    moveLeft ( form.b );
                    moveUp ( form.d );
                    moveRight ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 3 && clearB ( a, 2, 2 ) && clearB ( b, 1, 1 ) && clearB ( d, -1, -1 )) {
                    moveUp ( form.a );
                    moveUp ( form.a );
                    moveRight ( form.a );
                    moveRight ( form.a );
                    moveUp ( form.b );
                    moveRight ( form.b );
                    moveDown ( form.d );
                    moveLeft ( form.d );
                    form.changeForm ();
                    break;
                }
                if (f == 4 && clearB ( a, -2, -2 ) && clearB ( b, -1, -1 ) && clearB ( d, 1, 1 )) {
                    moveDown ( form.a );
                    moveDown ( form.a );
                    moveLeft ( form.a );
                    moveLeft ( form.a );
                    moveDown ( form.b );
                    moveLeft ( form.b );
                    moveUp ( form.d );
                    moveRight ( form.d );
                    form.changeForm ();
                    break;
                }
                break;
        }
    }

    /**
     * @param pane
     */
    private void removeRows(Pane pane) {
        ArrayList<Node> shape = new ArrayList<> ();
        ArrayList<Integer> lines = new ArrayList<> ();
        ArrayList<Node> newShape = new ArrayList<> ();
        int full = 0;
        for (int i = 0; i < BOARD[0].length; i++) {
            for (int j = 0; j < BOARD.length; j++) {
                if (BOARD[j][i] == 1)
                    full++;
            }
            if (full == BOARD.length)
                lines.add ( i );
            //lines.add(i + lines.size());
            full = 0;
        }
        if (lines.size () > 0)
            do {
                for (Node node : pane.getChildren ()) {
                    if (node instanceof Rectangle)
                        shape.add ( node );
                }
                score += 100;
                linesNo++;


                for (Node node : shape) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY () == lines.get ( 0 ) * SIZE) {
                        BOARD[(int) a.getX () / SIZE][(int) a.getY () / SIZE] = 0;
                        pane.getChildren ().remove ( node );
                    } else
                        newShape.add ( node );
                }

                for (Node node : newShape) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY () < lines.get ( 0 ) * SIZE) {
                        BOARD[(int) a.getX () / SIZE][(int) a.getY () / SIZE] = 0;
                        a.setY ( a.getY () + SIZE );
                    }
                }
                lines.remove ( 0 );
                shape.clear ();
                newShape.clear ();
                for (Node node : pane.getChildren ()) {
                    if (node instanceof Rectangle)
                        shape.add ( node );
                }
                for (Node node : shape) {
                    Rectangle a = (Rectangle) node;
                    try {
                        BOARD[(int) a.getX () / SIZE][(int) a.getY () / SIZE] = 1;
                    } catch ( ArrayIndexOutOfBoundsException e ) {
                    }
                }
                shape.clear ();
            } while (lines.size () > 0);
    }

    /**
     * @param rect
     */
    private void moveDown(Rectangle rect) {
        if (rect.getY () + MOVE < YMAX)
            rect.setY ( rect.getY () + MOVE );

    }

    /**
     * @param rect
     */
    private void moveRight(Rectangle rect) {
        if (rect.getX () + MOVE <= XMAX - SIZE)
            rect.setX ( rect.getX () + MOVE );
    }

    /**
     * @param rect
     */
    private void moveLeft(Rectangle rect) {
        if (rect.getX () - MOVE >= 0)
            rect.setX ( rect.getX () - MOVE );
    }

    /**
     * @param rect
     */
    private void moveUp(Rectangle rect) {
        if (rect.getY () - MOVE > 0)
            rect.setY ( rect.getY () - MOVE );
    }

    /**
     * @param form
     */
    private void gravity(OmegaForm form) {
        if (form.a.getY () == YMAX - SIZE || form.b.getY () == YMAX - SIZE || form.c.getY () == YMAX - SIZE
                || form.d.getY () == YMAX - SIZE || moveA ( form ) || moveB ( form ) || moveC ( form ) || moveD ( form )) {
            BOARD[(int) form.a.getX () / SIZE][(int) form.a.getY () / SIZE] = 1;
            BOARD[(int) form.b.getX () / SIZE][(int) form.b.getY () / SIZE] = 1;
            BOARD[(int) form.c.getX () / SIZE][(int) form.c.getY () / SIZE] = 1;
            BOARD[(int) form.d.getX () / SIZE][(int) form.d.getY () / SIZE] = 1;
            removeRows ( group );

            OmegaForm a = nextObj;
            nextObj = OmegaController.makeRect ();
            object = a;
            group.getChildren ().addAll ( a.a, a.b, a.c, a.d );
            moveOnKeyPress ( a );
        }

        if (form.a.getY () + MOVE < YMAX && form.b.getY () + MOVE < YMAX && form.c.getY () + MOVE < YMAX
                && form.d.getY () + MOVE < YMAX) {
            int movea = BOARD[(int) form.a.getX () / SIZE][((int) form.a.getY () / SIZE) + 1];
            int moveb = BOARD[(int) form.b.getX () / SIZE][((int) form.b.getY () / SIZE) + 1];
            int movec = BOARD[(int) form.c.getX () / SIZE][((int) form.c.getY () / SIZE) + 1];
            int moved = BOARD[(int) form.d.getX () / SIZE][((int) form.d.getY () / SIZE) + 1];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.a.setY ( form.a.getY () + MOVE );
                form.b.setY ( form.b.getY () + MOVE );
                form.c.setY ( form.c.getY () + MOVE );
                form.d.setY ( form.d.getY () + MOVE );
            }
        }
    }

    /**
     * @param form
     * @return
     */
    private boolean moveA(OmegaForm form) {
        return (BOARD[(int) form.a.getX () / SIZE][((int) form.a.getY () / SIZE) + 1] == 1);
    }

    /**
     * @param form
     * @return
     */
    private boolean moveB(OmegaForm form) {
        return (BOARD[(int) form.b.getX () / SIZE][((int) form.b.getY () / SIZE) + 1] == 1);
    }

    /**
     * @param form
     * @return
     */
    private boolean moveC(OmegaForm form) {
        return (BOARD[(int) form.c.getX () / SIZE][((int) form.c.getY () / SIZE) + 1] == 1);
    }

    /**
     * @param form
     * @return
     */
    private boolean moveD(OmegaForm form) {
        return (BOARD[(int) form.d.getX () / SIZE][((int) form.d.getY () / SIZE) + 1] == 1);
    }

    /**
     * @param rect
     * @param x
     * @param y
     * @return
     */

    private boolean clearB(Rectangle rect, int x, int y) {
        boolean xblock = false;
        boolean yblock = false;
        if (x >= 0) {
            xblock = rect.getX () + x * MOVE <= XMAX - SIZE;
        }
        if (x < 0) {
            xblock = rect.getX () + x * MOVE >= 0;
        }
        if (y >= 0) {
            yblock = rect.getY () - y * MOVE > 0;
        }
        if (y < 0) {
            yblock = rect.getY () + y * MOVE < YMAX;
        }
        return xblock && yblock && BOARD[((int) rect.getX () / SIZE) + x][((int) rect.getY () / SIZE) - y] == 0;
    }

}
