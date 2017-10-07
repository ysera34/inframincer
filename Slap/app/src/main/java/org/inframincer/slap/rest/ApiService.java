package org.inframincer.slap.rest;

import org.inframincer.slap.model.StatusObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yoon on 2017. 10. 7..
 */

public interface ApiService {

    @GET("{apiKey}/json/MosquitoStatus/{startIndex}/{endIndex}/{date}")
    Call<StatusObject> getStatus(@Path("apiKey") String apiKey, @Path("startIndex") int startIndex,
                                 @Path("endIndex") int endIndex, @Path("date") String date);
}
