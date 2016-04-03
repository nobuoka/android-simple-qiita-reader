package info.vividcode.android.sqr;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import info.vividcode.android.sqr.databinding.ActivityMainBinding;
import info.vividcode.android.sqr.di.AppComponent;
import info.vividcode.android.sqr.di.DaggerAppComponent;
import info.vividcode.android.sqr.di.QiitaWebApiModule;
import info.vividcode.android.sqr.dto.QiitaItem;
import info.vividcode.android.sqr.presentation.presenters.QiitaItemListAdapter;
import info.vividcode.android.sqr.presentation.models.NextPageExistence;
import info.vividcode.android.sqr.presentation.models.QiitaItemListPresentationModel;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_KEY_QIITA_ITEMS = "qiita-items";
    private static final String STATE_KEY_QIITA_ITEMS_CURRENT_PAGE = "qiita-items-current-page";
    private static final String STATE_KEY_QIITA_ITEMS_NEXT_PAGE_EXISTENCE =
            "qiita-items-next-page-existence";

    QiitaItemListPresentationModel mModel;

    QiitaItemListAdapter mAdapter;

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        mModel.saveInstanceState(new QiitaItemListPresentationModel.InstanceStateSaver() {
            @Override
            public void save(List<QiitaItem> items, int currentPage, NextPageExistence nextPageExistence) {
                outState.putSerializable(STATE_KEY_QIITA_ITEMS, new ArrayList<>(items));
                outState.putInt(STATE_KEY_QIITA_ITEMS_CURRENT_PAGE, currentPage);
                outState.putSerializable(STATE_KEY_QIITA_ITEMS_NEXT_PAGE_EXISTENCE, nextPageExistence);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent appComponent = DaggerAppComponent.builder()
                .qiitaWebApiModule(QiitaWebApiModule.createFormalInstance())
                .build();
        mModel = appComponent.qiitaItemListPresentationModel();
        if (savedInstanceState != null) {
            @SuppressWarnings("unchecked")
            List<QiitaItem> items = (List<QiitaItem>) savedInstanceState.getSerializable(STATE_KEY_QIITA_ITEMS);
            int currentPage = savedInstanceState.getInt(STATE_KEY_QIITA_ITEMS_CURRENT_PAGE);
            NextPageExistence nextPageExistence = (NextPageExistence) savedInstanceState.getSerializable(
                    STATE_KEY_QIITA_ITEMS_NEXT_PAGE_EXISTENCE);
            mModel.restoreInstanceState(items, currentPage, nextPageExistence);
        }

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mAdapter = new QiitaItemListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.getQiitaItemListReferenceComponent().setList(mModel.getObservableQiitaItemList());
        mAdapter.getNextPageControlComponent().subscribeToModel(mModel);
    }

    @Override
    protected void onStop() {
        mAdapter.getNextPageControlComponent().unsubscribeToModel();
        mAdapter.getQiitaItemListReferenceComponent().setList(null);
        super.onStop();
    }

}
