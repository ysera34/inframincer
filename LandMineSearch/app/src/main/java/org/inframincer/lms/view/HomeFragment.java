package org.inframincer.lms.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.inframincer.lms.R;
import org.inframincer.lms.model.BlockStorage;

/**
 * Created by yoon on 2017. 10. 11..
 */

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BlockStorage blockStorage = BlockStorage.getBlockStorage(30, 30, 4);
        blockStorage.setBlocks();
    }

//    edittext validation() number of mines
}
