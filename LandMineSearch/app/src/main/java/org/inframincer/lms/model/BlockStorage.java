package org.inframincer.lms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by yoon on 2017. 10. 11..
 */

public class BlockStorage {

    private static final String TAG = BlockStorage.class.getSimpleName();

    private static BlockStorage sBlockStorage;
    private ArrayList<ArrayList<Block>> mBlocks;
    private int mNumberOfMines;
    private int mNumberOfHorizontals;
    private int mNumberOfVerticals;

    public static BlockStorage getBlockStorage(
            int numberOfMines, int numberOfHorizontals, int numberOfVerticals) {
        if (sBlockStorage == null) {
            sBlockStorage = new BlockStorage(numberOfMines, numberOfHorizontals, numberOfVerticals);
        }
        return sBlockStorage;
    }

    private BlockStorage(int numberOfMines, int numberOfHorizontals, int numberOfVerticals) {
        mNumberOfMines = numberOfMines;
        mNumberOfHorizontals = numberOfHorizontals;
        mNumberOfVerticals = numberOfVerticals;
        mBlocks = new ArrayList<>();
        setBlocks();
    }

    public ArrayList<ArrayList<Block>> getBlocks() {
        return mBlocks;
    }

    public void setBlocks() {

//        for (int i = 0; i < mNumberOfVerticals; i++) {
//            ArrayList<Block> blocks = new ArrayList<>(mNumberOfHorizontals);
//            for (int j = 0; j < mNumberOfHorizontals; j++) {
//                Block block = new Block();
//                block.setNumber((i * 100) + (j));
//                blocks.add(block);
//            }
//            mBlocks.add(blocks);
//        }

//        for (ArrayList<Block> blocks : mBlocks) {
//            for (Block block : blocks) {
//                Log.i(TAG, "setBlocks: block number : " + block.getNumber());
//            }
//        }

        ArrayList<Block> blocks = new ArrayList<>();
        int blockCount = mNumberOfHorizontals * mNumberOfVerticals;
        for (int i = 0; i < blockCount; i++) {
            Block block = new Block();
//            block.setNumber(i);
            blocks.add(block);
        }
//        for (Block block1 : blocks) {
//            Log.i(TAG, "setBlocks: block number : " + block1.getNumber());
//        }
        setMines(blocks);
        arrangeBlocks(blocks);
        setBlockHintNumber();
    }

    private void setMines(ArrayList<Block> blocks) {
        ArrayList<Integer> mines = getUniqueIndex();
        for (int i : mines) {
            blocks.get(i).setMine(true);
        }
    }

    private void arrangeBlocks(ArrayList<Block> blocks) {
        for (int i = 0; i < mNumberOfVerticals; i++) {
            ArrayList<Block> block = new ArrayList<>();
            int start  = i * mNumberOfHorizontals;
            int end = start + mNumberOfHorizontals;
            for (int j = start; j < end; j++) {
                block.add(blocks.get(j));
            }
            mBlocks.add(block);
        }

//        for (ArrayList<Block> blocks1 : mBlocks) {
//            for (Block block : blocks1) {
//                Log.i(TAG, "arrangeBlocks: block number : " + block.getNumber());
//            }
//        }
    }

    private void setBlockHintNumber() {
        int outerSize = mBlocks.size();
        for (int i = 0; i < outerSize; i++) {
            ArrayList<Block> blocks = mBlocks.get(i);
            int innerSize = blocks.size();
            for (int j = 0; j < innerSize; j++) {
                if (blocks.get(j).isMine()) {
                    if (j > 0) {
                        blocks.get(j - 1).addHintNumber();
                    }
                    if (j < innerSize - 1) {
                        blocks.get(j + 1).addHintNumber();
                    }
                    if (i > 0) {
                        ArrayList<Block> beforeBlocks = mBlocks.get(i - 1);
                        if (j > 0) {
                            beforeBlocks.get(j - 1).addHintNumber();
                        }
                        beforeBlocks.get(j).addHintNumber();
                        if (j < innerSize - 1) {
                            beforeBlocks.get(j + 1).addHintNumber();
                        }
                    }
                    if (i < outerSize - 1) {
                        ArrayList<Block> nextBlocks = mBlocks.get(i + 1);
                        if (j > 0) {
                            nextBlocks.get(j - 1).addHintNumber();
                        }
                        nextBlocks.get(j).addHintNumber();
                        if (j < innerSize - 1) {
                            nextBlocks.get(j + 1).addHintNumber();
                        }
                    }
                }
            }
        }

//        for (ArrayList<Block> blocks1 : mBlocks) {
//            for (Block block : blocks1) {
//                if (block.isMine()) {
//                    block.setHintNumber(Integer.MAX_VALUE);
//                }
//                Log.i(TAG, "setBlockHintNumber: block getHintNumber : " + block.getHintNumber());
//            }
//        }
    }

    public void clearBlocks() {
        for (ArrayList<Block> blocks : mBlocks) {
            blocks.clear();
        }
        mBlocks.clear();
    }

    private ArrayList<Integer> getUniqueIndex() {
        ArrayList<Integer> uniqueIndices = new ArrayList<>();
        while (uniqueIndices.size() != mNumberOfMines) {
            int randomIndex = getRandomIndex();
            if (!uniqueIndices.contains(randomIndex)) {
                uniqueIndices.add(randomIndex);
            }
        }
        Collections.sort(uniqueIndices, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                if (i1 > i2) {
                    return 1;
                } else if (i1 < i2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

//        for (int i = 0; i < uniqueIndices.size(); i++) {
//            Log.i(TAG, "getUniqueIndex: index : " + i + " number : " + uniqueIndices.get(i));
//        }
        return uniqueIndices;
    }

    private int getRandomIndex() {
        int maxIndex = mNumberOfHorizontals * mNumberOfVerticals;
        return (int) (Math.random() * maxIndex);
    }
}
