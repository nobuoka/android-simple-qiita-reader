package info.vividcode.android.sqr;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import info.vividcode.android.sqr.databinding.ActivityMainBinding;
import info.vividcode.android.sqr.di.AppComponent;
import info.vividcode.android.sqr.di.DaggerAppComponent;
import info.vividcode.android.sqr.di.QiitaWebApiModule;
import info.vividcode.android.sqr.presentation.QiitaItemListAdapter;
import info.vividcode.android.sqr.presentation.models.QiitaItemListPresentationModel;

public class MainActivity extends AppCompatActivity {

    QiitaItemListPresentationModel mModel;

    QiitaItemListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent appComponent = DaggerAppComponent.builder()
                .qiitaWebApiModule(QiitaWebApiModule.createFormalInstance())
                .build();
        mModel = appComponent.qiitaItemListPresentationModel();

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
