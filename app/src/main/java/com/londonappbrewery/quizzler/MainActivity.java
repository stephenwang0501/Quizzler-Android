package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare constants here


    // TODO: Declare member variables here:
    Button mButtonTrue;
    Button mButtonFalse;
    ProgressBar progressBar;
    TextView mScoreTextView;
    TextView mQuestionTextView;
    int mIndex;
    int totalScore;

    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new com.londonappbrewery.quizzler.TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonTrue = (Button) findViewById(R.id.true_button);
        mButtonFalse = (Button) findViewById(R.id.false_button);

        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTotalScore(true);
                setScoreTextView();
                setNextQuestion();
            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTotalScore(false);
                setScoreTextView();
                setNextQuestion();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setMax(mQuestionBank.length);

        mIndex = 0;
        totalScore = 0;

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setText(mQuestionBank[mIndex].getmQuestionID());

        mScoreTextView = (TextView) findViewById(R.id.score);
        final String init_str = getString(R.string.initial_score);
        mScoreTextView.setText(init_str);
    }

    private void updateTotalScore(final boolean userInput) {
        if (mIndex >= mQuestionBank.length) {
            return;
        }

        if (mQuestionBank[mIndex].getmAnswer() == userInput) {
            ++totalScore;
        }
    }

    private void setNextQuestion() {
        ++mIndex;
        if (mIndex >= mQuestionBank.length) {
            mIndex = mQuestionBank.length;
            final String toastText = getString(R.string.final_score) + " " + totalScore;
            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
            return;
        }
        mQuestionTextView.setText(mQuestionBank[mIndex].getmQuestionID());
        progressBar.setProgress(mIndex+1);
    }

    private void setScoreTextView() {
        final String scoreText = getString(R.string.current_score) +
                " " + totalScore + "/" + mQuestionBank.length;
        mScoreTextView.setText(scoreText);
    }
}
