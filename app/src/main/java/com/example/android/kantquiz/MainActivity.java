package com.example.android.kantquiz;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private int questionOneScore = 0;
    private int questionTwoScore = 0;
    private int questionThreeScore = 0;
    private int questionFourScore = 0;
    private int questionFiveScore = 0;
    private int totalPoints = 6;

    private CheckBox q1a1;
    private CheckBox q1a2;
    private CheckBox q1a3;
    private CheckBox q1a4;

    private RadioButton q2a1;
    private RadioButton q2a2;
    private RadioButton q2a3;

    private RadioButton q3a1;
    private RadioButton q3a2;

    private EditText q4Answer;

    private EditText q5Answer;


    public static final String EXPORT_ANSWERS = "com.example.android.kantquiz.SCORES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        q1a1 = (CheckBox) findViewById(R.id.q1_a1);
        q1a2 = (CheckBox) findViewById(R.id.q1_a2);
        q1a3 = (CheckBox) findViewById(R.id.q1_a3);
        q1a4 = (CheckBox) findViewById(R.id.q1_a4);

        q2a1 = (RadioButton) findViewById(R.id.q2_a1);
        q2a2 = (RadioButton) findViewById(R.id.q2_a2);
        q2a3 = (RadioButton) findViewById(R.id.q2_a3);

        q3a1 = (RadioButton) findViewById(R.id.q3_a1);
        q3a2 = (RadioButton) findViewById(R.id.q3_a2);

        q4Answer = (EditText) findViewById(R.id.q4_answer);

        q5Answer = (EditText) findViewById(R.id.q5_answer);
    }

    private void calculateQuestionTwo() {
        if (q2a1.isChecked()) {
            questionTwoScore = 1;
        } else {
            questionTwoScore = 0;
        }
    }

    public void questionTwoClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.q2_a1:
                if (checked) {
                    questionTwoScore++;
                }
                break;
            case R.id.q2_a2:
                if (checked) {
                    if (questionTwoScore > 0) {
                        questionTwoScore--;
                    }
                }
                break;
            case R.id.q2_a3:
                if (checked) {
                    if (questionTwoScore > 0) {
                        questionTwoScore--;
                    }
                }
                break;
        }
    }

    public void questionThreeClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.q3_a1:
                if (checked) {
                    if (questionThreeScore > 0) {
                        questionThreeScore--;
                    }
                }
                break;
            case R.id.q3_a2:
                if (checked) {
                    questionThreeScore++;
                }
                break;
        }
    }

    private void calculuateQuestionOne() {
        // Check for correct answers first, then deduct points for incorrect answers.
        if (q1a3.isChecked()) {
            questionOneScore++;
        }
        if (q1a4.isChecked()) {
            questionOneScore++;
        }
        // Now deduct points for incorrect answers, but don't go below 0.
        if (q1a1.isChecked()) {
            if (questionOneScore > 0) {
                questionOneScore--;
            }
        }
        if (q1a2.isChecked()) {
            if (questionOneScore > 0) {
                questionOneScore--;
            }
        }
    }

    private void calculateQuestionThree() {
        if (q3a2.isChecked()) {
            questionThreeScore = 1;
        } else {
            questionThreeScore = 0;
        }
    }

    private void calculateQuestionFour() {
        // Text answers receive full credit for submitting any text.
        String questionFourAnswer = q4Answer.getText().toString();
        if (questionFourAnswer.matches("")) {
            questionFourScore = 0;
        } else {
            questionFourScore = 1;
        }
    }

    private void calculateQuestionFive() {
        // Again, the text answer receives full credit for submitting any text.
        String questionFiveAnswer = q5Answer.getText().toString();
        if (questionFiveAnswer.matches("")) {
            questionFiveScore = 0;
        } else {
            questionFiveScore = 1;
        }
    }

    private String calculateAnswers() {
        calculuateQuestionOne();
        calculateQuestionTwo();
        calculateQuestionThree();
        calculateQuestionFour();
        calculateQuestionFive();
        String summary = getString(R.string.questionOneSummary, questionOneScore);
        summary += "\n" + getString(R.string.questionTwoSummary, questionTwoScore);
        summary += "\n" + getString(R.string.questionThreeSummary, questionThreeScore);
        summary += "\n" + getString(R.string.questionFourSummary, questionFourScore);
        summary += "\n" + getString(R.string.questionFiveSummary, questionFiveScore);
        int overallScore = questionOneScore + questionTwoScore + questionThreeScore + questionFourScore + questionFiveScore;
        summary += "\n" + getString(R.string.finalSummary, overallScore, totalPoints);
        return summary;
    }

    public void submitAnswers(View view) {
        String summary = calculateAnswers();
        exportAnswers(summary);
    }

    public void exportAnswers(String summary) {
        Intent intent = new Intent(this, DisplayAnswers.class);
        intent.putExtra(EXPORT_ANSWERS, summary);
        startActivity(intent);
    }

}
