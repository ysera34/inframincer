package org.inframincer.lms.util;

import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * Created by yoon on 2017. 10. 12..
 */

public abstract class TextValidator implements TextWatcher {

    private static final String TAG = TextValidator.class.getSimpleName();

    private final View mView;

    public TextValidator(View view) {
        mView = view;
    }

    public abstract void validateText(View view, String text);

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mView instanceof TextInputEditText) {
            String text = ((TextInputEditText) mView).getText().toString().trim();
            validateText(mView, text);
        }
    }
}
