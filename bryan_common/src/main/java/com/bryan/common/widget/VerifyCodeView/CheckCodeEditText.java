package com.bryan.common.widget.VerifyCodeView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bryan.common.R;

import java.util.ArrayList;

/**
 * Created by huanglei on 6/25/16.
 */
public class CheckCodeEditText extends LinearLayout {

    private LinearLayout checkCodeLinear;
    private EditText checkCodeEdit;
    private TextView code1;
    private TextView code2;
    private TextView code3;
    private TextView code4;
    private TextView code5;
    private TextView code6;
    private ArrayList<TextView> codeViews = new ArrayList<>();

    public CheckCodeEditText(Context context) {
        super(context);
        init();
    }

    public CheckCodeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckCodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CheckCodeEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void init() {
        inflate(getContext(), R.layout.check_code_layout, this);
        this.checkCodeLinear = findViewById(R.id.checkcode_linear);
        this.checkCodeEdit = findViewById(R.id.checkcode_edit);
        this.code1 = findViewById(R.id.codeOne);
        this.code2 = findViewById(R.id.codeTow);
        this.code3 = findViewById(R.id.codeThree);
        this.code4 = findViewById(R.id.codeFour);
        this.code5 = findViewById(R.id.codeFive);
        this.code6 = findViewById(R.id.codeSix);

        this.codeViews.clear();
        this.codeViews.add(code1);
        this.codeViews.add(code2);
        this.codeViews.add(code3);
        this.codeViews.add(code4);
        this.codeViews.add(code5);
        this.codeViews.add(code6);

        this.checkCodeLinear.setOnClickListener(onClickListener);
        this.checkCodeEdit.addTextChangedListener(textWatcher);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == checkCodeLinear.getId()) {
                checkCodeEdit.requestFocus();
                Context context = v.getContext();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            for (int j = 0; j < codeViews.size(); j++) {
                codeViews.get(j).setText("");
            }
            for (int j = 0; j < s.length() && j < codeViews.size(); j++) {
                codeViews.get(j).setText(s.charAt(j) + "");
            }

            if (codeViews.size() > 0 && s.length() == codeViews.size()) {
                if (listener != null)
                    listener.checkCodeFinish();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public String getText() {
        return this.checkCodeEdit.getText().toString();
    }

    private CheckCodeCompleteListener listener;

    public void addCompleteListener(CheckCodeCompleteListener listener) {
        this.listener = listener;
    }

    public EditText getEditText() {
        return checkCodeEdit;
    }

}
