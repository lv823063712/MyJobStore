package soexample.bigfly.com.myjob_store.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.utils.apiservice.MainConstant;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/2/25   15:01<p>
 * <p>更改时间：2019/2/25   15:01<p>
 * <p>版本号：1<p>
 */

public class MyCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;

    public MyCommentAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        //return mList.size() + 1;//因为最后多了一个添加图片的ImageView
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > MainConstant.MAX_SELECT_PIC_NUM) {
            return mList.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.grid_item, parent,false);
        ImageView iv = convertView.findViewById(R.id.pic_iv);
        if (position < mList.size()) {
            //代表+号之前的需要正常显示图片
            //图片路径
            String picUrl = mList.get(position);
            Glide.with(mContext).load(picUrl).into(iv);
        } else {
            //最后一个显示加号图片
            iv.setImageResource(R.mipmap.common_btn_camera_blue_n_hdpi);
        }
        return convertView;
    }


}
