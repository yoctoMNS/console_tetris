public enum BlockRotation {
    BLOCK_ROT_0(0, 0),
    BLOCK_ROT_90(1, 90),
    BLOCK_ROT_180(2, 180),
    BLOCK_ROT_270(3, 270),
    BLOCK_ROT_MAX(4, 360);


    private int value;
    private int rot;


    private BlockRotation(int value, int rot) {
        this.value = value;
        this.rot = rot;
    }


    public int getValue() {
        return value;
    }

    
    public static BlockRotation getValue(int value) {
        BlockRotation[] arr = BlockRotation.values();

        for (BlockRotation br : arr) {
            if (br.value == value) {
                return br;
            }
        }

        return null;
    }
}
