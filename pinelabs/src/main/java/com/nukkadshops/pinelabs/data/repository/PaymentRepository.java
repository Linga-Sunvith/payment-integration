package com.nukkadshops.pinelabs.data.repository;

import com.nukkadshops.pinelabs.data.models.CancelRequest;
import com.nukkadshops.pinelabs.data.models.CancelResponse;
import com.nukkadshops.pinelabs.data.models.StatusRequest;
import com.nukkadshops.pinelabs.data.models.StatusResponse;
import com.nukkadshops.pinelabs.data.models.UploadRequest;
import com.nukkadshops.pinelabs.data.models.UploadResponse;
import com.nukkadshops.pinelabs.data.models.VoidRequest;
import com.nukkadshops.pinelabs.data.models.VoidResponse;
import com.nukkadshops.pinelabs.data.network.ApiClient;
import com.nukkadshops.pinelabs.data.network.ApiService;
import com.nukkadshops.pinelabs.sdk.PaymentConfig;

import retrofit2.Callback;

public class PaymentRepository {
    ApiService apiService;

    public PaymentRepository(PaymentConfig config) {
        apiService = ApiClient.getClient(config).create(ApiService.class);
    }

    public void upload(UploadRequest request, Callback<UploadResponse> callback) {
        apiService.uploadResponseCall(request).enqueue(callback);
    }

    public void status(StatusRequest requests, Callback<StatusResponse> callback) {
        apiService.statusResponseCall(requests).enqueue(callback);
    }

    public void cancel(CancelRequest request, Callback<CancelResponse> callback) {
        apiService.cancelResponseCall(request).enqueue(callback);
    }
    public void voidTransaction(VoidRequest request, Callback<VoidResponse> callback) {
        apiService.voidResponseCall(request).enqueue(callback);
    }
}
