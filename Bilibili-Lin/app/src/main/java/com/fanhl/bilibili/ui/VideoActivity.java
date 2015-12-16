package com.fanhl.bilibili.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.fanhl.bilibili.R;
import com.fanhl.bilibili.rest.model.VideoInfo;
import com.fanhl.bilibili.ui.base.BaseActivity;
import com.fanhl.bilibili.ui.fragment.video.VideoDetailsFragment;
import com.fanhl.util.GsonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import master.flame.danmaku.ui.widget.DanmakuView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 视频详细界面
 */
public class VideoActivity extends BaseActivity {
    public static final String TAG = VideoActivity.class.getSimpleName();

    public static final String EXTRA_VIDEO_INFO_DATA = "EXTRA_VIDEO_INFO_DATA";

    @Bind(R.id.main_content)
    CoordinatorLayout    mMainContent;
    @Bind(R.id.appbar)
    AppBarLayout         mAppbar;
    @Bind(R.id.toolbar)
    Toolbar              mToolbar;
    @Bind(R.id.tabs)
    TabLayout            mTabs;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.container)
    ViewPager            mViewPager;
    //player_container 视频播放相关----------------------------
    @Bind(R.id.video_view)
    VideoView            mVideoView;
    @Bind(R.id.danmaku_view)
    DanmakuView          mDanmakuView;
    @Bind(R.id.cover)
    ImageView            mCover;

    /*基础数据,跳转到当前Activity时传入的数据*/
    private VideoInfo baseData;


    private SectionsPagerAdapter mSectionsPagerAdapter;

    public static void launch(Activity activity, VideoInfo baseData) {
        Intent intent = new Intent(activity, VideoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra(EXTRA_VIDEO_INFO_DATA, GsonUtil.json(baseData));
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        assignViews();
        initData();
        refreshData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void assignViews() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabs.setupWithViewPager(mViewPager);

        mFab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());


    }

    private void initData() {
        Intent intent = getIntent();
        baseData = GsonUtil.obj(intent.getStringExtra(EXTRA_VIDEO_INFO_DATA), VideoInfo.class);
    }

    private void refreshData() {
        // FIXME: 15/12/15 改Observable成 先加载视频信息,再加载视频
        app().getClient().getVideoService().videoDetial()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    // FIXME: 15/12/15 这个接口还没写好
                });

//        Observable.<String>create(subscriber -> subscriber.onNext("朽木吃粑粑"))
//                .asObservable()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .<String>map(s -> s+"x2")
//                .<String>flatMap(s->Observable.just(s+"x2"))
//                .subscribe(s -> Log.d(TAG, "s:"+s));
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle              args     = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View     rootView = inflater.inflate(R.layout.fragment_video_palceholder, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private VideoDetailsFragment videoDetailsFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (videoDetailsFragment == null) videoDetailsFragment = VideoDetailsFragment.newInstance(baseData);
                    return videoDetailsFragment;
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
