package example.com.moni_4_4.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import example.com.moni_4_4.R;
import example.com.moni_4_4.model.bean.ShouYeBean;

public class ShouYeAdapter extends RecyclerView.Adapter<ShouYeAdapter.ViewHolder> {
    Context context;
    ShouYeBean shouYeBean;

    public ShouYeAdapter(Context context) {
        this.context = context;
    }
    public void setdata(ShouYeBean shouYeBean){
        this.shouYeBean=shouYeBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text_shou_name.setText(shouYeBean.getResult().get(i).getCommodityName());
        viewHolder.text_shou_price.setText(shouYeBean.getResult().get(i).getPrice()+"");

        Uri uri = Uri.parse(shouYeBean.getResult().get(i).getMasterPic());
        viewHolder.img_shou.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return shouYeBean.getResult().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView img_shou;
        private final TextView text_shou_name,text_shou_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_shou = itemView.findViewById(R.id.img_shou);
            text_shou_name = itemView.findViewById(R.id.text_shou_name);
            text_shou_price = itemView.findViewById(R.id.text_shou_price);
        }
    }
}
