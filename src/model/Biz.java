// Biz.java
package model;

public class Biz extends Piece {
    public Biz(Color color) {
        super(color);
        this.movementStrategy = new BizMovement();
    }

    @Override
    public boolean isValidMove(Position newPosition, Board board) {
        // Delegate move validation logic to the movement strategy
        return movementStrategy.isValidMove(position, newPosition, board);
    }

    @Override
    public void transform(Board board) {
        // Biz does not transform
    }

    @Override
    public String getImagePath() {
        return (color == Color.RED)
                ? "resources/images/Biz_red.png"
                : "resources/images/Biz_blue.png";
    }
}
