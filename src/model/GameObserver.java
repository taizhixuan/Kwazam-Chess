package model;

/**
 * The GameObserver interface defines the contract for observers interested
 * in receiving updates about game events.
 *
 * Design Pattern: Observer Pattern
 * Role: Observer - Receives notifications from the Subject (Game) about state changes.
 *
 * author Tai Zhi Xuan
 */
public interface GameObserver {
    /**
     * Called by the Subject (Game) when a specific event occurs.
     *
     * @param event The type of event that has occurred.
     */
    void update(GameEvent event);
}