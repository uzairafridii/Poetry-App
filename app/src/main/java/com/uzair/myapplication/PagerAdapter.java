package com.uzair.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter
{

    private Context context;
    private ImageView imageView;
    private  LayoutInflater mLayoutInflater;
    public int[] GalImages = new int[] {
            R.drawable.a, R.drawable.b, R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,
            R.drawable.h,R.drawable.i,R.drawable.j,R.drawable.k,R.drawable.l,R.drawable.m,R.drawable.n,
            R.drawable.o,R.drawable.p,R.drawable.q,R.drawable.r,R.drawable.s,R.drawable.t,R.drawable.u,
            R.drawable.v,R.drawable.w,R.drawable.x,R.drawable.y,R.drawable.z,R.drawable.aa, R.drawable.bb,
            R.drawable.cc,R.drawable.dd,R.drawable.ee,R.drawable.ff,R.drawable.gg, R.drawable.hh,R.drawable.ii,R.drawable.jj,
            R.drawable.kk,R.drawable.ll,R.drawable.mm,R.drawable.nn, R.drawable.oo,R.drawable.pp,R.drawable.qq,R.drawable.rr,
            R.drawable.ss,R.drawable.ttt,R.drawable.uu, R.drawable.vv,R.drawable.ww,R.drawable.xx,R.drawable.yy,R.drawable.zz,
            R.drawable.aaa, R.drawable.bbb,
            R.drawable.ccc,R.drawable.ddd,R.drawable.eee,R.drawable.fff,R.drawable.ggg, R.drawable.hhh,R.drawable.iii,R.drawable.jjj,
            R.drawable.kkk,R.drawable.lll,R.drawable.mmm,R.drawable.nnn, R.drawable.ooo,R.drawable.ppp,R.drawable.qqq,R.drawable.rrr,
            R.drawable.sss,R.drawable.first,R.drawable.uuu, R.drawable.vvv,R.drawable.www,R.drawable.xxx,R.drawable.yyy,R.drawable.zzz,
            R.drawable.second,R.drawable.third,R.drawable.fourth,R.drawable.fifth, R.drawable.six,R.drawable.seven,R.drawable.eight,
            R.drawable.nine,R.drawable.axax,R.drawable.ayay,R.drawable.azaz
    };


    PagerAdapter(Context context){
        this.context=context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.image_layout, container, false);


        imageView = (ImageView) itemView.findViewById(R.id.imageViewInImageLayout);
        imageView.setImageResource(GalImages[position]);

        container.addView(itemView);

        return itemView;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

        @Override
    public int getCount() {
        return GalImages.length;
    }


    public int setImageTOLast()
    {
        return GalImages.length;
    }

    public int setImageTOFirst()
    {
       return 0;
    }



    }










