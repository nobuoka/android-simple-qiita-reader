package info.vividcode.android.sqr;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import info.vividcode.android.sqr.databinding.ActivityMainBinding;
import info.vividcode.android.sqr.presentation.QiitaItemListAdapter;

public class MainActivity extends AppCompatActivity {

    QiitaItemListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mAdapter = new QiitaItemListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(mAdapter);
    }

}
