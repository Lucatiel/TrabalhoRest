package br.edu.ifpb.trabalhorest;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Arthur on 23/12/2015.
 */
public interface ListMethods {

    @POST("servicoservlet")
    Call<Object> login(@Body Usuario usuario);

}
