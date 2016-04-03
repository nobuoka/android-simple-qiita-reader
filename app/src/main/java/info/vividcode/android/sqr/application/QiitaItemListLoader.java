package info.vividcode.android.sqr.application;

import java.util.List;

import info.vividcode.android.sqr.dto.QiitaItem;
import rx.Single;

public interface QiitaItemListLoader {

    Single<List<QiitaItem>> getQiitaItemList(int page);

}
