package com.example.drinksmore3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginClass extends AppCompatActivity {
    private EditText email,password;
    private FirebaseAuth mAuth;
    Button login,signup;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_class);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.username);
        loadingbar=new ProgressDialog(this);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.lsignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginClass.this,SignUpClass.class);
                startActivity(intent);


            }
        });
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });


    }
    private void login()
    {
        String emailvalue=email.getText().toString();
        String passwordvalue=password.getText().toString();

        if (emailvalue.isEmpty() || passwordvalue.isEmpty())
        {
            Toast.makeText(LoginClass.this, "Please Enter All The Fields", Toast.LENGTH_SHORT).show();


        }

        else
        {

            loadingbar.setTitle("Please Wait");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);
            mAuth.signInWithEmailAndPassword(emailvalue,passwordvalue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(LoginClass.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginClass.this,MainActivity.class);
                        startActivity(intent);
                        loadingbar.dismiss();
                    }
                    else
                    {
                        String message= task.getException().getMessage();
                        Toast.makeText(LoginClass.this,"Error Occur\n"+message,Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }

                }
            });


        }

    }

    }

