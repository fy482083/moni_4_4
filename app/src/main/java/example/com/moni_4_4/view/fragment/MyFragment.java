package example.com.moni_4_4.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TableLayout;

import java.io.File;

import example.com.moni_4_4.R;

import static android.app.Activity.RESULT_OK;


public class MyFragment extends Fragment implements View.OnClickListener {
    private ImageView img_icon;
    private View contentView;
    private PopupWindow popupWindow;
    private Button btn_pai;
    private Button btn_xiang;
    private String path = Environment.getExternalStorageDirectory()
            + "/def.jpg";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_layout, container, false);
        // 1.初始化控件
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        // 2.创建PopupWindow
        contentView = View
                .inflate(getActivity(), R.layout.pop_layout, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 3.找到窗口里面的按钮
        btn_pai = (Button) contentView.findViewById(R.id.btn_pai);
        btn_xiang = (Button) contentView.findViewById(R.id.btn_xiang);
        // 4.添加点击监听
        btn_pai.setOnClickListener(this);
        btn_xiang.setOnClickListener(this);
        showPop( view);
        return view;

    }
    public void showPop(View view) {
        // 显示PopupWindow
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pai:
                // 拍照功能
                Intent pIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 保存到指定路径
                pIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(path)));
                // 打开页面--回传值的方式
                startActivityForResult(pIntent, 98);
                // 关闭窗口
                popupWindow.dismiss();
                break;
            case R.id.btn_xiang:
                // 相册功能
                Intent xIntent = new Intent(Intent.ACTION_PICK);
                // 必须设置取出图片的类型
                xIntent.setType("image/*");
                // 打开页面--回传值的方式
                startActivityForResult(xIntent, 99);
                //关闭窗口
                popupWindow.dismiss();
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 98 && resultCode == RESULT_OK) {
            // 取出的图片
            Uri fromFile = Uri.fromFile(new File(path));
            // 裁剪
            crop(fromFile);
        }
        if (requestCode == 100 && resultCode == RESULT_OK) {
            // 接收裁剪后的图片,,,进行设置头像
            Bitmap bitmap = data.getParcelableExtra("data");
            img_icon.setImageBitmap(bitmap);
        }
        if (requestCode == 99 && resultCode == RESULT_OK) {
            //进行裁剪
            crop(data.getData());
        }
    }

    private void crop(Uri fromFile) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fromFile, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 249);
        intent.putExtra("outputY", 249);
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 100);
    }
}
