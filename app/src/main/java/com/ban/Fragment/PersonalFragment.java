package com.ban.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ban.Bean.User;
import com.ban.maopu.R;

/**
 * Created by Administrator on 2016/6/6.
 */
public class PersonalFragment extends Fragment {

    private TextView info;
    private TextView collection;
    private TextView apply;
    private TextView setting;
    private Button logout;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_layout,container,false);
        info = (TextView) rootView.findViewById(R.id.info);

        info.setText(User.getUser().getPhone());
        collection = (TextView) rootView.findViewById(R.id.collection);
        apply = (TextView) rootView.findViewById(R.id.apply);
        setting = (TextView) rootView.findViewById(R.id.setting);
        logout = (Button) rootView.findViewById(R.id.logout);
        return rootView;
    }
}
