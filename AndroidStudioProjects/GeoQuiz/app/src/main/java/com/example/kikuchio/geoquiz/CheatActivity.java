package com.example.kikuchio.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    public static final String ANSWER_EXTRA = "com.example.kikuchio.geoquiz.answer";
    public static final String ANSWER_WAS_SHOWN_EXTRA = "com.example.kikuchio.geoquiz.answer_shown";
    private static final String ANSWER_WAS_SHOWN_STATE = "answer_was_shown";

    private TextView mCheatAnswerTextView;
    private Button mShowAnswerButton;

    private boolean mAnswerWasShown = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        linkWidgetsToViews();
        retrievePreviousStateIfPresent(savedInstanceState);
        initButtonListeners();
        setAnswerShownResult(mAnswerWasShown);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ANSWER_WAS_SHOWN_STATE, mAnswerWasShown);
    }

    private void retrievePreviousStateIfPresent(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mAnswerWasShown = savedInstanceState.getBoolean(ANSWER_WAS_SHOWN_STATE);
        }
        if (mAnswerWasShown) {
            displayAnswer();
        }
    }

    private void initButtonListeners() {
        mShowAnswerButton.setOnClickListener(showAnswerButtonOnClickListener());
    }

    @NonNull
    private View.OnClickListener showAnswerButtonOnClickListener() {
        return v -> {
            displayAnswer();
            mAnswerWasShown = true;
            setAnswerShownResult(mAnswerWasShown);
        };
    }

    private void displayAnswer() {
        Boolean answer = getIntent().getBooleanExtra(ANSWER_EXTRA, false);
        mCheatAnswerTextView.setText(answer == true ? R.string.true_button : R.string.false_button);
    }

    private void setAnswerShownResult(boolean wasShown) {
        Intent intent = new Intent();
        intent.putExtra(ANSWER_WAS_SHOWN_EXTRA, wasShown);
        setResult(RESULT_OK, intent);
    }

    private void linkWidgetsToViews() {
        mCheatAnswerTextView = findViewById(R.id.cheat_answer_text);
        mShowAnswerButton = findViewById(R.id.show_answer_button);
    }
}
