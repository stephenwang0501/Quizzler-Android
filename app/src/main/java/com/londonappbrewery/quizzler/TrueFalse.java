package com.londonappbrewery.quizzler;

public class TrueFalse {
    private int mQuestionID;
    private boolean mAnswer;

    public TrueFalse(final int mQuestionID, final boolean mAnswer) {
        this.mAnswer = mAnswer;
        this.mQuestionID = mQuestionID;
    }

    public int getmQuestionID() {
        return mQuestionID;
    }

    public boolean getmAnswer() {
        return mAnswer;
    }

    public void setmQuestionID(int mQuestionID) {
        this.mQuestionID = mQuestionID;
    }

    public void setmAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }
}
