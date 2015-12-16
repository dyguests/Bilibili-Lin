package com.fanhl.bilibili.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.fanhl.bilibili.R;
import com.fanhl.bilibili.Secret;
import com.fanhl.bilibili.rest.XmlDownloader;
import com.fanhl.bilibili.rest.model.VideoInfo;
import com.fanhl.bilibili.rest.model.VideoM;
import com.fanhl.bilibili.ui.base.BaseActivity;
import com.fanhl.bilibili.ui.fragment.video.VideoDetailsFragment;
import com.fanhl.bilibili.util.DanmakuParser;
import com.fanhl.util.GsonUtil;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
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
    @Bind(R.id.player_container)
    FrameLayout          mPlayerContainer;
    @Bind(R.id.video_view)
    VideoView            mVideoView;
    @Bind(R.id.danmaku_view)
    DanmakuView          mDanmakuView;
    @Bind(R.id.cover)
    ImageView            mCover;

    /*基础数据,跳转到当前Activity时传入的数据*/
    private VideoInfo baseData;


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private BaseDanmakuParser mDanmakuParser;
    private String            mXMLFileName;

    /*暂停时存放播放的位置*/
    private int mLastPosition;

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
    protected void onResume() {
        super.onResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
            mDanmakuView.seekTo((long) mLastPosition);
        }
        //todo 看看能不能保留缓冲的视频
        if (mVideoView != null && !mVideoView.isPlaying()) {
            mVideoView.start();
            mVideoView.seekTo(mLastPosition);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mLastPosition = mVideoView.getCurrentPosition();
            mVideoView.pause();
        }

        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null && mVideoView.isDrawingCacheEnabled()) {
            mVideoView.destroyDrawingCache();
        }
        if (mDanmakuView != null && mDanmakuView.isPaused()) {
            mDanmakuView.release();
        }
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
        //取得视频页面信息(视频简介,视频相关...)
        // FIXME: 15/12/15 改Observable成 先加载视频信息,再加载视频
        app().getClient().getVideoService().relatedVideos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(relatedVideos -> {
                    // FIXME: 15/12/15 这个接口还没写好
                    Log.d(TAG, "相关视频信息:" + relatedVideos.toString());
                }, throwable -> {
                    Log.d(TAG, "相关视频信息取得失败:" + Log.getStackTraceString(throwable));
                });
//        refreshPlayData();
    }

    /**
     * 视频播放信息
     */
    private void refreshPlayData() {
        //取得视频播放信息
        Observable<VideoM> videoMObservable = app().getClient().getVideoService().getVideoApiRx("3337029")
                .subscribeOn(Schedulers.io());
        //弹幕
        Observable<Void> danmakuObservable = videoMObservable
                .map(videoM -> {
                    if (videoM.getCid().contentEquals("undefined")) return "error";
                    mXMLFileName = videoM.getCid().substring(videoM.getCid().lastIndexOf('/') + 1);
                    String CID = mXMLFileName.substring(0, mXMLFileName.lastIndexOf("."));
                    return "http://www.bilibilijj.com/ashx/Barrage.ashx?f=true&av=&p=&s=xml&cid=" + CID + "&n=" + CID;
                })
                .flatMap(string -> {
                    if ("error".equals(string)) return Observable.error(new Exception("视频不存在或不能播放."));
                    return XmlDownloader.download(string);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::prepareDanmaku);
        //视频
        Observable<Void> videoObservable = videoMObservable
                .map(VideoM::getCid)
                .map(s -> {
                    if ("undefined".contentEquals(s)) return "error";
                    return s.substring(s.lastIndexOf('/') + 1, s.lastIndexOf("."));
                })
                .flatMap(cid -> {
                    if ("error".equals(cid)) return Observable.error(new Exception("视频不存在或不能播放."));
                    return app().getClient().getVideoService().getVideoApi("json", cid, "mp4", 4, Secret.App_Key);
                })
                .map(videoHDM -> Uri.parse(videoHDM.getDurl().get(0).getUrl()))
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::prepareVideo);
        //视频弹幕同时加载后才播放
        Observable
                .merge(danmakuObservable, videoObservable)
                .last()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                }, e -> {
                    Snackbar.make(mMainContent, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }, () -> {
                    mVideoView.start();
                    mDanmakuView.start();
                });
    }

    /**
     * danmaku加载
     *
     * @param xmlFile
     * @return
     */
    public Observable<Void> prepareDanmaku(final File xmlFile) {
        return Observable.<Void>create(subscriber -> {
            try {
                mDanmakuParser = DanmakuParser.createParser(xmlFile);
                mDanmakuView.setCallback(new DrawHandler.Callback() {
                    @Override
                    public void prepared() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void updateTimer(DanmakuTimer danmakuTimer) {
                    }

                    @Override
                    public void drawingFinished() {
                    }
                });
                mDanmakuView.prepare(mDanmakuParser, new DanmakuContext());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        });
    }

    /**
     * 视频加载
     *
     * @param src
     * @return
     */
    private Observable<Void> prepareVideo(Uri src) {
        return Observable.<Void>create(subscriber -> {
            mVideoView.setVideoURI(src);
            mVideoView.setOnPreparedListener(mp -> subscriber.onCompleted());
        });
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
