import java.util.logging.LogManager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


public class GameManager {
    public static final boolean debugMode = false;

    private Stage viewStage;
    private Stage bufStage;
    private Block block;
    private ConsoleController clear;
    private MyNativeHook hook;
    private long preTime;


    private class MyNativeHook implements NativeKeyListener {
        @Override public void nativeKeyPressed(NativeKeyEvent e) {
            switch (e.getKeyCode()) {
            case NativeKeyEvent.VC_A:
                if (!isLeftCollision(block)) {
                    block.moveLeft();
                }
                break;

            case NativeKeyEvent.VC_D:
                if (!isRightCollision(block)) {
                    block.moveRight();
                }
                break;

            case NativeKeyEvent.VC_W:
                if (canRotation()) {
                    block.rotation();
                }
                break;

            case NativeKeyEvent.VC_S:
                while (!isBottomCollision(block)) {
                    block.fall();
                }
                break;

            default:
                break;
            }
        }


        @Override public void nativeKeyReleased(NativeKeyEvent e) {
        }


        @Override public void nativeKeyTyped(NativeKeyEvent e) {
        }
    }


    public GameManager() {
        init();
    }


    private void init() {
        buildInstance();
        preTime = System.currentTimeMillis();
    }


    private void buildInstance() {
        viewStage = new Stage();
        bufStage = new Stage();
        block = new Block();
        clear = new ConsoleController("/bin/bash", "-c", "clear");
        hook = new MyNativeHook();
    }


    private void draw() {
        System.out.println("\u001b[0;0f");
        for (int y=0; y<=Stage.ACTUAL_STAGE_HEIGHT; ++y) {
            for (int x=Block.BLOCK_WIDTH-1; x<Stage.ACTUAL_STAGE_WIDTH+1; ++x) {
                if (debugMode) {
                    System.out.print(viewStage.getStageCell(x, y));
                } else {
                    switch (viewStage.getStageCell(x, y)) {
                    case Stage.CELL_NONE:
                        System.out.print(ConsoleColor.BACK_COLOR_BRIGHT_BLACK + "　");
                        break;

                    case Stage.CELL_BLOCK:
                        System.out.print(ConsoleColor.BACK_COLOR_BRIGHT_GREEN + "  ");
                        break;

                    case Stage.CELL_WALL:
                        System.out.print(ConsoleColor.BACK_COLOR_BRIGHT_MAGENTA + "  ");
                        break;

                    default:
                        System.out.print("ER");
                    }
                }
            }
            System.out.println();
        }
        System.out.println(ConsoleColor.COLOR_RESET);
    }


    private boolean isBottomCollision(Block block) {
        boolean isCollision = false;
        int blockX = block.getX();
        int blockY = block.getY();

        for (int y=0; y<Block.BLOCK_HEIGHT; ++y) {
            for (int x=0; x<Block.BLOCK_WIDTH; ++x) {
                if (block.getBlockCell(x, y) == Stage.CELL_BLOCK) {
                    if (bufStage.getStageCell(blockX+x, (blockY+y)+1) != Stage.CELL_NONE) {
                        isCollision = true;
                    }
                }
            }
        }

        return isCollision;
    }


    private boolean isLeftCollision(Block block) {
        boolean isCollision = false;
        int blockX = block.getX();
        int blockY = block.getY();

        for (int y=0; y<Block.BLOCK_HEIGHT; ++y) {
            for (int x=0; x<Block.BLOCK_WIDTH; ++x) {
                if (block.getBlockCell(x, y) == Stage.CELL_BLOCK) {
                    if (bufStage.getStageCell((blockX+x)-1, blockY+y) != Stage.CELL_NONE) {
                        isCollision = true;
                    }
                }
            }
        }

        return isCollision;
    }


    private boolean isRightCollision(Block block) {
        boolean isCollision = false;
        int blockX = block.getX();
        int blockY = block.getY();

        for (int y=0; y<Block.BLOCK_HEIGHT; ++y) {
            for (int x=0; x<Block.BLOCK_WIDTH; ++x) {
                if (block.getBlockCell(x, y) == Stage.CELL_BLOCK) {
                    if (bufStage.getStageCell((blockX+x)+1, blockY+y) != Stage.CELL_NONE) {
                        isCollision = true;
                    }
                }
            }
        }

        return isCollision;
    }


    private boolean canRotation() {
        int rotNum = block.getRot().getValue();
        if (++rotNum >= BlockRotation.BLOCK_ROT_MAX.getValue()) {
            rotNum = BlockRotation.BLOCK_ROT_0.getValue();
        }
        BlockRotation nextRot = BlockRotation.getValue(rotNum);

        Block tmpBlock = new Block();
        tmpBlock.setType(block.getType());
        tmpBlock.setRot(nextRot);
        tmpBlock.makeBlock();
        tmpBlock.setX(block.getX());
        tmpBlock.setY(block.getY());

        if (!isBottomCollision(tmpBlock) &&
            !isLeftCollision(tmpBlock)   &&
            !isRightCollision(tmpBlock)) {
            return true;
        }

        return false;
    }


    private void update() {
        bufStage.remove();
        marge();

        if (isOneSecondLater()) {
            if (!isBottomCollision(block)) {
                    block.fall();
            } else {
                fixBlock();
                block.reset();
            }
        }
    }


    private void fixBlock() {
        int blockX = block.getX();
        int blockY = block.getY();

        for (int y=0; y<Block.BLOCK_HEIGHT; ++y)
        for (int x=0; x<Block.BLOCK_WIDTH; ++x) {
            bufStage.margeBlock(block);
        }
    }


    private void marge() {
        int blockX = block.getX();
        int blockY = block.getY();

        for (int y=0; y<Stage.STAGE_HEIGHT; ++y)
        for (int x=0; x<Stage.STAGE_WIDTH; ++x) {
            viewStage.setStageCell(x, y, bufStage.getStageCell(x, y));
        }

        viewStage.margeBlock(block);
    }


    public void run() {
        registerHook();
        clear.execute();

        while (true) {
            draw();
            update();
        }
    }

    
    private boolean isOneSecondLater() {
        long nowTime = System.currentTimeMillis();

        if (nowTime - preTime >= 1000) {
            preTime = nowTime;
            return true;
        }

        return false;
    }


    private void registerHook() {
        LogManager.getLogManager().reset();

		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
            System.out.println("NativeHookException");
		}

		GlobalScreen.addNativeKeyListener(hook);
    }


    private void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
        }
    }
}
