package example.com.moni_4_4.presenter;

import example.com.moni_4_4.model.bean.ShopBean;
import example.com.moni_4_4.model.httputils.HttpUtils;
import example.com.moni_4_4.view.interfaces.IShopView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShopPresenter extends BasePresenter<IShopView> {

    private final HttpUtils instance;

    public ShopPresenter(){
        instance = HttpUtils.getInstance();
    }

    public void getShopData(String uid) {
        instance.api.getshopdata(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShopBean>() {
                    @Override
                    public void accept(ShopBean shopBean) throws Exception {
                        getMview().shopSuccess(shopBean);
                    }
                });
    }
}
