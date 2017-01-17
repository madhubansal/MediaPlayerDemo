package jobs.mara.com.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

/**
 * Created by mithilesh on 17/1/17.
 */
public class SecondMediaPlayer {//extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
//    MediaPlayer mMediaPlayer;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//    private void createMediaPlayerIfNeeded() {
//        if (mMediaPlayer == null) {
//            mMediaPlayer = new MediaPlayer();
//
//            // Make sure the media player will acquire a wake-lock while
//            // playing. If we don't do that, the CPU might go to sleep while the
//            // song is playing, causing playback to stop.
//            mMediaPlayer.setWakeMode(mService.getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
//
//            // we want the media player to notify us when it's ready preparing,
//            // and when it's done playing:
//            mMediaPlayer.setOnPreparedListener(this);
//            mMediaPlayer.setOnCompletionListener(this);
//            mMediaPlayer.setOnErrorListener(this);
//            mMediaPlayer.setOnSeekCompleteListener(this);
//        } else {
//            mMediaPlayer.reset();
//        }
//    }
//
//
//
//    @Override
//    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//        return false;
//    }
//
//    @Override
//    public void onPrepared(MediaPlayer mediaPlayer) {
//        configMediaPlayerState();
//    }
//
//    @Override
//    public void onCompletion(MediaPlayer mediaPlayer) {
//
//    }
}
