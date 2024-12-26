// ViewInterface.java
package view;

import model.Position;

import java.util.List;

public interface ViewInterface {
    void highlightValidMoves(List<Position> validMoves);
    void clearHighlights();
    void refreshBoard();
}
