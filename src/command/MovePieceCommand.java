// MovePieceCommand.java
package command;

import model.Game;
import model.Position;
import model.Piece;

public class MovePieceCommand implements Command {
    private final Game game;
    private final Position from;
    private final Position to;
    private Piece movedPiece;
    private Piece capturedPiece;

    public MovePieceCommand(Game game, Position from, Position to) {
        this.game = game;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute() {
        movedPiece = game.getBoard().getPieceAt(from);
        capturedPiece = game.getBoard().getPieceAt(to);

        // Call movePiece instead of makeMove
        if (game.movePiece(from, to)) {
            System.out.println("Move executed: " + movedPiece + " from " + from + " to " + to);
        } else {
            System.out.println("Invalid move.");
        }
    }

    @Override
    public void undo() {
        if (movedPiece != null) {
            game.getBoard().setPieceAt(from, movedPiece);
            movedPiece.setPosition(from);
            game.getBoard().setPieceAt(to, capturedPiece);

            System.out.println("Move undone: " + movedPiece + " back to " + from);
            if (capturedPiece != null) {
                System.out.println("Restored captured piece: " + capturedPiece + " at " + to);
            }
        } else {
            System.out.println("No move to undo.");
        }
    }
}
