package com.zhoumushui.zygotezuimei.control;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zhoumushui.zygotezuimei.bean.Card;
import com.zhoumushui.zygotezuimei.ui.CardFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CardPagerAdapter extends FragmentStatePagerAdapter {

    private List<Card> mCardList;
    private List<Fragment> mFragments = new ArrayList();

    public CardPagerAdapter(FragmentManager fragmentManager, List<Card> cardList) {
        super(fragmentManager);
        //使用迭代器遍历List,
        Iterator iterator = cardList.iterator();
        while (iterator.hasNext()) {
            Card card = (Card) iterator.next();
            //实例化相应的Fragment并添加到List中
            mFragments.add(CardFragment.getInstance(card));
        }
        mCardList = cardList;
    }

    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    public List<Card> getCardList() {
        return mCardList;
    }

    public List<Fragment> getFragments() {
        return mFragments;
    }


  /*   public void setCardList(List<Card> cardList) {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = cardList.iterator();
        while (localIterator.hasNext())
            localArrayList.add(CardFragment.getInstance((Card) localIterator.next()));
        this.mFragments = localArrayList;
        this.mPostList = cardList;
    }

    public void setFragments(List<Fragment> paramList) {
        this.mFragments = paramList;
    }*/

    /* public void addCardList(List<Card> cardList) {
        ArrayList arrayList = new ArrayList();
        Iterator iterator = cardList.iterator();
        while (iterator.hasNext())
            arrayList.add(CardFragment.getInstance((Card) iterator.next()));
        if (this.mFragments == null)
            this.mFragments = new ArrayList();
        this.mFragments.addAll(arrayList);
        this.mCardList.addAll(cardList);
    }*/
}
