package com.androidlab.shiji.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.Msg;
import com.androidlab.shiji.bean.User;
import com.androidlab.shiji.utils.StaticVariable;
import com.androidlab.shiji.utils.WebUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email)
    EditText mEmailText;
    @InjectView(R.id.input_password)
    EditText mPasswordText;
    @InjectView(R.id.btn_login)
    Button mLoginButton;
    @InjectView(R.id.link_signup)
    TextView mSignupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        mEmailText.setText(User.INSTANCE.Email);
        mPasswordText.setText(User.INSTANCE.Password);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        mLoginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        OkHttpClient client = new OkHttpClient();
        // 这里就不加密传输了
        client.newCall(new Request.Builder()
                .url("http://39.105.110.28:8000/user/register")
                .post(new FormBody.Builder()
                        .add("Email", email)
                        .add("Password", password)
                        .build())
                .build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        onLoginFailed();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onLoginFailed();
                                    progressDialog.dismiss();
                                }
                            });
                            return;
                        }

                        Msg msg = WebUtils.msgGetter(response.body().string());
                        if (msg.code != 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onLoginFailed();
                                    progressDialog.dismiss();
                                }
                            });
                        }

                        User.INSTANCE.Id = msg.data.getInt("Id");
                        User.INSTANCE.Name = msg.data.getString("Name");
                        User.INSTANCE.Email = msg.data.getString("Email");
                        User.INSTANCE.Password = msg.data.getString("Password");
                        StaticVariable.isLogin = true;

                        setResult(RESULT_OK, null);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onLoginSuccess();
                                progressDialog.dismiss();
                            }
                        });

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                setResult(RESULT_OK, null);
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        super.onBackPressed();
    }

    public void onLoginSuccess() {
        mLoginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        mLoginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        return valid;
    }
}
