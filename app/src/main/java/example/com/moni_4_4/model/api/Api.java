package example.com.moni_4_4.model.api;

import example.com.moni_4_4.model.bean.ShopBean;
import example.com.moni_4_4.model.bean.ShouYeBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("small/commodity/v1/findCommodityByKeyword")
    Observable<ShouYeBean> getshouyeData(@Query("keyword")String keyword,@Query("page")int page,@Query("count")int count);
     @GET("ks/product/getCarts?")
    Observable<ShopBean> getshopdata(@Query("uid") String uid);
}
