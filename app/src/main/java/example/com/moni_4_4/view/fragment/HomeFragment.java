package example.com.moni_4_4.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.moni_4_4.R;
import example.com.moni_4_4.model.bean.ShouYeBean;
import example.com.moni_4_4.presenter.ShouYePresenter;
import example.com.moni_4_4.view.adapter.ShouYeAdapter;
import example.com.moni_4_4.view.interfaces.IShowYeView;


public class HomeFragment extends Fragment implements IShowYeView {

    @BindView(R.id.img_left)
    ImageView mImgLeft;
    @BindView(R.id.ed_text)
    EditText mEdText;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @BindView(R.id.RelativeLayout)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.recy_view)
    RecyclerView mRecyView;
    private Unbinder unbinder;
    private View view;
    private ShouYePresenter shouYePresenter;
    String keyword = "板鞋";
    int page;
    int count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        shouYePresenter = new ShouYePresenter();
        shouYePresenter.setView(this);

        mImgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEdText.getText().toString();
                shouYePresenter.getShouYeData(name);
                Log.e("name", "onClick: " + name);
            }
        });
    }



    @Override
    public void shouyeSuccess(Object data) {
        ShouYeBean shouYeBean = (ShouYeBean) data;
        Log.e("shouYeBean", "shouyeSuccess: " + shouYeBean);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyView.setLayoutManager(gridLayoutManager);
        ShouYeAdapter shouYeAdapter = new ShouYeAdapter(getActivity());
        mRecyView.setAdapter(shouYeAdapter);
        shouYeAdapter.setdata(shouYeBean);


    }

    //解绑
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        shouYePresenter.onDettachView();
        unbinder.unbind();
    }
}
