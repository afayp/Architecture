package com.pfh.app_mvvm.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.pfh.app_mvvm.R;
import com.pfh.app_mvvm.model.Repository;
import com.pfh.app_mvvm.model.User;
import com.pfh.app_mvvm.network.ApiClient;
import com.squareup.picasso.Picasso;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/3.
 */

public class RepositoryViewModel extends BaseViewModel {

    private static final String TAG = "RepositoryViewModel";

    private Repository repository;

    public ObservableField<String> ownerName;
    public ObservableField<String> ownerEmail;
    public ObservableField<String> ownerLocation;
    public ObservableInt ownerEmailVisibility;
    public ObservableInt ownerLocationVisibility;
    public ObservableInt ownerLayoutVisibility;

    public RepositoryViewModel(Context context, final Repository repository) {
        this.repository = repository;
        this.mContext = context;
        this.ownerName = new ObservableField<>();
        this.ownerEmail = new ObservableField<>();
        this.ownerLocation = new ObservableField<>();
        this.ownerLayoutVisibility = new ObservableInt(View.INVISIBLE);
        this.ownerEmailVisibility = new ObservableInt(View.VISIBLE);
        this.ownerLocationVisibility = new ObservableInt(View.VISIBLE);
        // Trigger loading the rest of the user data as soon as the view model is created.
        // It's odd having to trigger this from here. Cases where accessing to the data model
        // needs to happen because of a change in the Activity/Fragment lifecycle
        // (i.e. an activity created) don't work very well with this MVVM pattern.
        // It also makes this class more difficult to test. Hopefully a better solution will be found
        loadFullUser(repository.owner.url);
    }

    //*********各种get方法 用来给xml中的变量提供数据
    public String getDescription() {
        return repository.description;
    }

    public String getHomepage() {
        return repository.homepage;
    }

    public int getHomepageVisibility() {
        return repository.hasHomepage() ? View.VISIBLE : View.GONE;
    }

    public String getLanguage() {
        return mContext.getString(R.string.text_language, repository.language);
    }

    public int getLanguageVisibility() {
        return repository.hasLanguage() ? View.VISIBLE : View.GONE;
    }

    public int getForkVisibility() {
        return repository.isFork() ? View.VISIBLE : View.GONE;
    }

    public String getOwnerAvatarUrl() {
        return repository.owner.avatarUrl;
    }


    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(view);
    }

    private void loadFullUser(String url) {

        ApiClient.getInstance().userFromUrl(url, new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {
                Log.i(TAG, "Full user data loaded " + user);
                ownerName.set(user.name);
                ownerEmail.set(user.email);
                ownerLocation.set(user.location);
                ownerEmailVisibility.set(user.hasEmail() ? View.VISIBLE : View.GONE);
                ownerLocationVisibility.set(user.hasLocation() ? View.VISIBLE : View.GONE);
                ownerLayoutVisibility.set(View.VISIBLE);
            }
        });

    }
}
