// GameObserver.java
package model;

/**
 * Observer interface for the Game.
 */
public interface GameObserver {
    /**
     * Called when the Game's state changes.
     *
     * @param event The type of event that occurred.
     */
    void update(GameEvent event);
}