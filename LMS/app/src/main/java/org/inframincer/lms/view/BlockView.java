package org.inframincer.lms.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import org.inframincer.lms.R;
import org.inframincer.lms.model.Block;

/**
 * Created by yoon on 2017. 10. 12..
 */

public class BlockView extends AppCompatTextView implements View.OnClickListener {

    private BlockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockView(Context context, @Nullable AttributeSet attrs, Block block, boolean isPractice) {
        this(context, attrs);
        mContext = context;
        mBlock = block;
        mIsPractice = isPractice;
        initializeView();
    }

    private Context mContext;
    private Block mBlock;
    private boolean mIsPractice;

    private void initializeView() {

        setGravity(Gravity.CENTER);
        if (mIsPractice) {
            if (!mBlock.isVerified()) {
                setBackgroundResource(R.drawable.bg_selector_block);
                setOnClickListener(this);
                if (mBlock.isMine()) {
                    try {
                        mBlockClickedListener = (OnBlockClickedListener) mContext;
                    } catch (ClassCastException e) {
                        throw new ClassCastException(mContext.toString()
                                + " must implements OnBlockClickedListener");
                    }
                }
            } else {
                showBlock();
            }
        } else {
            showBlock();
        }
    }

    @Override
    public void onClick(View view) {
        showBlock();
        if (mBlock.isMine()) {
            mBlockClickedListener.onBlockClicked();
        }
    }

    public void setViewSize(int viewSize) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        params.width = viewSize;
        params.height = viewSize;
        setLayoutParams(params);
    }

    private void showBlock() {
        mBlock.setVerified(true);
        if (mBlock.isMine()) {
            setBackgroundResource(R.drawable.ic_mine_24dp);
        } else {
            setBackground(null);
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            setTextColor(getResources().getColor(mBlock.getHintColor()));
            setText(String.valueOf(mBlock.getHintNumber()));
        }
    }

    OnBlockClickedListener mBlockClickedListener;

    interface OnBlockClickedListener {
        void onBlockClicked();
    }
}
