package com.example.kikuchio.geoquiz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int INCORRECT_ANSWER_TOAST_MSG = R.string.incorrect_toast;
    private static final int CORRECT_ANSWER_TOAST_MSG = R.string.correct_toast;
    public static final int JUDGEMENT_TOAST_MSG = R.string.judgement_toast;

    public static final int CHEAT_ACTIVITY_REQUEST_CODE = 0;

    private static final String KEY_INDEX = "index";
    private static final String CHEAT_ARRAY = "cheat_array";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private TrueFalseProvider mTrueFalseProvider = new GeographyTrueFalseProvider();
    private List<TrueFalse> mQuestions = new ArrayList<>();
    private boolean[] mCheatArray;
    private int mCurrentItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkWidgetsToViews();
        initButtonListeners();
        retrievePreviousStateIfPresent(savedInstanceState);
        initQuestions();
        initCheatArray();
        showCurrentQuizItem();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentItemIndex);
        outState.putBooleanArray(CHEAT_ARRAY, mCheatArray);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!userAlreadyCheated() && data != null)
            mCheatArray[mCurrentItemIndex] = data.getBooleanExtra(CheatActivity.ANSWER_WAS_SHOWN_EXTRA, false);
    }

    private void initQuestions() {
        if (mQuestions.isEmpty()) {
            defensiveCopy(mTrueFalseProvider.trueFalseIterable(), mQuestions);
        }
    }

    private void initCheatArray() {
        if (mCheatArray == null) {
            mCheatArray = new boolean[mQuestions.size()];
        }
    }

    private void retrievePreviousStateIfPresent(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            retrievePreviousState(savedInstanceState);
        }
    }

    private void retrievePreviousState(Bundle savedInstanceState) {
        mCurrentItemIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        mCheatArray = savedInstanceState.getBooleanArray(CHEAT_ARRAY);
    }

    @NonNull
    private View.OnClickListener cheatButtonOnClickListener() {
        return v -> startCheatActivity();
    }

    @NonNull
    private View.OnClickListener previousButtonOnClickListener() {
        return v -> showPreviousQuizItem();
    }

    @NonNull
    private View.OnClickListener nextButtonOnClickListener() {
        return v -> showNextQuizItem();
    }

    @NonNull
    private View.OnClickListener falseButtonOnClickListener() {
        return v -> respondToAnswer(false);
    }

    @NonNull
    private View.OnClickListener trueButtonOnClickListener() {
        return v -> respondToAnswer(true);
    }

    private void showCurrentQuizItem() {
        showQuizItem(getCurrentTrueFalseItem().getQuestion());
    }

    private void showPreviousQuizItem() {
        showQuizItem(getPreviousTrueFalseItem().getQuestion());
    }

    private void showNextQuizItem() {
        showQuizItem(getNextTrueFalseItem().getQuestion());
    }

    private void showQuizItem(int question) {
        mQuestionTextView.setText(question);
    }

    private TrueFalse getCurrentTrueFalseItem() {
        return mQuestions.get(mCurrentItemIndex);
    }

    private TrueFalse getPreviousTrueFalseItem() {
        mCurrentItemIndex = mCurrentItemIndex == 0 ? mQuestions.size() - 1 : mCurrentItemIndex - 1;
        return getCurrentTrueFalseItem();
    }

    private TrueFalse getNextTrueFalseItem() {
        mCurrentItemIndex = (mCurrentItemIndex + 1) % mQuestions.size();
        return getCurrentTrueFalseItem();
    }

    private void respondToAnswer(boolean answer) {
        if (userAlreadyCheated()) {
            showJudgementToast();
        } else if (getCurrentTrueFalseItem().getAnswer() == answer) {
            showCorrectToast();
        } else
            showIncorrectToast();
    }

    private boolean userAlreadyCheated() {
        return mCheatArray[mCurrentItemIndex];
    }

    private void showIncorrectToast() {
        showToast(INCORRECT_ANSWER_TOAST_MSG);
    }

    private void showCorrectToast() {
        showToast(CORRECT_ANSWER_TOAST_MSG);
    }

    private void showJudgementToast() {
        showToast(JUDGEMENT_TOAST_MSG);
    }

    private void showToast(int toastTextId) {
        Toast.makeText(MainActivity.this, toastTextId, Toast.LENGTH_SHORT).show();
    }

    private void startCheatActivity() {
        Intent intent = new Intent(MainActivity.this, CheatActivity.class);
        intent.putExtra(CheatActivity.ANSWER_EXTRA, getCurrentTrueFalseItem().getAnswer());
        startActivityForResult(intent, CHEAT_ACTIVITY_REQUEST_CODE);
    }

    private void defensiveCopy(Iterable<TrueFalse> trueFalseItr, List<TrueFalse> questions) {
        for (TrueFalse tf : trueFalseItr)
            questions.add(tf);
    }

    private void initButtonListeners() {
        mTrueButton.setOnClickListener(trueButtonOnClickListener());
        mFalseButton.setOnClickListener(falseButtonOnClickListener());
        mNextButton.setOnClickListener(nextButtonOnClickListener());
        mPreviousButton.setOnClickListener(previousButtonOnClickListener());
        mCheatButton.setOnClickListener(cheatButtonOnClickListener());
    }

    private void linkWidgetsToViews() {
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPreviousButton = findViewById(R.id.previous_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mCheatButton = findViewById(R.id.cheat_button);
    }
}
