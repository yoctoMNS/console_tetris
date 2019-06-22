public final class Blocks {
    public static final int[][][][] blocks =
    {
        // BLOCK_TYPE_I
        {
            // BLOCK_ROT_0
            {
                {0, 1, 0, 0,},
                {0, 1, 0, 0,},
                {0, 1, 0, 0,},
                {0, 1, 0, 0,},
            },
            // BLOCK_ROT_90
            {
                {0, 0, 0, 0,},
                {0, 0, 0, 0,},
                {1, 1, 1, 1,},
                {0, 0, 0, 0,},
            },
            // BLOCK_ROT_180
            {
                {0, 0, 1, 0,},
                {0, 0, 1, 0,},
                {0, 0, 1, 0,},
                {0, 0, 1, 0,},
            },
            // BLOCK_ROT_270
            {
                {0, 0, 0, 0,},
                {1, 1, 1, 1,},
                {0, 0, 0, 0,},
                {0, 0, 0, 0,},
            },
        }
    };


    public static int getBlockCell(BlockType type, BlockRotation rot, int x, int y) {
        return blocks[type.getValue()][rot.getValue()][y][x];
    }
}
