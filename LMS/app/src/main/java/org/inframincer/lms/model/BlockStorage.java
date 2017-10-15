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

    /**
     * 가이드용 지뢰 찾기 블럭을 위한 생성자
     * @param numberOfMines 지뢰의 개수
     * @param numberOfHorizontals 가로 방향 블럭의 개수
     * @param numberOfVerticals 세로 방향 블럭의 개수
     * @return BlockStorage의 인스턴스를 리턴합니다.
     */
    public static BlockStorage getBlockStorage(
            int numberOfMines, int numberOfHorizontals, int numberOfVerticals) {
        if (sBlockStorage == null) {
            sBlockStorage = new BlockStorage(numberOfMines, numberOfHorizontals, numberOfVerticals);
        }
        return sBlockStorage;
    }

    /**
     * 게임용 지뢰 찾기 블럭을 위한 생성자
     * @param numberOfMines 지뢰의 개수
     * @param numberOfHorizontals 가로 방향 블럭의 개수
     * @param numberOfVerticals 세로 방향 블럭의 개수
     * @return BlockStorage의 인스턴스를 리턴합니다.
     */
    public static BlockStorage getPracticeBlockStorage(
            int numberOfMines, int numberOfHorizontals, int numberOfVerticals) {
        return new BlockStorage(numberOfMines, numberOfHorizontals, numberOfVerticals);
    }

    /**
     * 위 두 메소드를 위한 실제 생성자
     * @param numberOfMines 지뢰의 개수
     * @param numberOfHorizontals 가로 방향 블럭의 개수
     * @param numberOfVerticals 세로 방향 블럭의 개수
     */
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

    /**
     * 생성자에서 넘겨 받은 가로, 세로 개수로 블럭을 생성하고,
     * 그 블럭을 랜덤으로 지뢰를 세팅한 후, 지뢰가 없는 블럭에는 주위 8방향의 지뢰 개수를 세팅합니다.
     */
    private void setBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        int blockCount = mNumberOfHorizontals * mNumberOfVerticals;
        for (int i = 0; i < blockCount; i++) {
            Block block = new Block();
            blocks.add(block);
        }
        setMines(blocks);
        arrangeBlocks(blocks);
        setBlockHintNumber();
    }

    /**
     * {@link #getUniqueIndex()} 를 통해 지뢰가 세팅될 인덱스 ArrayList를 받아서 들럭에 세팅합니다.
     * @param blocks 지뢰가 세팅 되기 전의 블럭들
     */
    private void setMines(ArrayList<Block> blocks) {
        ArrayList<Integer> mines = getUniqueIndex();
        for (int i : mines) {
            blocks.get(i).setMine(true);
        }
    }

    /**
     * 지뢰가 세팅된 블럭들을 힌트 수(자신을 제외한 8방향의 지뢰 수)를 세팅하기 위해서,
     * 1차원 ArrayList를 2차원 ArrayList로 재정리 합니다.
     * @param blocks 지뢰가 세팅된 블럭들
     */
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
    }

    /**
     * 힌트 수를 세팅하는 메소드입니다.
     * {@link #arrangeBlocks(ArrayList)} ()} 이용하여 1차원 블럭들을 2차원 사각형 모양으로 블럭들을 정리하였습니다.
     * 2차원 자료구조를 반복문으로 돌리고, 지뢰인 블럭을 찾을 경우,
     * 1. 그 블럭의 좌우 블럭에 힌트 수를 1씩 증가 시켜줍니다.
     * 2. 그 블럭의 윗 줄의 블럭을 가져와서 해당되는 3개의 블럭에 힌트 수를 1씩 증가 시켜줍니다.
     * 3. 그 블럭의 아랫 줄의 블럭을 가져와서 해당되는 3개의 블럭에 힌트 수를 1씩 증가 시켜줍니다.
     */
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
    }

    public void clearBlocks() {
        for (ArrayList<Block> blocks : mBlocks) {
            blocks.clear();
        }
        mBlocks.clear();
    }

    /**
     * 생성자를 통해 정해진 지뢰 수를 세팅
     * {@link #getRandomIndex()}를 이용해 유일한 인덱스 지뢰 수 만큼 세팅하고 오름 차순으로 정렬합니다.
     * @return 블럭에 세팅될 지뢰의 인덱스 배열을 리턴합니다.
     */
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
        return uniqueIndices;
    }

    /**
     * 랜덤인덱스를 생성하는 메소드.
     * @return 랜덤인덱스를 생성하고 리턴합니다.
     */
    private int getRandomIndex() {
        int maxIndex = mNumberOfHorizontals * mNumberOfVerticals;
        return (int) (Math.random() * maxIndex);
    }
}
