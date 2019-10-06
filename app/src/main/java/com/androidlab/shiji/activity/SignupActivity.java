package com.androidlab.shiji.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.util.Log;
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
import com.androidlab.shiji.utils.LoggerUtil;
import com.androidlab.shiji.utils.StaticVariable;
import com.androidlab.shiji.utils.WebUtils;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name)
    EditText mNameText;
    @InjectView(R.id.input_email)
    EditText mEmailText;
    @InjectView(R.id.input_password)
    EditText mPasswordText;
    @InjectView(R.id.btn_signup)
    Button mSignupButton;
    @InjectView(R.id.link_login)
    TextView mLoginLink;

    private Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        gson = new Gson();


        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进行注册操作
                signup();
            }
        });

        mLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mSignupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = mNameText.getText().toString();
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        // TODO: 注册逻辑

        OkHttpClient client = new OkHttpClient();
        // 这里就不加密传输了
        client.newCall(new Request.Builder()
                .url("http://39.105.110.28:8000/user/register")
                .post(new FormBody.Builder()
                        .add("Name", name)
                        .add("Email", email)
                        .add("Password", password)
                        .build())
                .build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        onSignupFailed();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onSignupFailed();
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
                                    onSignupFailed();
                                    progressDialog.dismiss();
                                }
                            });
                        }

                        User.INSTANCE.Id = (byte) msg.data.getInt("Id");
                        User.INSTANCE.Name = msg.data.getString("Name");
                        User.INSTANCE.Email = msg.data.getString("Email");
                        User.INSTANCE.Password = msg.data.getString("Password");
                        StaticVariable.isLogin = true;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onSignupSuccess();
                                progressDialog.dismiss();
                            }
                        });

                    }
                });
    }


    public void onSignupSuccess() {
        mSignupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        mSignupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = mNameText.getText().toString();
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            mNameText.setError("at least 3 characters");
            valid = false;
        } else {
            mNameText.setError(null);
        }

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
