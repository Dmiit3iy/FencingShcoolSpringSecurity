package org.dmiit3iy.retrofit;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserService {
//    @GET(".")
//    Call<ResponseResult<User>> getByLoginAndPassword(@Query("login") String login, @Query("password") String password);
    @GET(".")
    Call<ResponseResult<User>> get();

    @GET("{id}")
    Call<ResponseResult<User>> get(@Path("id") long id);

    @DELETE("{id}")
    Call<ResponseResult<User>> delete(@Path("id") long id);

    @POST(".")
    Call<ResponseResult<User>> post (@Body User user);
    @PATCH("{id}")
    Call<ResponseResult<User>> patch(@Path("id") long id, @Query("role") String role);
//    @POST(".")
//    Call<ResponseResult<User>> post (@Header("Authorization") String credentials,@Body User user);
}


