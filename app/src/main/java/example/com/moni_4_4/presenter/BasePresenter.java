package example.com.moni_4_4.presenter;

import example.com.moni_4_4.view.fragment.HomeFragment;

public class BasePresenter<V> {

    private V mview;

    public void setView(V mview) {
        this.mview =mview;
    }

    public V getMview() {
        return mview;
    }
    public void onDettachView(){
        mview=null;
    }
}
