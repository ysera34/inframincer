package org.inframincer.lms.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.inframincer.lms.R;
import org.inframincer.lms.model.Block;
import org.inframincer.lms.model.BlockStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2017. 10. 11..
 */

public class GuideFragment extends Fragment {

    private static final String TAG = GuideFragment.class.getSimpleName();

    public static GuideFragment newInstance() {

        Bundle args = new Bundle();

        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<ArrayList<Block>> mBlocks;
    private int mNumberOfMines;
    private int mNumberOfHorizontals;
    private int mNumberOfVerticals;
    private LinearLayout mBlocksLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBlocksLayout = view.findViewById(R.id.practice_blocks_layout);

        mNumberOfMines = 10;
        mNumberOfHorizontals = 10;
        mNumberOfVerticals = 10;
        BlockStorage blockStorage = BlockStorage.getBlockStorage(
                mNumberOfMines, mNumberOfHorizontals, mNumberOfVerticals);
        mBlocks = blockStorage.getBlocks();
        setBlockViews();
    }

    private void setBlockViews() {
        for (int i = 0; i < mNumberOfVerticals; i++) {
            BlockLayout blockLayout = new BlockLayout(getActivity(), null, mBlocks.get(i), false);
            mBlocksLayout.addView(blockLayout);
        }
    }
}
