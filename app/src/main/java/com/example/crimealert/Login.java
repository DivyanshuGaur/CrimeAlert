package com.example.crimealert;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class Login extends AppCompatActivity {


    private EditText userMail, userPassword;

    private ProgressDialog progressDialog;
    private Button btnLogin;
    private ProgressBar loginProgress;
    private Intent HomeActivity;
    private ImageView loginPhoto;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userMail = findViewById(R.id.login_mail);
        userPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.loginBtn);
        loginPhoto = findViewById(R.id.login_photo);



//        loginProgress.setVisibility(View.INVISIBLE);
    }

    public void auth(View view) {
        int i = 0;



        final String mail = userMail.getText().toString();
        final String password = userPassword.getText().toString();


        if (mail.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, " invalid credentials ", Toast.LENGTH_SHORT).show();
            btnLogin.setVisibility(View.VISIBLE);
        } else {
            if (mail.equals("1") && password.equals("1")) {
                Toast.makeText(this, "Login Succesful", Toast.LENGTH_SHORT).show();
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
                btnLogin.startAnimation(animation);


                Intent intent = new Intent(Login.this, Main3Activity.class);
                startActivity(intent);

            }

        }


    }


    private class LongOperation extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setTitle("Processing...");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(true);
            progressDialog.show();

            int i=0;
            i++;


            progressBar.setProgress(i);

        }

        @SuppressLint("WrongThread")
        protected String doInBackground(String... params) {
            try {
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String mail = userMail.getText().toString();
                        final String password = userPassword.getText().toString();


                        if (mail.isEmpty() || password.isEmpty()) {
                            Toast.makeText(getApplicationContext(), " invalid credentials ", Toast.LENGTH_SHORT).show();
                            btnLogin.setVisibility(View.VISIBLE);
                        }

                        else {
                            if (mail.equals("1") && password.equals("1")) {
                                Toast.makeText(getApplicationContext(), "Login Succesful", Toast.LENGTH_SHORT).show();
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
                                btnLogin.startAnimation(animation);


                            }
                        }


            }  });
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {

            progressDialog.dismiss();
            Intent n = new Intent(getApplicationContext(), Login.class);
            startActivity(n);
        }
    }

}




