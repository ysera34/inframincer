package org.inframincer.lms.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

public class PracticeActivity extends AppCompatActivity 
        implements BlockView.OnBlockClickedListener {

    private static final String TAG = PracticeActivity.class.getSimpleName();

    private static final String EXTRA_HORIZONTALS_NUMBER = "org.inframincer.lms.horizontals_number";
    private static final String EXTRA_VERTICALS_NUMBER = "org.inframincer.lms.verticals_number";
    private static final String EXTRA_MINES_NUMBER = "org.inframincer.lms.mines_number";

    private static final String INSTANCE_PRACTICE_BLOCKS = "instance_practice_blocks";
    private static final String INSTANCE_IS_RESET = "instance_is_reset";

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

    private boolean mIsReset;
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

        if (savedInstanceState != null) {
            mIsReset = savedInstanceState.getBoolean(INSTANCE_IS_RESET, false);
            if (!mIsReset) {
                ArrayList<Block> blocks = savedInstanceState.getParcelableArrayList(INSTANCE_PRACTICE_BLOCKS);
                mPracticeBlocks = setSquare(blocks);
            } else {
                BlockStorage blockStorage = BlockStorage.getPracticeBlockStorage(
                        mMines, mHorizontals, mVerticals);
                mPracticeBlocks = blockStorage.getBlocks();
                mIsReset = false;
            }
        } else {
            BlockStorage blockStorage = BlockStorage.getPracticeBlockStorage(
                    mMines, mHorizontals, mVerticals);
            mPracticeBlocks = blockStorage.getBlocks();
        }
        setPracticeBlockViews();
    }

    private void setPracticeBlockViews() {
        for (int i = 0; i < mVerticals; i++) {
            BlockLayout blockLayout = new BlockLayout(
                    PracticeActivity.this, null, mPracticeBlocks.get(i), true);
            mPracticeBlocksLayout.addView(blockLayout);
        }
    }

    @Override
    public void onBlockClicked() {
        showResetDialog();
    }

    @Override
    public void onBlockLongClicked() {
        if (compareBlocks()) {
            showAchieveDialog();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        showStopDialog();
    }

    private void showAchieveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PracticeActivity.this);
        builder.setTitle(R.string.dialog_title_practice_achieve);
        builder.setMessage(R.string.dialog_message_practice_achieve);
        builder.setPositiveButton(R.string.dialog_button_text_recreate, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mIsReset = true;
                recreate();
            }
        });
        builder.setNegativeButton(R.string.dialog_button_text_finish, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showResetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PracticeActivity.this);
        builder.setTitle(R.string.dialog_title_practice_reset);
        builder.setMessage(R.string.dialog_message_practice_reset);
        builder.setPositiveButton(R.string.dialog_button_text_recreate, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mIsReset = true;
                recreate();
            }
        });
        builder.setNegativeButton(R.string.dialog_button_text_finish, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showStopDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PracticeActivity.this);
        builder.setTitle(R.string.dialog_title_practice_stop);
        builder.setMessage(R.string.dialog_message_practice_stop);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton(android.R.string.no, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean compareBlocks() {
        for (ArrayList<Block> blocks : mPracticeBlocks) {
            for (Block block : blocks) {
                if (block.isMine() && !block.isDetected()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(INSTANCE_PRACTICE_BLOCKS, setStraighten());
        outState.putBoolean(INSTANCE_IS_RESET, mIsReset);
        super.onSaveInstanceState(outState);
    }

    private ArrayList<Block> setStraighten() {
        ArrayList<Block> straightBlocks = new ArrayList<>();
        for (ArrayList<Block> blocks : mPracticeBlocks) {
            for (Block b : blocks) {
                straightBlocks.add(b);
            }
        }
        return straightBlocks;
    }

    private ArrayList<ArrayList<Block>> setSquare(ArrayList<Block> blocks) {
        ArrayList<ArrayList<Block>> squareBlocks = new ArrayList<>();
        for (int i = 0; i < mVerticals; i++) {
            ArrayList<Block> block = new ArrayList<>();
            int start  = i * mHorizontals;
            int end = start + mHorizontals;
            for (int j = start; j < end; j++) {
                block.add(blocks.get(j));
            }
            squareBlocks.add(block);
        }
        return squareBlocks;
    }
}
