import java.util.Random;


public class Block {
    public static final int BLOCK_HEIGHT = 4;
    public static final int BLOCK_WIDTH  = 4;
    public static final int FALL_START_X = Stage.STAGE_WIDTH/2 - BLOCK_WIDTH/2;
    public static final int FALL_START_Y = 0;

    private int x;
    private int y;
    private int[][] block;
    private BlockType type;
    private BlockRotation rot;
    private Random random;


    public Block() {
        init();
    }


    private void init() {
        block = new int[BLOCK_HEIGHT][BLOCK_WIDTH];
        random = new Random();
        type = getRandomBlockType();
        rot = BlockRotation.BLOCK_ROT_0;
        x = FALL_START_X;
        y = FALL_START_Y;

        makeBlock();
    }


    public void makeBlock() {
        for (int y=0; y<BLOCK_HEIGHT; ++y)
        for (int x=0; x<BLOCK_WIDTH; ++x) {
            block[y][x] = Blocks.getBlockCell(type, rot, x, y);
        }
    }


    public void reset() {
        x = FALL_START_X;
        y = FALL_START_Y;
        type = getRandomBlockType();
        rot = BlockRotation.BLOCK_ROT_0;
        makeBlock();
    }


    private BlockType getRandomBlockType() {
        int nextType = random.nextInt(BlockType.BLOCK_TYPE_MAX.getValue());
        return BlockType.getValue(nextType);
    }


    public void fall() {
        ++y;
    }

    
    public void moveLeft() {
        --x;
    }


    public void moveRight() {
        ++x;
    }


    public void rotation() {
        int now = rot.getValue();
        ++now;
        if (now == BlockRotation.BLOCK_ROT_MAX.getValue()) {
            now = BlockRotation.BLOCK_ROT_0.getValue();
        }

        rot = rot.getValue(now);

        makeBlock();
    }


    public int getBlockCell(int x, int y) {
        return block[y][x];
    }


    public int getX() {
        return x;
    }

    
    public int getY() {
        return y;
    }


    public void setX(int x) {
        this.x = x;
    }


    public void setY(int y) {
        this.y = y;
    }


    public BlockType getType() {
        return type;
    }


    public void setType(BlockType type) {
        this.type = type;
    }


    public BlockRotation getRot() {
        return rot;
    }


    public void setRot(BlockRotation rot) {
        this.rot = rot;
    }
}
