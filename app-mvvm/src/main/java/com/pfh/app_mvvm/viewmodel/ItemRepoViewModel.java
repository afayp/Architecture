package com.pfh.app_mvvm.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.pfh.app_mvvm.R;
import com.pfh.app_mvvm.model.Repository;
import com.pfh.app_mvvm.view.RepositoryActivity;

/**
 * item的viewmodel，为什么采用继承BaseObservable的形式
 */

public class ItemRepoViewModel extends BaseObservable implements IViewModel {

    private Repository repository;
    private Context context;

    public ItemRepoViewModel(Context context, Repository repository) {
        this.repository = repository;
        this.context = context;
    }

    public String getName() {
        return repository.name;
    }

    public String getDescription() {
        return repository.description;
    }

    public String getStars() {
        return context.getString(R.string.text_stars, repository.stars);
    }

    public String getWatchers() {
        return context.getString(R.string.text_watchers, repository.watchers);
    }

    public String getForks() {
        return context.getString(R.string.text_forks, repository.forks);
    }

    public void onItemClick(View view) {
        context.startActivity(RepositoryActivity.newIntent(context, repository));
    }

    // Allows recycling ItemRepoViewModels within the recyclerview adapter
    public void setRepository(Repository repository) {
        this.repository = repository;
        notifyChange();// todo ?
    }

    @Override
    public void destory() {
        //In this case destroy doesn't need to do anything because there is not async calls
    }
}
