package com.yilmazgokhan.contact.RetrofitApi;

import com.yilmazgokhan.contact.HelperClass.ApiAnswer;
import com.yilmazgokhan.contact.HelperClass.ApiToken;
import com.yilmazgokhan.contact.HelperClass.Contact;
import com.yilmazgokhan.contact.HelperClass.ContactListInfo;
import com.yilmazgokhan.contact.HelperClass.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRetrofitApi {

    /*for Log in on LoginActivity*/
    @POST("auth")
    Call<ApiToken> Login(@Body User user);

    /*for register on RegisterActivity*/
    @POST("users")
    Call<ApiToken> Register(@Body User user);

    /*for list contacts on MainActivity*/
    @GET("contacts")
    Call<ContactListInfo> GetUsers(
            @Query("page") int pageId,
            @Header("x-auth-token") String authToken);

    /*for a contact on EditContactPresenter*/
    @GET("contacts/{id}")
    Call<Contact> GetTheUser(
            @Path("id") String userId,
            @Header("x-auth-token") String authToken);

    /*for add new contact on AddContactActivity*/
    @POST("contacts")
    Call<Contact> CreateNewContact(
            @Header("x-auth-token") String authToken,
            @Body Contact contact);

    @DELETE("contacts/{id}")
    Call<ApiAnswer> DeleteContact(
            @Path("id") String userId,
            @Header("x-auth-token") String authToken);

    @PUT("contacts/{id}")
    Call<ApiAnswer> UpdateContact(
            @Path("id") String userId,
            @Header("x-auth-token") String authToken,
            @Body Contact user);

}
