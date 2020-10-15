package com.oyeafrica.kwizzer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oyeafrica.kwizzer.R;
import com.oyeafrica.kwizzer.utils.OnBoardingPagerAdapter;

public class Onboarding extends AppCompatActivity {
    private static final String TAG = "Onboarding";
    private ViewPager onBoardingpager;
    private OnBoardingPagerAdapter pagerAdapter;
    private LinearLayout linearLayoutDots, linearLayoutNext;
    private TextView textViewNext;
    private int currentPage;
    private TextView[] dash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        onBoardingpager = findViewById(R.id.viewpager_onboarding);
        linearLayoutDots = findViewById(R.id.linearlayout_dots);
        linearLayoutNext = findViewById(R.id.linearlayout_next);
        textViewNext = findViewById(R.id.text_view_next);

        pagerAdapter = new OnBoardingPagerAdapter(this);
        onBoardingpager.setAdapter(pagerAdapter);
        addPositionDash(0);

        onBoardingpager.addOnPageChangeListener(pageChangeListener);
        linearLayoutNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBoardingpager.setCurrentItem(currentPage+1);
            }
        });


    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {
            addPositionDash(position);
            currentPage = position;
            if(position == dash.length-1){
                Log.d(TAG, "onPageSelected: " +position);
                textViewNext.setText(R.string.get_started);
                textViewNext.setTextColor(getResources().getColor(R.color.colorBlack));
                linearLayoutNext.setBackgroundResource(R.drawable.two_side_rounded_green_background);
                linearLayoutNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent auth = new Intent(Onboarding.this, AuthActivity.class);
                        startActivity(auth);
                        finish();
                    }
                });

            }
            else {
                linearLayoutNext.setBackgroundResource(R.drawable.two_side_rounded_button_background);
                textViewNext.setText(R.string.next);
                textViewNext.setTextColor(getResources().getColor(R.color.colorBlack));
                linearLayoutNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBoardingpager.setCurrentItem(currentPage+1);
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void addPositionDash(int position) {
        dash = new TextView[3];

        //remove any dots and draw new ones (to avoid drawing over existing dots when swiped)
        linearLayoutDots.removeAllViews();

        for (int i = 0; i < dash.length;i++){
            dash[i] = new TextView(this);
            dash[i].setText(Html.fromHtml("&#8212;"));;
            dash[i].setTextColor(getResources().getColor(R.color.colorGray));
            dash[i].setTextSize(30);
            linearLayoutDots.addView(dash[i]);
        }
        if(dash.length > 0){

            //change size and color of the dot of current slide
            dash[position].setTextSize(35);
            dash[position].setTextColor(getResources().getColor(R.color.colorAccent));

        }

    }
}