package org.inframincer.lms.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import org.inframincer.lms.R;
import org.inframincer.lms.model.Block;

import java.util.ArrayList;

/**
 * Created by yoon on 2017. 10. 12..
 */

public class BlockLayout extends LinearLayout {

    private BlockLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockLayout(Context context, @Nullable AttributeSet attrs, ArrayList<Block> blocks, boolean isPractice) {
        this(context, attrs);
        mBlocks = blocks;
        mIsPractice = isPractice;
        initializeView(context);
    }

    private ArrayList<Block> mBlocks;
    private boolean mIsPractice;

    private void initializeView(Context context) {

        setGravity(Gravity.CENTER_HORIZONTAL);
        int childViewSize = mBlocks.size();
        int blockViewSize = getResources().getDimensionPixelSize(R.dimen.block_layout_width) / childViewSize;
        for (int i = 0; i < childViewSize; i++) {
            BlockView blockView = new BlockView(context, null, mBlocks.get(i), mIsPractice);
            addView(blockView);
            blockView.setViewSize(blockViewSize);
        }
    }

    

}
