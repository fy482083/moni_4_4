package example.com.moni_4_4.view.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import example.com.moni_4_4.R;

public class PriceView extends LinearLayout {
    //接口回调

    private int num = 1;
    private OnAmountLisenter lisenter;

    public interface OnAmountLisenter {
        void onAmount(int num);
    }

    public void setLisenter(OnAmountLisenter lisenter) {
        this.lisenter = lisenter;
    }

    public PriceView(Context context) {
        super(context);
    }

    public PriceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.num_layout, null, true);
        addView(view);
        Button btn_jia = view.findViewById(R.id.btn_jia);
        Button btn_jian = view.findViewById(R.id.btn_jian);
        final EditText edit_text = view.findViewById(R.id.edit_text);
        edit_text.setText(num+"");
        //减
        btn_jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num==1){
                    Toast.makeText(context,"数量不能小于1",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    num--;
                    edit_text.setText(num+"");
                    if (lisenter!=null){
                        lisenter.onAmount(num);
                    }
                }
            }
        });
        //加
        btn_jia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                edit_text.setText(num+"");
                if (lisenter!=null){
                    lisenter.onAmount(num);
                }
            }
        });

    }
}
