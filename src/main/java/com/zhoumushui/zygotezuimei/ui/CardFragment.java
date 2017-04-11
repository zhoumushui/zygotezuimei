package com.zhoumushui.zygotezuimei.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhoumushui.zygotezuimei.R;
import com.zhoumushui.zygotezuimei.bean.Card;

public class CardFragment extends Fragment {

    public static CardFragment getInstance(Card card) {
        CardFragment fragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("card", card);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card, null);
    }
}
