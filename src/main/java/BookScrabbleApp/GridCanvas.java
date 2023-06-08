package BookScrabbleApp;

import BookScrabbleApp.View.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import java.util.*;

public class GridCanvas extends Canvas {
    public List<List<TileField>> tileFields;
    private int col;
    private int row;
    char[][] colorBoard = {
            {'r', '0', '0', 'p', '0', '0', '0', 'r', '0', '0', '0', 'p', '0', '0', 'r'},
            {'0', 'y', '0', '0', '0', 'b', '0', '0', '0', 'b', '0', '0', '0', 'y', '0'},
            {'0', '0', 'y', '0', '0', '0', 'p', '0', 'p', '0', '0', '0', 'y', '0', '0'},
            {'p', '0', '0', 'y', '0', '0', '0', 'p', '0', '0', '0', 'y', '0', '0', 'p'},
            {'0', '0', '0', '0', 'y', '0', '0', '0', '0', '0', 'y', '0', '0', '0', '0'},
            {'0', 'b', '0', '0', '0', 'b', '0', '0', '0', 'b', '0', '0', '0', 'b', '0'},
            {'0', '0', 'p', '0', '0', '0', 'p', '0', 'p', '0', '0', '0', 'p', '0', '0'},
            {'r', '0', '0', 'p', '0', '0', '0', 's', '0', '0', '0', 'p', '0', '0', 'r'},
            {'0', '0', 'p', '0', '0', '0', 'p', '0', 'p', '0', '0', '0', 'p', '0', '0'},
            {'0', 'b', '0', '0', '0', 'b', '0', '0', '0', 'b', '0', '0', '0', 'b', '0'},
            {'0', '0', '0', '0', 'y', '0', '0', '0', '0', '0', 'y', '0', '0', '0', '0'},
            {'p', '0', '0', 'y', '0', '0', '0', 'p', '0', '0', '0', 'y', '0', '0', 'p'},
            {'0', '0', 'y', '0', '0', '0', 'p', '0', 'p', '0', '0', '0', 'y', '0', '0'},
            {'0', 'y', '0', '0', '0', 'b', '0', '0', '0', 'b', '0', '0', '0', 'y', '0'},
            {'r', '0', '0', 'p', '0', '0', '0', 'r', '0', '0', '0', 'p', '0', '0', 'r'}
    };

    public GridCanvas() {
        super();
        this.tileFields = new ArrayList<>();
        this.getStyleClass().add("grid-canvas");
        col = 0;
        row = 0;
    }

    public void setTileFields(List<List<TileField>> tileFields) {
        this.tileFields = tileFields;
        redraw();
    }

    public void placeTileFiled(TileField tile,int tileRow, int tileCol) {
        if(tile != null)
            tile.draw(getGraphicsContext2D(), tileRow, tileCol, this.getWidth()/15, this.getHeight()/15,15);
    }

    public void redraw() {
        //if (tileFields != null) {
        double W = getWidth();
        double H = getHeight();
        double w = W / colorBoard[0].length;
        double h = H / colorBoard.length;

        GraphicsContext gc = getGraphicsContext2D();

        gc.clearRect(0, 0, W, H);

        for (int boardRow = 0; boardRow < 15; boardRow++) {
            for (int boardCol = 0; boardCol < 15; boardCol++) {
                if (tileFields.get(boardRow).get(boardCol).letter.getText().equals("")) {
                    gc.setFill(getColorForScore(colorBoard[boardRow][boardCol]));
                    gc.fillRect(boardRow * w , boardCol * h, w, h);
                    gc.fillText(String.valueOf(colorBoard[boardRow][boardCol]), boardRow * w + w / 2, boardCol * h + h / 2);
                } else{
                    tileFields.get(boardRow).get(boardCol).setLocked();
                    this.placeTileFiled(tileFields.get(boardRow).get(boardCol),boardRow,boardCol);
                }
            }
        }
        gc.setFill(Color.rgb(248, 0, 0,0.6));
        gc.fillRect(row * w, col * h, w, h);
        // }
    }

    private Color getColorForScore(char score) {
        // Assign colors based on the score characters
        return switch (score) {
            case 'r' -> Color.rgb(174, 204, 228); // pale blue triple word score
            case 'p' -> Color.rgb(128, 0, 128); // pale blue double letter score
            case 'b' -> Color.BLUE; // blue triple letter score
            case 'y' -> Color.YELLOW; // yellow double word score
            case 's' -> Color.rgb(255, 165, 0); // orange star
            case '0' -> Color.rgb(0, 128, 0); // green normal tile
            default -> Color.WHITE; // default color for empty cells
        };
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
    public void setPlace(int row, int col) {
        this.col = col;
        this.row = row;
        redraw();
    }
}
