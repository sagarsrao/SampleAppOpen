package sampleapp.wolken.com.sampleappopen;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.okta.appauth.android.AuthStateManager;
import com.okta.appauth.android.OktaAppAuth;

import net.openid.appauth.AuthorizationException;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizedActivity extends AppCompatActivity {

    private OktaAppAuth mOktaAuth;

    private Button loginBradcom;

    protected AuthStateManager mAuthStateManager;

    private ProgressBar progressBar;


    APInterface apInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOktaAuth = OktaAppAuth.getInstance(this);
        mAuthStateManager = AuthStateManager.getInstance(this);
        setContentView(R.layout.activity_authorized);

        progressBar = findViewById(R.id.progress_bar);
        loginBradcom = findViewById(R.id.bt_login);

        loginBradcom.setVisibility(View.INVISIBLE);

        apInterface = APIClient.getClient().create(APInterface.class);



        /*Run the progress bar for a time period*/
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                progressBar.setVisibility(View.GONE);
                fetchUserInfo();
                fetchJwt();
                startActivity(new Intent(getApplicationContext(), ProtectedActivity.class));
            }

        }, 10);


    }


    private void fetchJwt() {

        try {
            Call<ResponseOpenIdAuth> call = apInterface.openIdAuthConnect(mAuthStateManager.getCurrent().getIdToken());
            call.enqueue(new Callback<ResponseOpenIdAuth>() {
                @Override
                public void onResponse(Call<ResponseOpenIdAuth> call, Response<ResponseOpenIdAuth> response) {

                    if (response.isSuccessful()) {


                        Log.d(getApplicationContext().toString(), response.code() + "Awesome authenticated" + response.toString());
                        Log.d("COOKIESPLEASE", "" + response.headers().get("Set-Cookie"));


                        String displayResponse = "";
                        ResponseOpenIdAuth responseOpenIdAuth = response.body();

                        String successMessage = responseOpenIdAuth.getStatus();
                        String message = responseOpenIdAuth.getMessage();
                        responseOpenIdAuth.setCookie(response.headers().get("Set-Cookie"));
                        Log.d("ResposiveCookie", "" + responseOpenIdAuth.getCookie().substring(7, 339));


                        Toast.makeText(getApplicationContext(), "SucessMessage" + successMessage + "Message" + message, Toast.LENGTH_LONG).show();

                        displayResponse = "Success" + successMessage + "message" + message;
                        Log.d("DisplayResponse", "" + displayResponse);


                    } else {

                    }


                }

                @Override
                public void onFailure(Call<ResponseOpenIdAuth> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*fetches user information*/
    private void fetchUserInfo() {
        mOktaAuth.getUserInfo(new OktaAppAuth.OktaAuthActionCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                // Do whatever you need to do with the user info data
                Log.d("Okta_User_Response", "Okta_User_Response" + response.toString());
                Log.d("OktaToken", "" + mOktaAuth.hasIdToken());
                Log.d("ResponseHeaders", "IdToken" + mAuthStateManager.getCurrent().getIdToken());
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


    /*Hit the wolken api*/


}
