package com.oyeafrica.kwizzer.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.oyeafrica.kwizzer.R;

public class OnBoardingPagerAdapter extends PagerAdapter {
    Context context;

    private LayoutInflater layoutInflater;
    public String[] titles = {
            "Trivia",
            "True False",
            "Leader Board"
    };
    public String[] bodies = {
            "Answer questions\nabout\ndifferent topics",
            "Is it\nor \nis it not",
            "Earn points\nand\nbe first"
    };
    public  int [] animation = {
            R.raw.idea_anim,
            R.raw.ask_anim,
            R.raw.leader_board
    };



    public OnBoardingPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return animation.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);
        TextView titletext = (TextView) view.findViewById(R.id.text_title);
        TextView bodytext = (TextView) view.findViewById(R.id.text_body);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) view.findViewById(R.id.anim);

        lottieAnimationView.setAnimation(animation[position]);
        lottieAnimationView.playAnimation();

        titletext.setText(titles[position]);
        bodytext.setText(bodies[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
