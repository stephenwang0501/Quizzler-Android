package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    //TODO: add constant variables here
    private static final String INDEX = "CURRENT_INDEX";
    private static final String SCORE = "CURRENT_SCORE";
    private static final String DIALOG_SHOWN = "DIALOG_SHOWN";

    // TODO: Declare member variables here:
    Button mButtonTrue;
    Button mButtonFalse;
    ProgressBar mProgressBar;
    TextView mScoreTextView;
    TextView mQuestionTextView;
    AlertDialog mDialog;
    int mIndex;
    int mTotalScore;
    boolean mDialogShown;

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

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setMax(mQuestionBank.length);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);

        if (savedInstanceState == null) {
            reset(0, 0);
            mDialogShown = false;
        } else {
            reset(savedInstanceState.getInt(INDEX), savedInstanceState.getInt(SCORE));
            mDialogShown = savedInstanceState.getBoolean(DIALOG_SHOWN);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(INDEX, mIndex);
        bundle.putInt(SCORE, mTotalScore);
        bundle.putBoolean(DIALOG_SHOWN, mDialogShown);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mDialog != null && mDialog.isShowing()) {
            mDialogShown = true;
            mDialog.cancel();
        } else {
            mDialogShown = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mDialogShown) {
            setDialog();
        }
    }

    private void updateTotalScore(final boolean userInput) {
        if (mIndex >= mQuestionBank.length) {
            return;
        }

        if (mQuestionBank[mIndex].getmAnswer() == userInput) {
            ++mTotalScore;
        }
    }

    private void setNextQuestion() {
        ++mIndex;
        if (mIndex >= mQuestionBank.length) {
            mIndex = mQuestionBank.length;
            setDialog();
            return;
        }
        mQuestionTextView.setText(mQuestionBank[mIndex].getmQuestionID());
        mProgressBar.setProgress(mIndex+1);
    }

    private void setScoreTextView() {
        final String scoreText = getString(R.string.current_score) +
                " " + mTotalScore + "/" + mQuestionBank.length;
        mScoreTextView.setText(scoreText);
    }

    private void reset(final int index, final int score) {
        mIndex = index;
        mTotalScore = score;
        mProgressBar.setProgress(mIndex);
        final String scoreText = getString(R.string.initial_score) + " " + mTotalScore + "/" + mQuestionBank.length;
        mScoreTextView.setText(scoreText);
        if (mIndex < mQuestionBank.length) {

        } else {
            mQuestionTextView.setText(mQuestionBank[mQuestionBank.length-1].getmQuestionID());
        }
    }

    private void setDialog() {
        final String dialogText = getString(R.string.final_score) + " " + mTotalScore;
        mDialog = new AlertDialog.Builder(this).create();
        mDialog.setTitle(R.string.result_dialog_title);
        mDialog.setCancelable(false);
        mDialog.setMessage(dialogText);
        mDialog.setButton(
                AlertDialog.BUTTON_POSITIVE,
                getString(R.string.result_dialog_retake),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reset(0, 0);
                    }
                });
        mDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE,
                getString(R.string.result_dialog_exit),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }
        );
        mDialog.show();
        mDialogShown = true;
    }
}
