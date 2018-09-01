package sampleapp.wolken.com.sampleappopen;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface APInterface {

    @FormUrlEncoded
    @POST("account_service/oktaoidcauth")
    Call<ResponseOpenIdAuth> openIdAuthConnect(@Field("id_token") String id_token);
}
