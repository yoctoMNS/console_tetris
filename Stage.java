public class Stage {
    public static final int STAGE_HEIGHT = 20 + Block.BLOCK_HEIGHT;
    public static final int STAGE_WIDTH = 10 + Block.BLOCK_WIDTH*2;
    public static final int ACTUAL_STAGE_WIDTH = (Stage.STAGE_WIDTH-1) - (Block.BLOCK_WIDTH-1);
    public static final int ACTUAL_STAGE_HEIGHT = (Stage.STAGE_HEIGHT) - (Block.BLOCK_HEIGHT-1);
    public static final int CELL_NONE =  0;
    public static final int CELL_BLOCK =  1;

    private int[][] field;


    public Stage() {
        init();
    }


    private void init() {
        makeStage();
    }


    private void makeStage() {
        field = new int[STAGE_HEIGHT][STAGE_WIDTH];

        for (int y=0; y<STAGE_HEIGHT; ++y)
        for (int x=0; x<STAGE_WIDTH; ++x) {
            field[y][x] = CELL_NONE;

            for (int i=1; i<=Block.BLOCK_WIDTH; ++i) {
                field[y][Block.BLOCK_WIDTH-i] = CELL_BLOCK;
                field[y][STAGE_WIDTH-i] = CELL_BLOCK;
            }
            for (int i=1; i<=Block.BLOCK_HEIGHT; ++i) {
                field[STAGE_HEIGHT-i][x] = CELL_BLOCK;
            }
        }
    }


    public int getStageCell(int x, int y) {
        return field[y][x];
    }


    public void setStageCell(int x, int y, int v) {
        field[y][x] = v;
    }


    public void margeBlock(Block block) {
        int blockX = block.getX();
        int blockY = block.getY();

        for (int y=0; y<Block.BLOCK_HEIGHT; ++y)
        for (int x=0; x<Block.BLOCK_WIDTH; ++x) {
            field[blockY+y][blockX+x] |= block.getBlockCell(x, y);
        }
    }


    public static boolean isStageOut(int x, int y) {
        return (x<0 || x>ACTUAL_STAGE_WIDTH) || (y<0 || y>ACTUAL_STAGE_HEIGHT);
    }


    public void remove() {
        for (int y=ACTUAL_STAGE_HEIGHT-1; y>0; --y)
        for (int x=Block.BLOCK_WIDTH; x<ACTUAL_STAGE_WIDTH; ++x) {
            if (isHorizontalAlign(y)) {
                removeLine(y);
                fallBlockLine(y);
            }
        }
    }


    public void fallBlockLine(int removeLine) {
        for (int y=removeLine; y>0; --y) {
            for (int x=Block.BLOCK_WIDTH; x<ACTUAL_STAGE_WIDTH; ++x) {
                setStageCell(x, y, getStageCell(x, y-1));
            }
        }
    }


    private void removeLine(int y) {
        for (int x=Block.BLOCK_WIDTH; x<ACTUAL_STAGE_WIDTH; ++x) {
            field[y][x] = CELL_NONE;
        }
    }


    private boolean isHorizontalAlign(int y) {
        boolean isAlign = true;

        for (int x=Block.BLOCK_WIDTH; x<ACTUAL_STAGE_WIDTH; ++x) {
            if (field[y][x] == CELL_NONE) {
                isAlign = false;
            }
        }

        return isAlign;
    }
}
