package org.inframincer.lms.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;

import org.inframincer.lms.R;
import org.inframincer.lms.model.Block;

/**
 * Created by yoon on 2017. 10. 12..
 */

public class BlockView extends AppCompatTextView {

    private BlockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockView(Context context, @Nullable AttributeSet attrs, Block block) {
        this(context, attrs);
        mBlock = block;
        initializeView();
    }

    private Block mBlock;

    private void initializeView() {
        setGravity(Gravity.CENTER);
        if (mBlock.isMine()) {
            setBackgroundResource(R.mipmap.ic_launcher);
        } else {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            setTextColor(getResources().getColor(mBlock.getHintColor()));
            setText(String.valueOf(mBlock.getHintNumber()));
        }
    }

    public void setViewSize(int viewSize) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        params.width = viewSize;
        params.height = viewSize;
        setLayoutParams(params);
    }
}
