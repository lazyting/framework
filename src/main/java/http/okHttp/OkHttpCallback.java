package http.okHttp;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

public abstract class OkHttpCallback implements Callback {
    public static Response response;

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        OkHttpCallback.response = response;
    }
}
