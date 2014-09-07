package cn.embel.sr;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SoundRecorderActivity extends Activity implements OnClickListener {

	private Button btnStart;
	private Button btnStop;
	private Button btnPlay;

	private MediaRecorder mMediaRecorder;
	private File recAudioFile;
	private MusicPlayer mPlayer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setupViews();
	}

	private void setupViews() {
		btnStart = (Button) findViewById(R.id.start);
		btnStop = (Button) findViewById(R.id.stop);
		btnPlay = (Button) findViewById(R.id.play);
		
		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
		
		recAudioFile = new File("/mnt/sdcard", "new.amr");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start:
			startRecorder();
			break;
		case R.id.stop:
			stopRecorder();
			break;
		case R.id.play:
			mPlayer = new MusicPlayer(SoundRecorderActivity.this);
			mPlayer.playMicFile(recAudioFile);
			break;
		default:
			break;
		}
	}

	private void startRecorder() {
		mMediaRecorder = new MediaRecorder();
		if (recAudioFile.exists()) {
			recAudioFile.delete();
		}

		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); 
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		mMediaRecorder.setOutputFile(recAudioFile.getAbsolutePath());
		try {
			mMediaRecorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mMediaRecorder.start();
	}
	
	private void stopRecorder(){
		if (recAudioFile!=null) {
			mMediaRecorder.stop();
			mMediaRecorder.release();
		}
	}
}