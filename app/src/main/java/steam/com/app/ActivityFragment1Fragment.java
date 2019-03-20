package steam.com.app;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;

import steam.com.app.mould.Constans;
import steam.com.app.util.Store;

public class ActivityFragment1Fragment extends Fragment  {

    private TextView m_text1;
//这里重写了Fragment的onCreateView（）方法，
// 然后再这个方法中通过LayoutInflater的inflater（）方法将定义好的activity_fragment_1布局动态加载进来
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_1, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        m_text1 = (TextView) view.findViewById(R.id.text1);
        //initView();
    }

}
