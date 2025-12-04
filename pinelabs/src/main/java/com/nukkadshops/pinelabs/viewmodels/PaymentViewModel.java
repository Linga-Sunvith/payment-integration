package com.nukkadshops.pinelabs.viewmodels;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nukkadshops.pinelabs.data.models.CancelRequest;
import com.nukkadshops.pinelabs.data.models.CancelResponse;
import com.nukkadshops.pinelabs.data.models.StatusRequest;
import com.nukkadshops.pinelabs.data.models.StatusResponse;
import com.nukkadshops.pinelabs.data.models.UploadRequest;
import com.nukkadshops.pinelabs.data.models.UploadResponse;
import com.nukkadshops.pinelabs.data.models.VoidRequest;
import com.nukkadshops.pinelabs.data.models.VoidResponse;
import com.nukkadshops.pinelabs.data.repository.PaymentRepository;
import com.nukkadshops.pinelabs.sdk.PaymentConfig;
import com.nukkadshops.pinelabs.sdk.PaymentState;
import com.nukkadshops.pinelabs.sdk.VoidState;

public class PaymentViewModel extends ViewModel {

    public MutableLiveData<PaymentState> state = new MutableLiveData<>(PaymentState.IDLE);
    public MutableLiveData<Long> ptr = new MutableLiveData<>(0L);

    public MutableLiveData<VoidState> capital =
            new MutableLiveData<>(VoidState.IDLE);

    public MutableLiveData<UploadResponse> uploadResponse = new MutableLiveData<>();
    public MutableLiveData<StatusResponse> statusResponse = new MutableLiveData<>();
    public MutableLiveData<CancelResponse> cancelResponse = new MutableLiveData<>();
    public MutableLiveData<VoidResponse> voidResponse = new MutableLiveData<>();

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable poller;

    PaymentRepository repo;
    PaymentConfig cfg;

    public PaymentViewModel(PaymentConfig c) {
        cfg = c;
        repo = new PaymentRepository(c);
    }

    public void uploadPayment(UploadRequest request) {
        state.setValue(PaymentState.UPLOADING);
        repo.upload(request, res -> {
            if (res != null) {
                ptr.postValue(res.ptr);
                repo.startStatusPolling(res.ptr, statusResponse);
                state.postValue(PaymentState.UPLOAD_DONE);
            } else {
                state.postValue(PaymentState.ERROR);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repo.stopStatusPolling();
    }

    public void cancelPayment(CancelRequest req) {
        state.setValue(PaymentState.CANCELING);
        repo.cancel(req, result -> {
            cancelResponse.postValue(result);
            if (result != null) {
                state.postValue(PaymentState.CANCEL_DONE);
            } else {
                state.postValue(PaymentState.ERROR);
            }
        });
    }

    public void voidPayment(VoidRequest req) {
        capital.postValue(VoidState.PROCESSING);
        repo.voidTransaction(req, result -> {
            voidResponse.postValue(result);
            if (result != null) {
                capital.postValue(VoidState.APPROVED);
            } else {
                capital.postValue(VoidState.FAILED);
            }
        });
    }

    public MutableLiveData<Long> getPtrLiveData() {
        return ptr;
    }

    public MutableLiveData<UploadResponse> getUploadResponse() {
        return uploadResponse;
    }

    public MutableLiveData<StatusResponse> getStatusResponse() {
        return statusResponse;
    }

    public MutableLiveData<CancelResponse> getCancelResponse() {
        return cancelResponse;
    }
}
