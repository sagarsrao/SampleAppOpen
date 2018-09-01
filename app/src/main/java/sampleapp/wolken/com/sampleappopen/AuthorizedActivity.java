package sampleapp.wolken.com.sampleappopen;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.okta.appauth.android.AuthStateManager;
import com.okta.appauth.android.OktaAppAuth;

import net.openid.appauth.AuthorizationException;

import org.json.JSONObject;

public class AuthorizedActivity extends AppCompatActivity {

    private OktaAppAuth mOktaAuth;

    private Button loginBradcom;

    protected AuthStateManager mAuthStateManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOktaAuth = OktaAppAuth.getInstance(this);
        mAuthStateManager = AuthStateManager.getInstance(this);
        setContentView(R.layout.activity_authorized);

        loginBradcom = findViewById(R.id.bt_login);

        loginBradcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchUserInfo();

            }
        });



    }


    /*fetches user information*/
    private void fetchUserInfo() {
        mOktaAuth.getUserInfo(new OktaAppAuth.OktaAuthActionCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                // Do whatever you need to do with the user info data
                Log.d("Okta_User_Response","Okta_User_Response"+response.toString());
                Log.d("OktaToken",""+mOktaAuth.hasIdToken());
                Log.d("ResponseHeaders","IdToken"+mAuthStateManager.getCurrent().getIdToken());
            }

            @Override
            public void onTokenFailure(@NonNull AuthorizationException ex) {
                // Handle an error with the Okta authorization and tokens
            }

            @Override
            public void onFailure(int httpResponseCode, Exception ex) {
                // Handle a network error when fetching the user info data
            }
        });
    }


}
