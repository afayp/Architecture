package com.pfh.app_mvp.repos;

/**
 * 契约类,包括
 *  1. view(activity)与Presenter交互的接口，定义界面改变需要的方法
 *      - view要实现这个接口，然后通过p层的构造函数传入p层；
 *      - P层持有view引用来改变view的状态，如showProgress，showData，hideProgress.
 *  2. Presenter的接口，定义了跟业务相关的控制逻辑(这个接口不是必须的，有些mvp的实现没有这个接口)
 *      - p层要实现这个接口，然后通过BaseView的setPresenter传入view层
 *      - view层要持有Presenter的引用，以便操作界面后去执行相关业务逻辑,比如点击按钮后去加载数据。
 *  一个简单的逻辑是： 点击view层的一个按钮，调用mPresenter.loadData()加载数据，加载完毕后在p层调用mView.showData()显示数据。
 */

public interface ReposContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showLoadErrorMsg();

        void showRepos();

    }

    interface Presenter {

        void loadRepos();

        void clickRepo();

    }



}
