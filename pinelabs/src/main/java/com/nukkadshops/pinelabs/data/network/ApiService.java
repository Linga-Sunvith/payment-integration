package com.nukkadshops.pinelabs.data.network;

import com.nukkadshops.pinelabs.data.models.CancelRequest;
import com.nukkadshops.pinelabs.data.models.CancelResponse;
import com.nukkadshops.pinelabs.data.models.StatusRequest;
import com.nukkadshops.pinelabs.data.models.StatusResponse;
import com.nukkadshops.pinelabs.data.models.UploadRequest;
import com.nukkadshops.pinelabs.data.models.UploadResponse;
import com.nukkadshops.pinelabs.data.models.VoidRequest;
import com.nukkadshops.pinelabs.data.models.VoidResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/payment/pinelabs/upload")
    Call<UploadResponse> uploadResponseCall(@Body UploadRequest request);

    @POST("api/payment/pinelabs/status")
    Call<StatusResponse> statusResponseCall(@Body StatusRequest request);

    @POST("api/payment/pinelabs/forceCancel")
    Call<CancelResponse> cancelResponseCall(@Body CancelRequest request);

    @POST("api/payment/pinelabs/void")
    Call<VoidResponse> voidResponseCall(@Body VoidRequest request);
}
