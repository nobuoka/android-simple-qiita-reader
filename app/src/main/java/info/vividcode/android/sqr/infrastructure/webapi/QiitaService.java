package info.vividcode.android.sqr.infrastructure.webapi;

import java.util.List;

import info.vividcode.android.sqr.dto.QiitaItem;
import retrofit2.Call;
import retrofit2.http.GET;

public interface QiitaService {

    @GET("/api/v2/items")
    Call<List<QiitaItem>> getItems();

}
