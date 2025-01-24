package model;

/**
 * The IDGenerator class is a singleton responsible for generating unique identifiers
 * for different types of pieces in the game. It ensures that each piece has a distinct ID.
 *
 * Design Pattern: Singleton Pattern
 * Role: Singleton - Ensures a single instance exists throughout the application.
 *
 * @author Tiffany Jong Shu Ting
 */
public class IDGenerator {
    /**
     * The single instance of IDGenerator.
     */
    private static volatile IDGenerator instance = null;

    /**
     * Counter for Ram pieces.
     */
    private int ramId;

    /**
     * Fixed ID for Tor pieces.
     */
    private final int torId;

    /**
     * Fixed ID for Xor pieces.
     */
    private final int xorId;

    /**
     * Counter for Biz pieces.
     */
    private int bizId;

    /**
     * Fixed ID for Sau pieces.
     */
    private final int sauId;

    /**
     * Private constructor to prevent instantiation.
     * Initializes all piece ID counters.
     */
    private IDGenerator() {
        this.ramId = 1;
        this.torId = 7;
        this.xorId = 6;
        this.bizId = 8;
        this.sauId = 10;
    }

    /**
     * Retrieves the single instance of IDGenerator.
     * Implements double-checked locking for thread safety.
     *
     * @return The IDGenerator instance.
     */
    public static IDGenerator getInstance() {
        if (instance == null) {
            synchronized (IDGenerator.class) {
                if (instance == null) {
                    instance = new IDGenerator();
                }
            }
        }
        return instance;
    }

    /**
     * Retrieves the next available Ram ID.
     * Cycles between 1 and 5.
     *
     * @return The next Ram ID.
     */
    public synchronized int getNextRamId() {
        if (ramId <= 5) {
            return ramId++;
        } else {
            ramId = 1;
            return ramId++;
        }
    }

    /**
     * Retrieves the fixed Tor ID.
     *
     * @return The Tor ID.
     */
    public int getTorId() {
        return torId;
    }

    /**
     * Retrieves the fixed Xor ID.
     *
     * @return The Xor ID.
     */
    public int getXorId() {
        return xorId;
    }

    /**
     * Retrieves the next available Biz ID.
     * Cycles between 8 and 9.
     *
     * @return The next Biz ID.
     */
    public synchronized int getNextBizId() {
        if (bizId <= 9) {
            return bizId++;
        } else {
            bizId = 8;
            return bizId++;
        }
    }

    /**
     * Retrieves the fixed Sau ID.
     *
     * @return The Sau ID.
     */
    public int getSauId() {
        return sauId;
    }

    /**
     * Resets the ID counters for Ram and Biz pieces.
     * Useful when starting a new game.
     */
    public synchronized void resetIds() {
        this.ramId = 1;
        this.bizId = 8;
    }
}
