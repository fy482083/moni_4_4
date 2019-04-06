package example.com.moni_4_4.presenter;

import example.com.moni_4_4.model.bean.ShouYeBean;
import example.com.moni_4_4.model.httputils.HttpUtils;
import example.com.moni_4_4.view.interfaces.IShowYeView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShouYePresenter extends BasePresenter<IShowYeView> {

    private final HttpUtils instance;

    public ShouYePresenter(){
        instance = HttpUtils.getInstance();
    }
    public void getShouYeData(String keyword) {
          instance.api.getshouyeData(keyword,1,10)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Consumer<ShouYeBean>() {
                      @Override
                      public void accept(ShouYeBean shouYeBean) throws Exception {
                          getMview().shouyeSuccess(shouYeBean);
                      }
                  });
    }
}
