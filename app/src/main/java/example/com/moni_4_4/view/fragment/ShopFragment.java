package example.com.moni_4_4.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.moni_4_4.R;
import example.com.moni_4_4.model.bean.ShopBean;
import example.com.moni_4_4.presenter.ShopPresenter;
import example.com.moni_4_4.view.adapter.ShopAdapter;
import example.com.moni_4_4.view.interfaces.IShopView;


public class ShopFragment extends Fragment implements IShopView {

    @BindView(R.id.ExpandableListView)
    ExpandableListView mExpandableListView;
    @BindView(R.id.check_all)
    CheckBox mCheckAll;
    @BindView(R.id.text_pricrall)
    TextView mTextPricrall;
    @BindView(R.id.linear_layout)
    LinearLayout mLinearLayout;
    private ShopPresenter shopPresenter;
    private Unbinder unbinder;
    String uid = "51";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        shopPresenter = new ShopPresenter();
        shopPresenter.setView(this);
        shopPresenter.getShopData(uid);
    }

    @Override
    public void shopSuccess(Object data) {
        ShopBean shopBean = (ShopBean) data;
        ShopAdapter shopAdapter = new ShopAdapter(getActivity());
        shopAdapter.setData(shopBean);
        mExpandableListView.setAdapter(shopAdapter);
        //默认展开
        for (int i = 0; i < shopBean.getData().size(); i++) {
            mExpandableListView.expandGroup(i);
        }
        //把全选的checkbox和Adapter联动到一起
          shopAdapter.setCheckBox(mCheckAll);
        //计算总价和adapter联动
        shopAdapter.setPrice(mTextPricrall);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        shopPresenter.onDettachView();
    }
}
