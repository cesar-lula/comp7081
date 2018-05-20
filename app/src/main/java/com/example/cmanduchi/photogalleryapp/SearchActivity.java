package com.example.cmanduchi.photogalleryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    EditText latUp;
    EditText latDown;
    EditText lonLeft;
    EditText lonRight;
    EditText caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        latDown = (EditText) findViewById(R.id.latDownEditText);
        latUp = (EditText) findViewById(R.id.latUpEditText);
        lonLeft = (EditText) findViewById(R.id.lonLeftEditText);
        lonRight = (EditText) findViewById(R.id.lonRightEditText);
        caption = (EditText) findViewById(R.id.captionEditText);



    }

    public void searchPics(View view) {

        String latUpText = latUp.getText().toString();
        String latDownText = latDown.getText().toString();
        String lonLeftText = lonLeft.getText().toString();
        String lonRightText = lonRight.getText().toString();
        String captionText = caption.getText().toString();

        //return this information to the caller

        Intent output = new Intent();
        output.putExtra("latDown", latDownText);
        output.putExtra("latUp", latUpText);
        output.putExtra("lonLeft", lonLeftText);
        output.putExtra("lonRight", lonRightText);
        output.putExtra("caption", captionText);


        setResult(RESULT_OK, output);
        finish();

    }
}
