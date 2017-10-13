package org.inframincer.lms.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioGroup;

import org.inframincer.lms.R;
import org.inframincer.lms.util.TextValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yoon on 2017. 10. 12..
 */

public class FormFragment extends Fragment implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    private static final String TAG = FormFragment.class.getSimpleName();

    public static FormFragment newInstance() {

        Bundle args = new Bundle();

        FormFragment fragment = new FormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private NestedScrollView mPracticeNestedScrollView;
    private RadioGroup mQuickStartRadioGroup;
    private Button mPracticeQuickStartButton;

    private TextInputLayout mHorizontalsTextInputLayout;
    private TextInputLayout mVerticalsTextInputLayout;
    private TextInputLayout mMinesTextInputLayout;
    private TextInputEditText mHorizontalsTextInputEditText;
    private TextInputEditText mVerticalsTextInputEditText;
    private TextInputEditText mMinesTextInputEditText;
    private Button mPracticeCustomStartButton;

    private InputMethodManager mInputMethodManager;
    private int mNumberOfHorizontals;
    private int mNumberOfVerticals;
    private int mNumberOfMines;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInputMethodManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPracticeNestedScrollView = view.findViewById(R.id.practice_nested_scroll_view);
        mQuickStartRadioGroup = view.findViewById(R.id.quick_start_radio_group);
        mQuickStartRadioGroup.setOnCheckedChangeListener(this);
        mPracticeQuickStartButton = view.findViewById(R.id.practice_quick_start_button);
        mPracticeQuickStartButton.setEnabled(false);
        mPracticeQuickStartButton.setOnClickListener(this);

        mHorizontalsTextInputLayout = view.findViewById(R.id.horizontals_text_input_layout);
        mVerticalsTextInputLayout = view.findViewById(R.id.verticals_text_input_layout);
        mMinesTextInputLayout = view.findViewById(R.id.mines_text_input_layout);
        mHorizontalsTextInputEditText = view.findViewById(R.id.horizontals_text_input_edit_text);
        mVerticalsTextInputEditText = view.findViewById(R.id.verticals_text_input_edit_text);
        mMinesTextInputEditText = view.findViewById(R.id.mines_text_input_edit_text);
        mPracticeCustomStartButton = view.findViewById(R.id.practice_custom_start_button);
        mPracticeCustomStartButton.setEnabled(false);
        mPracticeCustomStartButton.setOnClickListener(this);

        addInputValidator();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.practice_quick_start_button:
                setNumbersByRadioGroup();
                showConfirmDialog();
                break;
            case R.id.practice_custom_start_button:
                if (validateCustomStartButton()) {
                    setNumbersByInputLayouts();
                    showConfirmDialog();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        mPracticeQuickStartButton.setEnabled(true);
    }

    private void setNumbersByRadioGroup() {
        int checkedRadioButtonId = mQuickStartRadioGroup.getCheckedRadioButtonId();
        switch (checkedRadioButtonId) {
            case R.id.quick_beginning_radio_button:
                mNumberOfHorizontals = 8;
                mNumberOfVerticals = 8;
                mNumberOfMines = 10;
                break;
            case R.id.quick_intermediate_radio_button:
                mNumberOfHorizontals = 10;
                mNumberOfVerticals = 10;
                mNumberOfMines = 20;
                break;
            case R.id.quick_advanced_radio_button:
                mNumberOfHorizontals = 12;
                mNumberOfVerticals = 12;
                mNumberOfMines = 35;
                break;
        }
    }

    private void setNumbersByInputLayouts() {
        mNumberOfHorizontals = Integer.valueOf(mHorizontalsTextInputEditText.getText().toString());
        mNumberOfVerticals = Integer.valueOf(mVerticalsTextInputEditText.getText().toString());
        mNumberOfMines = Integer.valueOf(mMinesTextInputEditText.getText().toString());
    }

    private boolean mIsValidatedHorizontals;
    private boolean mIsValidatedVerticals;
    private boolean mIsValidatedMines;

    private void addInputValidator() {
        mHorizontalsTextInputEditText.addTextChangedListener(
                new TextValidator(mHorizontalsTextInputEditText) {
            @Override
            public void validateText(View view, String text) {
                String validatedMessage = validateBlocks(text);
                if (validatedMessage == null) {
                    mHorizontalsTextInputLayout.setErrorEnabled(false);
                    mIsValidatedHorizontals = true;
                    mNumberOfHorizontals = Integer.valueOf(text);
                    if (validateCustomStartButton()) {
                        mPracticeCustomStartButton.setEnabled(true);
                    }
                } else {
                    mHorizontalsTextInputLayout.setError(validatedMessage);
                    mIsValidatedHorizontals = false;
                    mNumberOfHorizontals = 0;
                    mPracticeCustomStartButton.setEnabled(false);
                    mMinesTextInputEditText.getText().clear();
                }
            }
        });
        mVerticalsTextInputEditText.addTextChangedListener(
                new TextValidator(mVerticalsTextInputEditText) {
            @Override
            public void validateText(View view, String text) {
                String validatedMessage = validateBlocks(text);
                if (validatedMessage == null) {
                    mVerticalsTextInputLayout.setErrorEnabled(false);
                    mIsValidatedVerticals = true;
                    mNumberOfVerticals = Integer.valueOf(text);
                    if (validateCustomStartButton()) {
                        mPracticeCustomStartButton.setEnabled(true);
                    }
                } else {
                    mVerticalsTextInputLayout.setError(validatedMessage);
                    mIsValidatedVerticals = false;
                    mNumberOfVerticals = 0;
                    mPracticeCustomStartButton.setEnabled(false);
                    mMinesTextInputEditText.getText().clear();
                }
            }
        });
        mMinesTextInputEditText.addTextChangedListener(
                new TextValidator(mMinesTextInputEditText) {
            @Override
            public void validateText(View view, String text) {
                String validatedMessage = validateMines(text);
                if (validatedMessage == null) {
                    mMinesTextInputLayout.setErrorEnabled(false);
                    mIsValidatedMines = true;
                    mNumberOfMines = Integer.valueOf(text);
                    if (validateCustomStartButton()) {
                        mPracticeCustomStartButton.setEnabled(true);
                    }
                } else {
                    mMinesTextInputLayout.setError(validatedMessage);
                    mIsValidatedMines = false;
                    mNumberOfMines = 0;
                    mPracticeCustomStartButton.setEnabled(false);
                }
            }
        });
    }

    private String validateBlocks(String blocksText) {
        String NUMBER_PATTERN = "[0-9]+";
        if (!blocksText.isEmpty()) {
            Pattern pattern = Pattern.compile(NUMBER_PATTERN);
            Matcher matcher = pattern.matcher(blocksText);
            if (matcher.matches()) {
                int blocks = Integer.parseInt(blocksText);
                if (blocks < 5) {
                    return getString(R.string.error_message_too_less_number);
                } else if (blocks > 15) {
                    return getString(R.string.error_message_too_many_number);
                } else {
                    return null;
                }
            } else {
                return getString(R.string.error_message_number_pattern);
            }
        } else {
            return getString(R.string.error_message_text_empty);
        }
    }

    private String validateMines(String minesText) {
        String NUMBER_PATTERN = "[0-9]+";
        if (!minesText.isEmpty()) {
            Pattern pattern = Pattern.compile(NUMBER_PATTERN);
            Matcher matcher = pattern.matcher(minesText);
            if (matcher.matches()) {
                int mines = Integer.parseInt(minesText);
                if (mNumberOfHorizontals <= 0) {
                    return getString(R.string.error_message_no_setting_horizontals);
                } else if (mNumberOfVerticals <= 0) {
                    return getString(R.string.error_message_no_setting_verticals);
                }
                int blocks = mNumberOfHorizontals * mNumberOfVerticals;
                if (blocks == mines) {
                    return getString(R.string.error_message_same_between_blocks_and_mines);
                } else if (blocks < mines) {
                    return getString(R.string.error_message_too_many_mines);
                } else {
                    return null;
                }
            } else {
                return getString(R.string.error_message_number_pattern);
            }
        } else {
            return getString(R.string.error_message_text_empty);
        }
    }

    private boolean validateCustomStartButton() {
        return (mIsValidatedHorizontals && mIsValidatedVerticals && mIsValidatedMines);
    }

    private void showConfirmDialog() {

        String confirmDialogMessage = getString(R.string.dialog_message_practice_start,
                mNumberOfHorizontals, mNumberOfVerticals, mNumberOfMines);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title_practice_start);
        builder.setMessage(confirmDialogMessage);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(PracticeActivity.newIntent(getActivity(),
                        mNumberOfHorizontals, mNumberOfVerticals, mNumberOfMines));
            }
        });
        builder.setNegativeButton(android.R.string.no, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mInputMethodManager.hideSoftInputFromWindow(mHorizontalsTextInputEditText.getWindowToken(), 0);
        mInputMethodManager.hideSoftInputFromWindow(mVerticalsTextInputEditText.getWindowToken(), 0);
        mInputMethodManager.hideSoftInputFromWindow(mMinesTextInputEditText.getWindowToken(), 0);
    }
}
