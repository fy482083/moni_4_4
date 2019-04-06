package example.com.moni_4_4.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import example.com.moni_4_4.R;
import example.com.moni_4_4.model.bean.ShopBean;
import example.com.moni_4_4.view.activity.PriceView;


public class ShopAdapter extends BaseExpandableListAdapter {
        Context context;
        ShopBean shopBean;

    public ShopAdapter(Context context) {
        this.context = context;
    }
    public void setData(ShopBean shopBean){
        this.shopBean=shopBean;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if (shopBean!=null){
            return shopBean.getData().size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

       if (shopBean!=null){
           return shopBean.getData().get(groupPosition).getList().size();
       }
       return  0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.shop_parent, null);
            parentViewHolder = new ParentViewHolder();
            parentViewHolder.check_parent=convertView.findViewById(R.id.check_parent);
            parentViewHolder.text_parent_name=convertView.findViewById(R.id.text_parent_name);
            convertView.setTag(parentViewHolder);
        }else{
            parentViewHolder= (ParentViewHolder) convertView.getTag();
        }
        parentViewHolder.text_parent_name.setText(shopBean.getData().get(groupPosition).getSellerName()+"");
        parentViewHolder.check_parent.setChecked(shopBean.getData().get(groupPosition).isCheck());
       parentViewHolder.check_parent.setTag(groupPosition);
       parentViewHolder.check_parent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CheckBox checkBox= (CheckBox) v;
               boolean checked = checkBox.isChecked();
               int groupPosition = Integer.parseInt(checkBox.getTag().toString());
               shopBean.getData().get(groupPosition).setCheck(checked);
              //点击商家   商品全部选中
              selectGroup(groupPosition,checked);
                //判断商家是否全选
               boolean selectGroupAll = isSelectGroupAll();
               //如果商家是选中的，那么总的CheckBox就会选中
               mCheckBox.setChecked(selectGroupAll);
               notifyDataSetChanged();
           }
       });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.shop_child, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.check_child=convertView.findViewById(R.id.check_child);
            childViewHolder.text_child_price=convertView.findViewById(R.id.text_child_price);
            childViewHolder.text_child_name=convertView.findViewById(R.id.text_child_name);
            childViewHolder.img_child=convertView.findViewById(R.id.img_child);
            childViewHolder.custom=convertView.findViewById(R.id.custom);
            convertView.setTag(childViewHolder);
        }
        else{
            childViewHolder= (ChildViewHolder) convertView.getTag();

        }
        childViewHolder.check_child.setChecked(shopBean.getData().get(groupPosition).getList().get(childPosition).isCheck());
        childViewHolder.text_child_name.setText(shopBean.getData().get(groupPosition).getList().get(childPosition).getTitle());
        childViewHolder.text_child_price.setText(shopBean.getData().get(groupPosition).getList().get(childPosition).getPrice()+"");
        Uri uri = Uri.parse(shopBean.getData().get(groupPosition).getList().get(childPosition).getImages());
        childViewHolder.img_child.setImageURI(uri);
        childViewHolder.check_child.setTag(groupPosition+"#"+childPosition);

        childViewHolder.check_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    CheckBox checkBox= (CheckBox) v;
                    String tag = (String) v.getTag();
                    int groupPosition = Integer.parseInt(tag.split("#")[0]);
                    int childPosition = Integer.parseInt(tag.split("#")[1]);
                ShopBean.DataBean dataBean = shopBean.getData().get(groupPosition);
                ShopBean.DataBean.ListBean listBean = dataBean.getList().get(childPosition);
                listBean.setCheck(checkBox.isChecked());
                //查询商品是否全部选中选中
                boolean selectGroup = isSelectGroup(groupPosition);
                //如果商品的CheckBox是选中的，那么商家的状态就是选中的
                dataBean.setCheck(selectGroup);
                //判断商家是否全部选中
                boolean selectGroupAll = isSelectGroupAll();
                //如果商家是选中的，那么总的CheckBox就会选中
                mCheckBox.setChecked(selectGroupAll);
               //计算总价
                jiSuanPrice();
                notifyDataSetChanged();
            }
        });
        //接口回调   获取加加减减
        childViewHolder.custom.setLisenter(new PriceView.OnAmountLisenter() {
            @Override
            public void onAmount(int num) {
                Log.i("eeee", "onAmount: "+num);
                shopBean.getData().get(groupPosition).getList().get(childPosition).setNum(num+"");
                //计算总价
                jiSuanPrice();
            }
        });
        return convertView;
    }
//计算总价
    private void jiSuanPrice() {
        int totalprice=0;
        for (int i = 0; i < shopBean.getData().size(); i++)
            for (int j = 0; j < shopBean.getData().get(i).getList().size(); j++)
                if (shopBean.getData().get(i).getList().get(j).isCheck()) {
                    int num = Integer.parseInt(shopBean.getData().get(i).getList().get(j).getNum());
                    int price = Integer.parseInt(shopBean.getData().get(i).getList().get(j).getPrice());
                    totalprice += price * num;
                }
        priceTotal.setText(totalprice+"");
        notifyDataSetChanged();

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //点击商家   商品全部选中
    public void selectGroup(int groupPosition,boolean isChecked){
        for (int i = 0; i <shopBean.getData().get(groupPosition).getList().size() ; i++) {
            ShopBean.DataBean.ListBean listBean = shopBean.getData().get(groupPosition).getList().get(i);
            listBean.setCheck(isChecked);
        }
    }
    //判断商家是否全部选中
    private boolean isSelectGroupAll() {
        for (int i = 0; i <shopBean.getData().size() ; i++) {
            //遍历商家
            ShopBean.DataBean dataBean = shopBean.getData().get(i);
            boolean check = dataBean.isCheck();
            if (!check){
                return false;
            }
        }
        return true;
    }


//查询商品是否全部选中选中
    private boolean isSelectGroup(int groupPosition) {
        for (int i = 0; i < shopBean.getData().get(groupPosition).getList().size(); i++) {
            ShopBean.DataBean.ListBean listBean = shopBean.getData().get(groupPosition).getList().get(i);
            boolean check = listBean.isCheck();
            if (!check){
                   return false;
            }
        }
        return true;
    }
    //checkbox全选
    CheckBox mCheckBox;
    public void setCheckBox(CheckBox checkBox) {
        this.mCheckBox=checkBox;
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                CheckBox checkBox= (CheckBox) v;
                //全部选中     checkBox.isChecked()选中状态
                selectAll(checkBox.isChecked());
            }
        });
    }
    //全部选中
    private void selectAll(boolean checked) {
        for (int i = 0; i < shopBean.getData().size(); i++) {
            //商家选中
            ShopBean.DataBean dataBean = shopBean.getData().get(i);
            dataBean.setCheck(checked);
            for (int j = 0; j < dataBean.getList().size(); j++) {
                //商品选中
                ShopBean.DataBean.ListBean listBean = dataBean.getList().get(j);
                listBean.setCheck(checked);
            }
        }
        notifyDataSetChanged();
    }
//计算总价
    TextView priceTotal;
    public void setPrice(TextView mTextPricrall) {
            this.priceTotal=mTextPricrall;
            notifyDataSetChanged();
    }

    class ParentViewHolder{
          CheckBox check_parent;
          TextView text_parent_name;
    }
    class ChildViewHolder{
       CheckBox check_child;
       TextView text_child_name,text_child_price;
       SimpleDraweeView img_child;
       PriceView custom;

    }
}
