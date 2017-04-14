package com.foldhorslidview.main;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import comm.custom.CustomViewPager;
import comm.custom.DimensUtils;
import comm.custom.MyPageTransformer;
import comm.custom.RelativeLayoutViewPager;


public class MainActivity extends AppCompatActivity {
    private RelativeLayoutViewPager view_pager_parent;
    private CustomViewPager cvp_view_pager;
    private CustomViewPagerAdapter pagerAdapter;//122
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setImagePathData(null);
    }

    /**
     * 初始化，View
     */
    public void initView() {
        view_pager_parent = (RelativeLayoutViewPager) findViewById(R.id.view_pager_parent);
        cvp_view_pager = (CustomViewPager) findViewById(R.id.cvp_view_pager);
        cvp_view_pager.setOffscreenPageLimit(5);
        cvp_view_pager.setPageTransformer(true, new MyPageTransformer());
        cvp_view_pager.setPageMargin(-(DimensUtils.dip2px(this,
                (float) 62.7)));
        view_pager_parent
                .setOnClickPoint(new RelativeLayoutViewPager.OnClickPoint() {
                    @Override
                    public void onClickPoint(int pX, int pY) {
                        try {
                            int clickRange = view_pager_parent
                                    .getClickRange(pX, pY, cvp_view_pager);
                            if (clickRange == RelativeLayoutViewPager.ClickRange.LEFTTWO) {
                                int currentItem = cvp_view_pager
                                        .getCurrentItem();
                                cvp_view_pager.setCurrentItem(currentItem - 2,
                                        true);
                            } else if (clickRange == RelativeLayoutViewPager.ClickRange.LEFT) {
                                int currentItem = cvp_view_pager
                                        .getCurrentItem();
                                cvp_view_pager.setCurrentItem(currentItem - 1,
                                        true);
                            } else if (clickRange == RelativeLayoutViewPager.ClickRange.RIGHT) {
                                int currentItem = cvp_view_pager
                                        .getCurrentItem();
                                cvp_view_pager.setCurrentItem(currentItem + 1,
                                        true);
                            } else if (clickRange == RelativeLayoutViewPager.ClickRange.RIGHTTWO) {
                                int currentItem = cvp_view_pager
                                        .getCurrentItem();
                                cvp_view_pager.setCurrentItem(currentItem + 2,
                                        true);
                            } else {
                            }
                        } catch (Exception e) {
                        }

                    }

                    @Override
                    public void onClickPointByScreen(int pX, int pY) {

                    }

                    @Override
                    public void toLeft() {
                        int currentItem = cvp_view_pager.getCurrentItem();
                        cvp_view_pager.setCurrentItem(currentItem - 1, true);
                    }

                    @Override
                    public void toRight() {
                        int currentItem = cvp_view_pager.getCurrentItem();
                        cvp_view_pager.setCurrentItem(currentItem + 1, true);
                    }
                });

    }


    /**
     * 设置图片左右滑动的路径
     * @param paths
     */
    private void setImagePathData(List<String> paths) {
        pagerAdapter = new CustomViewPagerAdapter(paths);
        cvp_view_pager.setAdapter(pagerAdapter);
        cvp_view_pager.setCurrentItem(200);
    }

    class CustomViewPagerAdapter extends PagerAdapter {
        private List<String> imgPaths;

        private int[] bitmapRes;

        public CustomViewPagerAdapter(List<String> apps) {
            imgPaths  = apps;
            bitmapRes=new int[]{R.mipmap.bg_me_head,R.mipmap.bg_sanheyi_hong,R.mipmap.bg_sanheyi_lv,R.mipmap.bg_sanheyi_zi};
        }
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        View view;
        ImageView iv_img_bg;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int index = position % bitmapRes.length;
           int res = bitmapRes[index];
            view = LayoutInflater.from(container.getContext()).inflate(
                    R.layout.card_viewpager_hor_item, null);
            iv_img_bg = (ImageView) view.findViewById(R.id.iv_img_bg);
            iv_img_bg.setImageResource(res);
            container.addView(view);
            return view;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
