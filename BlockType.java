public enum BlockType {
    BLOCK_TYPE_I(0),
    BLOCK_TYPE_O(1),
    BLOCK_TYPE_S(2),
    BLOCK_TYPE_Z(3),
    BLOCK_TYPE_J(4),
    BLOCK_TYPE_L(5),
    BLOCK_TYPE_T(6),
    BLOCK_TYPE_MAX(7);


    private int value;


    private BlockType(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }


    public static BlockType getValue(int value) {
        BlockType[] arr = BlockType.values();

        for (BlockType bt : arr) {
            if (bt.value == value) {
                return bt;
            }
        }

        return null;
    }
}
