// IDGenerator.java
package model;

/**
 * Singleton class for generating unique IDs for different piece types.
 */
public class IDGenerator {
    // Volatile keyword ensures visibility of changes across threads
    private static volatile IDGenerator instance = null;

    private int ramId;
    private final int torId;
    private final int xorId;
    private int bizId;
    private final int sauId;

    // Private constructor to prevent instantiation
    private IDGenerator() {
        this.ramId = 1;
        this.torId = 7;
        this.xorId = 6;
        this.bizId = 8;
        this.sauId = 10;
    }

    /**
     * Returns the single instance of IDGenerator.
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
     * Gets the next Ram ID.
     *
     * @return The next available Ram ID.
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
     * Gets the Tor ID.
     *
     * @return The Tor ID.
     */
    public int getTorId() {
        return torId;
    }

    /**
     * Gets the Xor ID.
     *
     * @return The Xor ID.
     */
    public int getXorId() {
        return xorId;
    }

    /**
     * Gets the next Biz ID.
     *
     * @return The next available Biz ID.
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
     * Gets the Sau ID.
     *
     * @return The Sau ID.
     */
    public int getSauId() {
        return sauId;
    }

    /**
     * Resets the ID counters for a new game.
     */
    public synchronized void resetIds() {
        this.ramId = 1;
        this.bizId = 8;
        // torId, xorId, sauId are final and do not reset
    }
}
