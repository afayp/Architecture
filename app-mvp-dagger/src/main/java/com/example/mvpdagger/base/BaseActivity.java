package com.example.mvpdagger.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.example.mvpdagger.AppApplication;
import com.example.mvpdagger.AppComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by panfeihang on 2017/6/12.
 */

public class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    @Inject
    public T mPresenter;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected AppComponent getAppComponent(){
        return AppApplication.getInstance().getAppComponent();
    }
}
