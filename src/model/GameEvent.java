package model;

/**
 * The GameEvent enum represents various events that can occur within the game.
 * Observers can react differently based on the type of event received.
 *
 * Design Pattern: Observer Pattern
 * Role: Event Types - Defines the different events that observers can listen to.
 *
 * @author Tai Zhi Xuan
 */
public enum GameEvent {
    MOVE,       // A piece has been moved
    RESET,      // The game has been reset
    TRANSFORM,  // Pieces have been transformed
    GAME_OVER   // The game has ended
}
