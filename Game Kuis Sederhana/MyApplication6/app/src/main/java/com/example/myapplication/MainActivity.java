package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView totalQuestionsTextView;
    TextView question;
    Button ansA,ansB,ansC,ansD;
    Button submitBtn;

    int score =0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    int clickState =0;
    String selectedAnswer ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalQuestionsTextView = findViewById(R.id.totalQuestionsTextView);
        question = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);


        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        totalQuestionsTextView.setText("Total Questions :" +totalQuestion);

        loadNewQuestion();
    }

     void loadNewQuestion() {
        if(currentQuestionIndex == QuestionAnswer.question.length){
            finishQuiz();
        }
        else {
            question.setText(QuestionAnswer.question[currentQuestionIndex]);
            ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
            ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
            ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
            ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
        }

    }

     void finishQuiz() {
        String passState ="";
        if(score > totalQuestion*0.60){
            passState="Berhasil";
        }else{
            passState="Gagal";
        }
        new AlertDialog.Builder(this)
                .setTitle(passState)
                .setMessage("Kamu berhasil menjawab "+score+" dari "+totalQuestion+" pertanyaan")
                .setPositiveButton("Restart",((dialogInterface, i) -> restartQuiz()))
                .setNegativeButton("Keluar",((dialogInterface, i) ->  startActivity(new Intent(MainActivity.this,utama.class))))
                .setCancelable(false)
                .show();
    }

     void restartQuiz() {
        score =0;
        currentQuestionIndex =0;
        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);
    Button clickedButton = (Button) view;
    if(clickedButton.getId()==R.id.submit_btn) {
        if (clickState == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Gagal")
                    .setMessage("Mohon pilih salah satu jawaban!")
                    .setPositiveButton("Oke", ((dialogInterface, i) -> dialogInterface.dismiss()))
                    .setCancelable(false)
                    .show();
        } if(clickState == 1) {
            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;
            }
            if(currentQuestionIndex == QuestionAnswer.question.length){
                finishQuiz();
                clickState =0;
            }
            if(currentQuestionIndex <QuestionAnswer.question.length){
                currentQuestionIndex++;
                loadNewQuestion();
                clickState =0;
            }

        }
        }else {
        clickState = 1;
        selectedAnswer = clickedButton.getText().toString();
        clickedButton.setBackgroundColor(Color.MAGENTA);
    }
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

}