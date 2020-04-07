package com.example.drinksmore3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class SignUpClass extends AppCompatActivity {
    private EditText email,password,number,rpassword;
    Button signup;
    private ProgressDialog loadingbar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_class);

        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.username);
        loadingbar=new ProgressDialog(this);
        password=findViewById(R.id.password);
        rpassword=findViewById(R.id.rpassword);
        number=findViewById(R.id.number);
        signup=findViewById(R.id.ssignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || rpassword.getText().toString().isEmpty() || number.getText().toString().isEmpty())
                {
                    Toast.makeText(SignUpClass.this,"Please Enter All The Fields",Toast.LENGTH_SHORT).show();
                }

                else {

                    if (password.getText().toString().equals(rpassword.getText().toString()))
                    {
                        creatNewAccount();
                    }
                    else
                    {
                        Toast.makeText(SignUpClass.this,"Password must be matched",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser != null)
        {
            Intent intent=new Intent(SignUpClass.this,BuyerSeller.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }
    private void creatNewAccount()
    {
        loadingbar.setMessage("Please Wait");
        loadingbar.show();
        loadingbar.setCanceledOnTouchOutside(true);

        final String emailValue = email.getText().toString();
        final String passwordValue = password.getText().toString();
        final String numerValue = number.getText().toString();
        mAuth.createUserWithEmailAndPassword(emailValue,passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                if(task.isSuccessful())
                {
                    Toast.makeText(SignUpClass.this,"Signup Successfull",Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Intent intent=new Intent(SignUpClass.this,BuyerSeller.class);
                    startActivity(intent);
                }
                else
                {
                   String message= task.getException().getMessage();
                   Toast.makeText(SignUpClass.this,"Error Occur\n"+message,Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                }
            }
        });
    }
}
