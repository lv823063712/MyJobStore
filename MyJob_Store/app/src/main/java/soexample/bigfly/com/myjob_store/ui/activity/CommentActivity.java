package soexample.bigfly.com.myjob_store.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.bean.MyRegister;
import soexample.bigfly.com.myjob_store.bean.ReleaseCircleBean;
import soexample.bigfly.com.myjob_store.mvp.ipresenter.IPresenterImpl;
import soexample.bigfly.com.myjob_store.mvp.iview.IView;
import soexample.bigfly.com.myjob_store.ui.adapter.MyCommentAdapter;
import soexample.bigfly.com.myjob_store.ui.base.MyBaseActivity;
import soexample.bigfly.com.myjob_store.utils.PictureSelectorConfig;
import soexample.bigfly.com.myjob_store.utils.apiservice.Constant;
import soexample.bigfly.com.myjob_store.utils.apiservice.MainConstant;

public class CommentActivity extends MyBaseActivity implements IView {


    @BindView(R.id.item_image)
    SimpleDraweeView itemImage;
    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.item_price)
    TextView itemPrice;
    @BindView(R.id.write_remate)
    EditText writeRemate;
    @BindView(R.id.radio)
    RadioButton radio;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.publish)
    Button publish;
    @BindView(R.id.gridView)
    GridView gridView;
    private String commodityId;
    private String orderId;
    private String img;
    private String name;
    private String price;
    private IPresenterImpl iPresenter;
    private SharedPreferences shared;
    private int userId;
    private String sessionId;

    /**
     * 上传的图片凭证的数据源
     */
    private ArrayList<String> mPicList = new ArrayList<>();

    private List<Object> PLlist = new ArrayList<>();
    private Map<String, String> headMap = new HashMap<>();
    private Map<String, Object> map = new HashMap<>();
    private MyCommentAdapter myCommentAdapter;
    private Bitmap bitmap;

    @Override
    protected int getLayout() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //intent取值
        Intent intent = getIntent();
        commodityId = intent.getStringExtra("commodityId");
        orderId = intent.getStringExtra("orderId");
        img = intent.getStringExtra("img");
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        itemImage.setImageURI(img);
        itemName.setText(name);
        itemPrice.setText("¥" + price);

        PLlist.add(R.mipmap.common_btn_camera_blue_n_hdpi);


        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);

    }

    @Override
    protected void setClick() {
    }

    @Override
    protected void proLogic() {
        iPresenter = new IPresenterImpl(this);
        initGridView();
    }

    private void initGridView() {
        myCommentAdapter = new MyCommentAdapter(this, mPicList);
        gridView.setAdapter(myCommentAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过9张，才能点击
                    if (mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                        //最多添加9张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPicList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }

    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                //压缩后的图片路径
                String compressPath = localMedia.getCompressPath();
                //把图片添加到将要上传的图片数组中
                mPicList.add(compressPath);
                myCommentAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST);
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            myCommentAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.publish)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish:
                if (radio.isChecked()) {
                    String content = writeRemate.getText().toString().trim();
                    map.put("commodityId", String.valueOf(commodityId));
                    map.put("content", content);
                    map.put("image", mPicList);
                    iPresenter.startRequestImagePost(Constant.RELEASE_CIRCLE_URL, headMap, map, ReleaseCircleBean.class);
               }
                String content = writeRemate.getText().toString().trim();
                map.put("commodityId", String.valueOf(commodityId));
                map.put("orderId", orderId);
                map.put("content", content);
                map.put("image", mPicList);
                iPresenter.startRequestImagePost(Constant.ADD_COMMODITY_COMMENT_LIST_URL, headMap, map, MyRegister.class);
                break;
                default:
                    break;
        }

    }

    @Override
    public void success(Object data) {
        if (data instanceof MyRegister) {
            Log.e("aaa2", "daozhele" );
            MyRegister remaitBean = (MyRegister) data;
            Toast.makeText(CommentActivity.this, remaitBean.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (data instanceof ReleaseCircleBean) {
            Log.e("aaa4", "daozhele" );
            ReleaseCircleBean releaseCircleBean = (ReleaseCircleBean) data;
            Toast.makeText(CommentActivity.this, releaseCircleBean.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void error(Object error) {

        Log.e("zzz", "error: " + System.currentTimeMillis());

        Toast.makeText(this, "失败了o(╥﹏╥)o" + error, Toast.LENGTH_SHORT).show();
    }

}
