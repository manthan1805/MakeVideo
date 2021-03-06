package com.khacchung.makevideo.activity;

import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.khacchung.makevideo.R;
import com.khacchung.makevideo.base.BaseActivity;
import com.khacchung.makevideo.base.ShowLog;
import com.khacchung.makevideo.databinding.ActivityPlayVideoBinding;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.io.File;

public class PlayVideoActivity extends BaseActivity {
    private ActivityPlayVideoBinding binding;
    private String pathVideo;
    private static final String PATH_VIDEO = "path_video";
    private UniversalVideoView mVideoView;
    private UniversalMediaController mMediaController;

    public static final String[] PERMISSION_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static void startIntent(Activity activity, String pathVideo) {
        Intent intent = new Intent(activity, PlayVideoActivity.class);
        intent.putExtra(PATH_VIDEO, pathVideo);
        activity.startActivity(intent);
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pathVideo));
//        intent.setDataAndType(Uri.parse(pathVideo), "video/*");
//        activity.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            intentShareVideo(pathVideo);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        enableBackButton();
        setTitleToolbar(getString(R.string.watching));
        realtimePermission(PERMISSION_LIST);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_video);

        Intent intent = getIntent();
        if (intent != null) {
            pathVideo = intent.getStringExtra(PATH_VIDEO);
        }
        if (pathVideo == null || pathVideo.isEmpty()) {
            ShowLog.ShowLog(this, binding.getRoot(), getString(R.string.errror), false);
            finish();
        }

        initVideo();
    }

    private void initVideo() {
        File file = new File(pathVideo);
        if (file.exists()) {
            mVideoView = binding.videoView;
            mMediaController = binding.mediaController;
            mVideoView.setMediaController(mMediaController);
            mVideoView.setVideoURI(Uri.parse(file.getAbsolutePath()));
            mVideoView.start();
        } else {
            ShowLog.ShowLog(this, binding.getRoot(), getString(R.string.errror), false);
            finish();
        }
    }
}
