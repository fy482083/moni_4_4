package example.com.moni_4_4.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.com.moni_4_4.R;
import example.com.moni_4_4.view.fragment.HomeFragment;
import example.com.moni_4_4.view.fragment.MyFragment;
import example.com.moni_4_4.view.fragment.ShopFragment;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_shou)
    RadioButton mBtnShou;
    @BindView(R.id.btn_shop)
    RadioButton mBtnShop;
    @BindView(R.id.btn_my)
    RadioButton mBtnMy;
    @BindView(R.id.redio_group)
    RadioGroup mRedioGroup;
    @BindView(R.id.fram_layout)
    FrameLayout mFramLayout;
    private HomeFragment homeFragment;
    private ShopFragment shopFragment;
    private MyFragment myFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        homeFragment = new HomeFragment();
        shopFragment = new ShopFragment();
        myFragment = new MyFragment();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.fram_layout,homeFragment);
        transaction.commit();

        mRedioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (checkedId){
                    case R.id.btn_shou:
                        fragmentTransaction.replace(R.id.fram_layout,homeFragment);
                        break;
                    case R.id.btn_shop:
                        fragmentTransaction.replace(R.id.fram_layout,shopFragment);
                        break;
                    case R.id.btn_my:
                        fragmentTransaction.replace(R.id.fram_layout,myFragment);
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       unbinder.unbind();
    }
}
