package org.inframincer.slap.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.inframincer.slap.R;
import org.inframincer.slap.model.Action;
import org.inframincer.slap.model.StatusObject;
import org.inframincer.slap.rest.ApiClient;
import org.inframincer.slap.rest.ApiService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yoon on 2017. 10. 9..
 */

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private DatabaseReference mDatabaseReference;
    private TextView mDateTimeTextView;
    private ImageView mNeedleImageView;
    private RotateAnimation mNeedleRotateAnimation;
    private Animation mTextInAnimation;
    private Animation mTextOutAnimation;
    private TextView mStageTextView;
    private TextView mStatusTextView;
    private TextView mAction1TextView;
    private TextView mActionsTextView;
    private TextView mAction2TextView;
    private TextView mAction3TextView;

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180.0f;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mTextInAnimation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
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

        mNeedleImageView = view.findViewById(R.id.needle_image_view);
        mNeedleRotateAnimation = new RotateAnimation(0, -90f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.783482f);
        mNeedleRotateAnimation.setDuration(500);
        mNeedleRotateAnimation.setFillAfter(true);
        mNeedleImageView.startAnimation(mNeedleRotateAnimation);

        mDateTimeTextView = view.findViewById(R.id.date_time_text_view);
        mDateTimeTextView.setText(getCurrentDateTime());
        mStageTextView = view.findViewById(R.id.stage_text_view);
        mStatusTextView = view.findViewById(R.id.status_text_view);
        mAction1TextView = view.findViewById(R.id.action1_text_view);
        mActionsTextView = view.findViewById(R.id.actions_text_view);
        mAction2TextView = view.findViewById(R.id.action2_text_view);
        mAction3TextView = view.findViewById(R.id.action3_text_view);
        callStatusValue();
    }

    private void callStatusValue() {
        String apiKey = getString(R.string.api_key);
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<StatusObject> statusCall = apiService.getStatus(apiKey, 0, 5, getCurrentDate());
        statusCall.enqueue(new Callback<StatusObject>() {
            @Override
            public void onResponse(Call<StatusObject> call, Response<StatusObject> response) {
                if (response.body().getStatusResponse() != null) {
                    final double value = response.body().getStatusResponse().getStatuses().get(0).getValue();
                    getAction((float) value);
                } else {
                    updateUI(-1f, null);
                }
            }

            @Override
            public void onFailure(Call<StatusObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                updateUI(-2f, null);
            }
        });
    }

    private void getAction(final float value) {
        mDatabaseReference.child(getLevel(value))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Action action = dataSnapshot.getValue(Action.class);
//                        Log.d(TAG, "onDataChange: action " + action.toString());
                updateUI(value, action);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled: ", databaseError.toException());
                updateUI(-3f, null);
            }
        });
    }

    private void updateUI(final float value, final Action action) {
        if (action != null) {
            int[] stageColorArr = {R.color.colorOrange300, R.color.colorOrange500,
                    R.color.colorOrange700, R.color.colorOrange900,};

            mNeedleRotateAnimation = new RotateAnimation(INITIAL_POSITION - 90f,
                    calculateTheAngle(value) - 90f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.783482f);
            mNeedleRotateAnimation.setDuration(500);
            mNeedleRotateAnimation.setInterpolator(new OvershootInterpolator(3));
            mNeedleRotateAnimation.setFillAfter(true);
            mNeedleImageView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mNeedleImageView.startAnimation(mNeedleRotateAnimation);
                }
            }, 700);

            mStageTextView.setTextColor(getResources().getColor(stageColorArr[action.stage - 1]));
            mStageTextView.setText(action.name);
            mStageTextView.startAnimation(mTextInAnimation);
            mStatusTextView.setTextColor(getResources().getColor(stageColorArr[action.stage - 1]));
            mStatusTextView.setText(String.valueOf(value));
            mStatusTextView.startAnimation(mTextInAnimation);
            mAction1TextView.setText(action.action1);
            mAction1TextView.startAnimation(mTextInAnimation);
            mAction2TextView.setText(action.action2);
            mAction2TextView.startAnimation(mTextInAnimation);
            mAction3TextView.setText(action.action3);
        } else {
            if (value == -1f) {
                mAction1TextView.setText(R.string.source_error_message);
            } else {
                mAction1TextView.setText(R.string.internet_error_message);
            }
        }
    }

    private String getLevel(float value) {
        float[] minValueArr = {0.0f, 83.4f, 166.7f, 250.1f, 333.4f, 416.7f,
                500.1f, 583.4f, 666.7f, 750.1f, 833.4f, 916.7f};
        int resultIndex = -1;
        for (int i = 0; i < minValueArr.length; i++) {
            if (value - minValueArr[i] < 0) {
                resultIndex = i - 1;
                break;
            }
        }
        return String.valueOf(resultIndex);
    }

    private String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd h:mm a", Locale.KOREA);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    private float calculateTheAngle(float value) {
        float maxValue = 1000.0f;
        return value * ROTATED_POSITION / maxValue;
    }
}
