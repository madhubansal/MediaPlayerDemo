package jobs.mara.com.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener, MediaPlaybackService.Callbacks {


    //song url:   http://hcmaslov.d-real.sci-nnov.ru/public/mp3/Mika/Mika%20'Grace%20Kelly'.mp3

    Intent playbackServiceIntent;
    TextView play, startTime, endTime;
    MediaPlaybackService mMediaPlaybackService;
    SeekBar playerseek;


    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Toast.makeText(MediaPlayerActivity.this, "onServiceConnected called", Toast.LENGTH_SHORT).show();
            // We've binded to LocalService, cast the IBinder and get LocalService instance
            MediaPlaybackService.LocalBinder binder = (MediaPlaybackService.LocalBinder) service;
            mMediaPlaybackService = binder.getServiceInstance(); //Get instance of your service!
            mMediaPlaybackService.registerClient(MediaPlayerActivity.this); //Activity register in the service as client for callabcks!
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Toast.makeText(MediaPlayerActivity.this, "onServiceDisconnected called", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (TextView) findViewById(R.id.play);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        play.setOnClickListener(this);
        startTime.setText("00:00");
        playbackServiceIntent = new Intent(this, MediaPlaybackService.class);

        playerseek = (SeekBar) findViewById(R.id.playerseek);

        playerseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setStartTime(i);
                mMediaPlaybackService.seekMediaPlayer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view == play) {

            if (play.getText().toString().equalsIgnoreCase("Play")) {

                play.setText("Pause");
                System.out.println("mMediaPlaybackService: " + mMediaPlaybackService);
                if (mMediaPlaybackService == null) {

                    startService(playbackServiceIntent);
                    bindService(playbackServiceIntent, mConnection, Context.BIND_AUTO_CREATE); //Binding to the service!

                } else {
                    mMediaPlaybackService.playMedia();
                }
            } else {
                play.setText("Play");
                mMediaPlaybackService.pauseMedia();
            }
        }
    }

    @Override
    public void updateTime(int data) {
        setStartTime(data);
    }


    public void setStartTime(int data) {
        int seconds =  (data / 1000) % 60;
        int minutes =  ((data / (1000 * 60)) % 60);
        startTime.setText(minutes + ":" + seconds);
        playerseek.setProgress(data);
    }

    @Override
    public void updateEndTime(int time) {
        int seconds =  (time / 1000) % 60;
        int minutes =  ((time / (1000 * 60)) % 60);
        endTime.setText(minutes + ":" + seconds);
        playerseek.setProgress(0);
        playerseek.setMax(time);
    }
}
