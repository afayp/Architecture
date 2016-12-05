package com.pfh.app_mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.pfh.app_mvvm.R;
import com.pfh.app_mvvm.model.Repository;
import com.pfh.app_mvvm.network.ApiClient;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * viewmodel 不持有任何控件，持有context，model
 */

public class MainViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";

    public ObservableInt infoMessageVisibility;
    public ObservableInt progressVisibility;
    public ObservableInt recyclerViewVisibility;
    public ObservableInt searchButtonVisibility;
    public ObservableField<String> infoMessage;

    private List<Repository> repositories;//数据
    private DataListener dataListener;
    public String editTextUsernameValue;

    public MainViewModel(Context context, DataListener dataListener){
        this.mContext = context;
        this.dataListener = dataListener;
        infoMessageVisibility = new ObservableInt(View.VISIBLE);
        progressVisibility = new ObservableInt(View.INVISIBLE);
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        searchButtonVisibility = new ObservableInt(View.GONE);
        infoMessage = new ObservableField<>(context.getString(R.string.default_info_message));
    }

    public interface DataListener {
        void onRepositoriesChanged(List<Repository> repositories);
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    public void onClickSearch(View view){
        loadGithubRepos(editTextUsernameValue);
    }

    public boolean onSearchAction(TextView view, int actionId, KeyEvent event){
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            String username = view.getText().toString();
            if (username.length() > 0) loadGithubRepos(username);
            return true;
        }
        return false;
    }

    public TextWatcher getUsernameEditTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextUsernameValue = s.toString();
                searchButtonVisibility.set(s.length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private void loadGithubRepos(String username) {
        progressVisibility.set(View.VISIBLE);
        recyclerViewVisibility.set(View.INVISIBLE);
        infoMessageVisibility.set(View.INVISIBLE);

//        //如果先前一个未注销，则先注销 Todo ？ 每个都这么写一遍太麻烦。。
//        if (subscription != null && !subscription.isUnsubscribed()){
//            subscription.unsubscribe();
//        }
        subscription = ApiClient.getInstance().publicRepositories(username, new Subscriber<List<Repository>>() {
            @Override
            public void onCompleted() {
                if (dataListener != null) dataListener.onRepositoriesChanged(repositories);
                progressVisibility.set(View.INVISIBLE);
                if (!repositories.isEmpty()) {
                    recyclerViewVisibility.set(View.VISIBLE);
                } else {
                    infoMessage.set(mContext.getString(R.string.text_empty_repos));
                    infoMessageVisibility.set(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error loading GitHub repos "+ e.getMessage());
                progressVisibility.set(View.INVISIBLE);
                infoMessage.set(mContext.getString(R.string.error_username_not_found));
                infoMessageVisibility.set(View.VISIBLE);
            }

            @Override
            public void onNext(List<Repository> repositories) {
                Log.e(TAG, "Repos loaded " + repositories);
                MainViewModel.this.repositories = repositories;
            }
        });

    }

    @Override
    public void destory() {
        super.destory();
        dataListener = null;
    }

    private static boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }
}
