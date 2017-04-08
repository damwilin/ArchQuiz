package com.wili.android.archquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText nameField;
    private EditText question6;
    private RadioGroup questionGroup1;
    private RadioGroup questionGroup3;
    private RadioGroup questionGroup4;
    private RadioGroup questionGroup5;
    private CheckBox question2_a1;
    private CheckBox question2_a2;
    private CheckBox question2_a3;
    private CheckBox question2_a4;
    private Button submitButton;
    private String userName;
    private double userPoints = 0;
    private final int amountOfMaxPoints = 6;
    private ArrayList<String> radioGroupsCorrectAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting Views
        questionGroup1 = (RadioGroup) findViewById(R.id.radio_group1);
        questionGroup3 = (RadioGroup) findViewById(R.id.radio_group3);
        questionGroup4 = (RadioGroup) findViewById(R.id.radio_group4);
        questionGroup5 = (RadioGroup) findViewById(R.id.radio_group5);
        question2_a1 = (CheckBox) findViewById(R.id.question2_a1);
        question2_a2 = (CheckBox) findViewById(R.id.question2_a2);
        question2_a3 = (CheckBox) findViewById(R.id.question2_a3);
        question2_a4 = (CheckBox) findViewById(R.id.question2_a4);
        nameField = (EditText) findViewById(R.id.name);
        question6 = (EditText) findViewById(R.id.question6_answer);
        submitButton = (Button) findViewById(R.id.submit_button);
        //setting correct answers for RadioGroups
        radioGroupsCorrectAnswers = new ArrayList<String>();
        radioGroupsCorrectAnswers.add(getString(R.string.question1_a1));
        radioGroupsCorrectAnswers.add(getString(R.string.question3_a3));
        radioGroupsCorrectAnswers.add(getString(R.string.question4_a2));
        radioGroupsCorrectAnswers.add(getString(R.string.question5_a4));
    }

    private int checkAnswersForRadioGroup(RadioGroup radioGroup) {
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            if (radioGroupsCorrectAnswers.contains(((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString()))
                userPoints++;
        } else
            return -1;
        return 0;
    }

    private int checkAnswersForQuestion2() {
        if (question2_a1.isChecked() && !question2_a2.isChecked() && question2_a3.isChecked() && question2_a4.isChecked())
            userPoints++;
        else if (!question2_a1.isChecked() && !question2_a2.isChecked() && !question2_a3.isChecked() && !question2_a4.isChecked())
            return -1;
        return 0;
    }

    private int checkAnswerForQuestion6() {
        String answer = question6.getText().toString();
        if (answer.equals(""))
            return -1;
        else {
            if (answer.trim().toLowerCase().equals(getString(R.string.question6_ok_answer)))
                userPoints++;
        }
        return 0;
    }

    private void getName() {
        userName = nameField.getText().toString();
    }

    private String composeAnswer() {
        String answer = "Congratulations " + userName + "\nYou got " + Math.round(userPoints / amountOfMaxPoints * 100) + " % of test.";
        return answer;
    }

    public void submitAnswers(View view) {
        userPoints = 0;
        getName();
        if (userName.equals("")) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_name), Toast.LENGTH_SHORT).show();
            return;
        } else {
            int answers = 0;
            answers += checkAnswersForRadioGroup(questionGroup1);
            answers += checkAnswersForQuestion2();
            answers += checkAnswersForRadioGroup(questionGroup3);
            answers += checkAnswersForRadioGroup(questionGroup4);
            answers += checkAnswersForRadioGroup(questionGroup5);
            answers += checkAnswerForQuestion6();
            if (answers < 0) {
                Toast.makeText(getApplicationContext(), getString(R.string.no_all_answers), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getApplicationContext(), composeAnswer(), Toast.LENGTH_SHORT).show();
        }
    }
}