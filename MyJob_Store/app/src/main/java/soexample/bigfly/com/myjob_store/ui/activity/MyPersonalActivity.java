package soexample.bigfly.com.myjob_store.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.ImageHeadBean;
import soexample.bigfly.com.myjob_store.bean.InformationBean;
import soexample.bigfly.com.myjob_store.bean.MyRegister;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.ImageFileUtil;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;

public class MyPersonalActivity extends MyBaseActivity implements IView {
    @BindView(R.id.My_Head)
    SimpleDraweeView MyHead;
    @BindView(R.id.MyHeadImg)
    RelativeLayout MyHeadImg;
    @BindView(R.id.My_NiCheng)
    TextView MyNiCheng;
    @BindView(R.id.MyNickName)
    RelativeLayout MyNickName;
    @BindView(R.id.My_Pwd)
    TextView MyPwd;
    @BindView(R.id.MyPassWord)
    RelativeLayout MyPassWord;
    @BindView(R.id.lin)
    LinearLayout lin;
    private SharedPreferences user;
    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, String> headMap = new HashMap<>();
    private IPresenterImpl iPresenter;
    private String path = Environment.getExternalStorageDirectory()
            + "/image.png";
    private String file = Environment.getExternalStorageDirectory()
            + "/file.png";

    private TextView camera;
    private TextView pick;
    private TextView cancel;
    private PopupWindow window;
    private AlertDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_personal;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        InformationBean.ResultBean result = (InformationBean.ResultBean) intent.getSerializableExtra("result");
        String headPic = result.getHeadPic();
        String nickName = result.getNickName();
        String pwd = result.getPassword();
        iPresenter = new IPresenterImpl(this);
        user = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = user.getInt("userId", 0);
        String sessionId = user.getString("sessionId", "");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);
        MyHead.setImageURI(Uri.parse(headPic));
        MyNiCheng.setText(nickName);
        MyPwd.setText(pwd);
    }

    /**
     * 判断sd卡是否挂载
     */
    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void setClick() {

    }

    @Override
    protected void proLogic() {
        //加载视图
        View view_p = View.inflate(this, R.layout.popupwindow_item, null);
        camera = view_p.findViewById(R.id.text_camera);
        pick = view_p.findViewById(R.id.text_pick);
        cancel = view_p.findViewById(R.id.text_cancel);
        //创建PopupWindow
        window = new PopupWindow(view_p, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置焦点
        window.setFocusable(true);
        //设置背景
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //设置可触摸
        window.setTouchable(true);
        //打开相机
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调取系统相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (hasSdcard()) {
                    // 存到sdcard中
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(path)));
                    //执行
                    startActivityForResult(intent, 100);
                    window.dismiss();
                }

            }
        });
        //打开相册
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载相册
                Intent intent = new Intent(Intent.ACTION_PICK);
                if (hasSdcard()) {
                    //设置图片格式
                    intent.setType("image/*");
                    //执行
                    startActivityForResult(intent, 300);
                    window.dismiss();
                }

            }
        });
        //取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

    }


    @OnClick({R.id.My_Head, R.id.My_NiCheng, R.id.My_Pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.My_Head:
                window.showAtLocation(View.inflate(MyPersonalActivity.this, R.layout.activity_presonal_data, null),
                        Gravity.BOTTOM, 0, 0);
                break;
            case R.id.My_NiCheng:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MyPersonalActivity.this);
                View viewname = View.inflate(this, R.layout.presonal_alertdalog_name, null);
                builder.setView(viewname);
                Button update = viewname.findViewById(R.id.updata_btn);
                Button cencal = viewname.findViewById(R.id.cancal_btn);
                final EditText updateedix = viewname.findViewById(R.id.updata_edix);
                //修改
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = updateedix.getText().toString().trim();
                        if (name.equals("")) {
                            Toast.makeText(MyPersonalActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            map.put("nickName", name);
                            iPresenter.startRequestPut(Constant.UPDATE_NAME_URL,headMap, map, MyRegister.class);
                            MyNiCheng.setText(name);
                        }
                        dialog.dismiss();
                    }
                });
                //取消
                cencal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MyPersonalActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.My_Pwd:
                final AlertDialog.Builder pbuilder = new AlertDialog.Builder(MyPersonalActivity.this);
                View viewpas = View.inflate(this, R.layout.presonal_alertdalog_password, null);
                pbuilder.setView(viewpas);
                final EditText former_edix = viewpas.findViewById(R.id.former_edix);
                final EditText new_edix = viewpas.findViewById(R.id.new_edix);
                Button updatep = viewpas.findViewById(R.id.updata_btn);
                Button cancelp = viewpas.findViewById(R.id.cancal_btn);
                //修改
                updatep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String formerPass = former_edix.getText().toString().trim();
                        String newPass = new_edix.getText().toString().trim();
                        if (formerPass.equals("") || newPass.equals("")) {
                            Toast.makeText(MyPersonalActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                        }else if(formerPass.equals(newPass)){
                            Toast.makeText(MyPersonalActivity.this, "两次输入的密码一样", Toast.LENGTH_SHORT).show();
                        } else {
                            map.put("oldPwd", formerPass);
                            map.put("newPwd", newPass);
                            iPresenter.startRequestPut(Constant.UPDATE_PASSWORD_URL,headMap, map, MyRegister.class);
                            MyPwd.setText(newPass);
                            dialog.dismiss();
                        }

                    }
                });
                //取消
                cancelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MyPersonalActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog = pbuilder.create();
                dialog.show();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相机
        if(requestCode == 100 && resultCode == RESULT_OK){
            //调取裁剪功能
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置显示大小
            intent.putExtra("outputX", 50);
            intent.putExtra("outputY", 50);
            //将图片返回给data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);
        }
        //相册
        if(requestCode == 300 && resultCode == RESULT_OK){
            //获取相册路径
            Uri uri = data.getData();
            //调取裁剪功能
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置框高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置显示大小
            intent.putExtra("outputX", 50);
            intent.putExtra("outputY", 50);
            //将图片返回给data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);
        }
        if(requestCode == 200 && resultCode == RESULT_OK){
            Bitmap bitmap = data.getParcelableExtra("data");
            try {
                ImageFileUtil.setBitmap(bitmap,file,50);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MyPersonalActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            Map<String,String> mapaa = new HashMap<>();
            mapaa.put("image",file);
            iPresenter.startRequestImage(Constant.UPDATE_HEADERIMAGE_URL,headMap,mapaa,ImageHeadBean.class);
        }
    }


    @Override
    public void success(Object data) {
        //修改昵称
        if (data instanceof MyRegister) {
            MyRegister updateBean = (MyRegister) data;
            Toast.makeText(MyPersonalActivity.this, updateBean.getMessage(), Toast.LENGTH_SHORT).show();
            //修改密码
        } else if (data instanceof MyRegister) {
            MyRegister passwordBean = (MyRegister) data;
            Toast.makeText(MyPersonalActivity.this, passwordBean.getMessage(), Toast.LENGTH_SHORT).show();
        }else if(data instanceof ImageHeadBean){
            ImageHeadBean headBean = (ImageHeadBean) data;
            Toast.makeText(MyPersonalActivity.this,headBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error(Object error) {

    }

}
