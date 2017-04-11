package galileo.android.myflashcards.model;

import java.io.Serializable;

/**
 * Created by Agro on 11/04/2017.
 */

public class FlashCard implements Serializable{
    private String mQuestion;
    private String mAnswer;

    public FlashCard(String question, String answer) {
        mQuestion = question;
        mAnswer = answer;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }
}
