package jobs.mara.com.myapplication;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by mithilesh on 16/1/17.
 */
public class MediaPlaybackService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {


    //    public static MediaPlaybackService mMediaPlaybackService;
    private final IBinder mBinder = new LocalBinder();
    //    private MediaSessionCompat mMediaSession;
//    private PlaybackStateCompat.Builder mStateBuilder;
    Context context;
    String LOG_TAG = "MediaPlaybackService";
    String url = "http://hcmaslov.d-real.sci-nnov.ru/public/mp3/Mika/Mika%20'Grace%20Kelly'.mp3"; // your URL here
    MediaPlayer mediaPlayer;
    Callbacks activity;
    Handler handler = new Handler();
    private int startTime = 0;
    private int endTime = 0;
    private int millis = 0;
    Runnable serviceRunnable = new Runnable() {
        @Override
        public void run() {
            if (millis < endTime) {
                millis = millis + 1000;
                activity.updateTime(millis); //Update Activity (client) by the implementd callback
                handler.postDelayed(this, 1000);
            }
        }
    };


//    public static MediaPlaybackService getInstance() {
//
//        if (mMediaPlaybackService == null) {
//            mMediaPlaybackService = new MediaPlaybackService();
//        }
//        return mMediaPlaybackService;
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(LOG_TAG, "onStartCommand");

        if (mediaPlayer == null) {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(url);
                // might take long! (for buffering, etc)
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnCompletionListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return START_STICKY;
    }


    public void playMedia() {
        mediaPlayer.start();
    }

    public void seekMediaPlayer(int time) {
        millis = time;
        mediaPlayer.seekTo(time);
    }


    public void pauseMedia() {
        handler.removeCallbacks(serviceRunnable);

        mediaPlayer.pause();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOG_TAG, "onCreate");
        context = this;

//        mMediaSession = new MediaSessionCompat(context, LOG_TAG);
//        mMediaSession.setFlags(
//                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
//                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
//        mStateBuilder = new PlaybackStateCompat.Builder()
//                .setActions(
//                        PlaybackStateCompat.ACTION_PLAY |
//                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
//        mMediaSession.setState(mStateBuilder.build());


    }

    //Here Activity register to the service as Callbacks client
    public void registerClient(Activity a) {
        this.activity = (Callbacks) a;
    }

    public void onDestroy() {
        Log.v(LOG_TAG, "onDestroy");
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.v(LOG_TAG, "onPrepared");
        mediaPlayer.start();

        System.out.println("mediaPlayer.getDuration():  " + mediaPlayer.getDuration());
        endTime = mediaPlayer.getDuration();
        handler.postDelayed(serviceRunnable, 0);
        activity.updateEndTime(mediaPlayer.getDuration());
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.v(LOG_TAG, "onCompletion");
        stopSelf();

    }

    public interface Callbacks {
        public void updateTime(int data);

        public void updateEndTime(int data);
    }

    public class LocalBinder extends Binder {
        public MediaPlaybackService getServiceInstance() {
            return MediaPlaybackService.this;
        }
    }

//    @Nullable
//    @Override
//    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
//        return null;
//    }
//
//    @Override
//    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
//
//    }
}
