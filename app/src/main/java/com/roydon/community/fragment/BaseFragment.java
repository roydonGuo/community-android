package com.roydon.community.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private boolean isFragmentVisible;
    private boolean isReuseView;
    private boolean isFirstVisible;

    protected View mRootView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(initLayout(), container, false);
            initView();
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    public void showShortToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showShortToastSync(String msg) {
        Looper.prepare();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void navigateTo(Class cls) {
        Intent in = new Intent(getActivity(), cls);
        startActivity(in);
    }

    public void navigateToWithBundle(Class cls, Bundle bundle) {
        Intent in = new Intent(getActivity(), cls);
        in.putExtras(bundle);
        startActivity(in);
    }

    public void navigateToWithFlag(Class cls, int flags) {
        Intent in = new Intent(getActivity(), cls);
        in.setFlags(flags);
        startActivity(in);
    }

//    protected void insertVal(String key, String val) {
//        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(key, val);
//        editor.commit();
//    }
//
//    protected String findByKey(String key) {
//        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
//        return sp.getString(key, "");
//    }
//
//    protected void removeByKey(String key) {
//        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();
//        edit.remove(key);
//        edit.commit();
//    }

}
