package com.example.amrelmasry.simpletwitterclient.common.mvpbase;

/**
 * any mvp Presenter must extends this class
 * it provides two methods to register and unregister views
 *
 * @param <V>
 */
public class BasePresenter<V extends BaseViewInterface> {
    public V view;

    public void registerView(V view) {
        this.view = view;
    }

    public void unregisterView() {
        this.view = null;
    }
    
}
