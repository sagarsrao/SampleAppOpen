package sampleapp.wolken.com.sampleappopen;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.okta.appauth.android.OktaAppAuth;

import net.openid.appauth.AuthorizationException;

public class LoginActivity extends AppCompatActivity {



    private OktaAppAuth mOktaAuth;

    private Button mLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mOktaAuth = OktaAppAuth.getInstance(this);

        if (mOktaAuth.isUserLoggedIn()) {
            Log.i("LoginActivity", "User is already authenticated, proceeding to protected activity");
            startActivity(new Intent(this, ProtectedActivity.class));
            finish();
            return;
        }
        mLogin = findViewById(R.id.buttonLogin);

        // Do any of your own setup of the Activity

        mOktaAuth.init(
                this,
                new OktaAppAuth.OktaAuthListener() {
                    @Override
                    public void onSuccess() {
                        // Handle a successful initialization (e.g. display login button)

                        mLogin.setVisibility(View.VISIBLE);
                        mLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {





                                startAuth();

                            }
                        });




                    }

                    @Override
                    public void onTokenFailure(@NonNull AuthorizationException ex) {
                        // Handle a failed initialization
                    }
                }
        );
    }

    private void startAuth() {


        Intent completionIntent = new Intent(this, AuthorizedActivity.class);
        Intent cancelIntent = new Intent(this, LoginActivity.class);
        cancelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        mOktaAuth.login(
                this,
                PendingIntent.getActivity(this, 0, completionIntent, 0),
                PendingIntent.getActivity(this, 0, cancelIntent, 0)
        );
    }

}
