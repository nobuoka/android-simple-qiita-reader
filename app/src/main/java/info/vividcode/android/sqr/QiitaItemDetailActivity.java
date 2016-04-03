package info.vividcode.android.sqr;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import info.vividcode.android.sqr.databinding.ActivityQiitaItemDetailBinding;
import info.vividcode.android.sqr.dto.QiitaItem;

public class QiitaItemDetailActivity extends AppCompatActivity {

    private static final String EXTRA_QIITA_ITEM = "qiita-item";

    public static Intent createIntent(Context ctx, QiitaItem item) {
        Intent intent = new Intent(ctx, QiitaItemDetailActivity.class);
        intent.putExtra(EXTRA_QIITA_ITEM, item);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityQiitaItemDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_qiita_item_detail);
        QiitaItem qiitaItem = (QiitaItem) getIntent().getSerializableExtra(EXTRA_QIITA_ITEM);
        binding.setItem(qiitaItem);
    }

}
