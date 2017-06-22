package com.example.mvpdagger.repos;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mvpdagger.R;
import com.example.mvpdagger.base.BaseActivity;
import com.example.mvpdagger.model.Repository;

import java.util.List;

import butterknife.Bind;

public class ReposActivity extends BaseActivity<ReposPresenter> implements ReposContract.View {

    @Bind(R.id.button_search)
    ImageButton button_search;
    @Bind(R.id.edit_text_username)
    EditText edit_text_username;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.text_info)
    TextView text_info;
    @Bind(R.id.repos_recycler_view)
    RecyclerView repos_recycler_view;

//    @Inject
//    ReposContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        DaggerReposComponent.builder()
                .appComponent(getAppComponent())
                .reposPresenterModule(new ReposPresenterModule(ReposActivity.this))
                .build()
                .inject(this);
        edit_text_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                button_search.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.loadRepos(edit_text_username.getText().toString());
            }
        });
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        RepositoryAdapter adapter = new RepositoryAdapter();
        repos_recycler_view.setAdapter(adapter);
        repos_recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showLoadErrorMsg(String errorInfo) {
        text_info.setVisibility(View.VISIBLE);
        text_info.setText(errorInfo);
    }

    @Override
    public void hideLoadErrorMsg() {
        text_info.setVisibility(View.GONE);
    }

    @Override
    public void showRepos(List<Repository> repositories) {
        repos_recycler_view.setVisibility(View.VISIBLE);
        RepositoryAdapter adapter = (RepositoryAdapter) repos_recycler_view.getAdapter();
        adapter.setRepositories(repositories);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideRepos() {
        repos_recycler_view.setVisibility(View.GONE);
    }

}
