package com.pfh.app_mvvm.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.pfh.app_mvvm.R;
import com.pfh.app_mvvm.databinding.ActivityRepositoryBinding;
import com.pfh.app_mvvm.model.Repository;
import com.pfh.app_mvvm.viewmodel.RepositoryViewModel;

/**
 * Created by Administrator on 2016/12/3.
 */

public class RepositoryActivity extends AppCompatActivity {
    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";
    private ActivityRepositoryBinding binding;
    private RepositoryViewModel repositoryViewModel;

    // 注意这种intent启动方式
    public static Intent newIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, repository);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Repository repository = getIntent().getParcelableExtra(EXTRA_REPOSITORY);
        repositoryViewModel = new RepositoryViewModel(this, repository);
        binding.setViewModel(repositoryViewModel);

        //Currently there is no way of setting an activity title using data binding
        setTitle(repository.name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repositoryViewModel.destory();
    }
}
