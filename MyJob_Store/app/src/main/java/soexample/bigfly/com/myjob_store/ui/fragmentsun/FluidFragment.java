package soexample.bigfly.com.myjob_store.ui.fragmentsun;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import soexample.bigfly.com.myjob_store.R;
import soexample.bigfly.com.myjob_store.sqlite.dao.MyDao;
import soexample.bigfly.com.myjob_store.utils.user_defined_view.User_Defined;
import soexample.bigfly.com.myjob_store.utils.user_defined_view.user_defined_View;

/**
 * A simple {@link Fragment} subclass.
 */
public class FluidFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.My_Header)
    user_defined_View MyHeader;
    @BindView(R.id.My_del)
    TextView MyDel;
    @BindView(R.id.My_History)
    User_Defined MyHistory;
    @BindView(R.id.My_Hot)
    User_Defined MyHot;
    Unbinder unbinder;
    private String[] lists = {"手机", "电脑", "零食", "配件", "耳机"};
    private View inflate;
    private MyDao myDao;
    private String mName;
    private FragmentManager manager;
    private SearchFragment searchFragment = new SearchFragment();
    private ArrayList<String> datas = new ArrayList<>();
    private ArrayList<String> mHistory = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_fluid, null);
        unbinder = ButterKnife.bind(this, inflate);
        myDao = new MyDao(getContext());
        mHistory = myDao.show();
        //初始化数据
        initData();
        initView(inflate);
        //进行一个判断
        if (!mHistory.isEmpty()) {
            //将默认数据直接从数据库添加到集合中
            MyHistory.setData(mHistory);
        }

        return inflate;
    }

    private void initView(View inflate) {
        MyHot.setData(datas);
    }


    private void initData() {
        for (int i = 0; i < lists.length; i++) {
            datas.add(lists[i]);
        }
        MyDel.setOnClickListener(this);
        MyHeader.getBack().setOnClickListener(this);
        MyHeader.getAdd().setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.My_Search_Btn:
                String name = MyHeader.getSearch().trim();
                //进行一个非空判断
                if (name != null && name.equals("")) {
                    Toast.makeText(getContext(), "输入为空", Toast.LENGTH_SHORT).show();
                } else {

                    myDao.insertall(MyHeader.getSearch().trim());
                    //自己封装一个删除子控件
                    MyHistory.removeChildView();
                    //将数据添加到集合中
                    mHistory.add(name);
                    MyHistory.setData(mHistory);

                    EventBus.getDefault().postSticky(name);
                    manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.My_Seach_Fragment, searchFragment).commit();


                }
                break;
            case R.id.My_del:
                //删除数据库
                myDao.delete();
                //删除输入历史
                MyHistory.removeChildView();
                //对数据集合进行清空
                mHistory.clear();
                break;
            case R.id.My_Btn_Back:
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}