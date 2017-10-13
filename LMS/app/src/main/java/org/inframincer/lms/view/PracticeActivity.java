package org.inframincer.lms.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.inframincer.lms.R;
import org.inframincer.lms.model.Block;
import org.inframincer.lms.model.BlockStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2017. 10. 13..
 */

public class PracticeActivity extends AppCompatActivity {

    private static final String TAG = PracticeActivity.class.getSimpleName();

    private static final String EXTRA_HORIZONTALS_NUMBER = "org.inframincer.lms.horizontals_number";
    private static final String EXTRA_VERTICALS_NUMBER = "org.inframincer.lms.verticals_number";
    private static final String EXTRA_MINES_NUMBER = "org.inframincer.lms.mines_number";


    public static Intent newIntent(Context packageContext,
                                   int horizontals, int verticals, int mines) {
        Intent intent = new Intent(packageContext, PracticeActivity.class);
        intent.putExtra(EXTRA_HORIZONTALS_NUMBER, horizontals);
        intent.putExtra(EXTRA_VERTICALS_NUMBER, verticals);
        intent.putExtra(EXTRA_MINES_NUMBER, mines);
        return intent;
    }

    private int mHorizontals;
    private int mVerticals;
    private int mMines;

    private TextView mBlockStatusTextView;
    private TextView mMineStatusTextView;

    private ArrayList<ArrayList<Block>> mPracticeBlocks;
    private LinearLayout mPracticeBlocksLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHorizontals = getIntent().getIntExtra(EXTRA_HORIZONTALS_NUMBER, 0);
        mVerticals = getIntent().getIntExtra(EXTRA_VERTICALS_NUMBER, 0);
        mMines = getIntent().getIntExtra(EXTRA_MINES_NUMBER, 0);

        setContentView(R.layout.activity_practice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_title));

        mBlockStatusTextView = (TextView) findViewById(R.id.block_status_text_view);
        mMineStatusTextView = (TextView) findViewById(R.id.mine_status_text_view);
        mBlockStatusTextView.setText(getString(R.string.text_block_status, mHorizontals, mVerticals));
        mMineStatusTextView.setText(getString(R.string.text_mine_status, mMines));

        mPracticeBlocksLayout = (LinearLayout) findViewById(R.id.practice_blocks_layout);

        BlockStorage blockStorage = BlockStorage.getPracticeBlockStorage(
                mMines, mHorizontals, mVerticals);
        mPracticeBlocks = blockStorage.getBlocks();
        setPracticeBlockViews();
    }

    private void setPracticeBlockViews() {
        for (int i = 0; i < mVerticals; i++) {
            BlockLayout blockLayout = new BlockLayout(getApplicationContext(), null, mPracticeBlocks.get(i), true);
            mPracticeBlocksLayout.addView(blockLayout);
        }
    }
}
