package org.inframincer.slap.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import org.inframincer.slap.R;
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

    private TextView mDateTimeTextView;
    private TextView mStatusTextView;
    private ImageView mNeedleImageView;
    private RotateAnimation mNeedleRotateAnimation;


    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180.0f;

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

        mStatusTextView = view.findViewById(R.id.status_text_view);
        mNeedleImageView = view.findViewById(R.id.needle_image_view);
        mNeedleRotateAnimation = new RotateAnimation(0, -90f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 1.0f);
        mNeedleRotateAnimation.setDuration(500);
        mNeedleRotateAnimation.setFillAfter(true);
        mNeedleImageView.startAnimation(mNeedleRotateAnimation);

        String apiKey = getString(R.string.api_key);
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<StatusObject> statusCall = apiService.getStatus(apiKey, 0, 5, getCurrentDate());
        statusCall.enqueue(new Callback<StatusObject>() {
            @Override
            public void onResponse(Call<StatusObject> call, Response<StatusObject> response) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                double value = response.body().getStatusResponse().getStatuses().get(0).getValue();
                mStatusTextView.setText(String.valueOf(value));
                mNeedleRotateAnimation = new RotateAnimation(INITIAL_POSITION - 90f,
                        calculateTheAngle((float) value) - 90f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 1.0f);
                mNeedleRotateAnimation.setDuration(500);
                mNeedleRotateAnimation.setFillAfter(true);
                mNeedleImageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNeedleImageView.startAnimation(mNeedleRotateAnimation);
                    }
                }, 1000);

            }

            @Override
            public void onFailure(Call<StatusObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });

        mDateTimeTextView = view.findViewById(R.id.date_time_text_view);
        mDateTimeTextView.setText(getCurrentDateTime());
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
