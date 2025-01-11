package model;

/**
 * Assigned IDs for each different type of piece
 */
public class IDGenerator {
    // Static IDs shared for both RED and BLUE sides
    private static final int[] RAM_IDS = {1, 2, 3, 4, 5};
    private static final int[] BIZ_IDS = {8, 9};
    private static final int TOR_ID = 7;
    private static final int XOR_ID = 6;
    private static final int SAU_ID = 10;

    public static int getRamId(int index) {
        return RAM_IDS[index];
    }

    public static int getBizId(int index) {
        return BIZ_IDS[index];
    }

    public static int getTorId() {
        return TOR_ID;
    }

    public static int getXorId() {
        return XOR_ID;
    }

    public static int getSauId() {
        return SAU_ID;
    }
}
