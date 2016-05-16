package hu.ait.android.saferide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @Bind(R.id.signUp_etEmail)
    EditText etEmail;
    @Bind(R.id.signUp_etPassword)
    EditText etPassword;
    @Bind(R.id.signUp_etConfPass)
    EditText etConfPass;
    @Bind(R.id.signUp_cbDriver)
    CheckBox cbDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.signUp_btnSignUp)
    public  void signUpClick(View v) {

        BackendlessUser newUser = new BackendlessUser();
        boolean validUser = false;

        // checks amherst email and password and sets it
        String pass = etPassword.getText().toString();
        String confPass = etConfPass.getText().toString();
        if (etEmail.getText().toString().endsWith("amherst.edu") && pass.equals(confPass)) {
            newUser.setEmail(etEmail.getText().toString());
            newUser.setProperty("userName", etEmail.getText().toString());
            newUser.setProperty("driver", cbDriver.isChecked());
            newUser.setPassword(pass);
            validUser = true;
        } else {
            Toast.makeText(SignUpActivity.this, "Must be an Amherst email or passwords do not match", Toast.LENGTH_SHORT).show();
        }

        // if user is valid, it adds it to backendless
        // goes to main activity
        if (validUser) {
            Backendless.UserService.register(newUser, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    Toast.makeText(SignUpActivity.this, "REGISTRATION OK", Toast.LENGTH_SHORT).show();
                    // goes to main activity
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(SignUpActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
