package com.github.dukekan.cubadocker.util;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

/**
 * @author d.kuznetsov
 * @version $Id$
 */
public interface ScalarService {
    @GET()
    @Headers("Authorization: Bearer perm:ZC5rdXpuZXRzb3Y=.ZC5rdXpuZXRzb3YgZm9yIGludGVncmF0aW9uIGRvY2tlciA8LT4geW91dHJhY2s=.SKhkfNy4XqwMpkNWa1aTYHEMUpDE6K")
    Call<ResponseBody> getStringResponse(@Url String url);
}