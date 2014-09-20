package link.techlogics.inthefuture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hima on 14/09/21.
 */
public class GameActivity extends Activity {


    private Button btnStartStop;
    private TextView timerText;
    private TextView lifeText;
    private TextView clearText;
    private TextView goalTime;
    private Timer timer;
    private int lifePoint = 3;
    private boolean isStart = false;
    private int clearCount = 0;
    private int seconds1 = 0;
    private int seconds2 = 0;
    private int mSeconds1 = 0;
    private int mSeconds2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timerText = (TextView) findViewById(R.id.timer_text);
        goalTime = (TextView) findViewById(R.id.goal_time);

        lifeText = (TextView) findViewById(R.id.life_text);
        lifeText.setText("残りライフ：" + lifePoint);

        clearText = (TextView) findViewById(R.id.clear_text);
        clearText.setText("クリア回数：" + clearCount);

        btnStartStop = (Button) findViewById(R.id.btn_start_stop);
        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStart) {
                    btnStartStop.setEnabled(false);
                    isStart = false;

                    btnStartStop.setText("スタート！");

                    if (timer != null) {
                        timer.cancel();
                        timer.purge();
                        timer = null;

                        if (checkClear()) {
                            clearCount++;
                            if (clearCount % 10 == 0 && lifePoint < 3) {
                                lifePoint++;
                                goalTime.setText("クリア！\nライフポイント回復！");
                                lifeText.setText("残りライフ：" + lifePoint);
                            } else {
                                goalTime.setText("クリア！\nおめでとうございます！");
                            }

                            clearText.setText("クリア回数：" + clearCount);
                            (new Handler()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    seconds1 = 0;
                                    seconds2 = 0;
                                    mSeconds1 = 0;
                                    mSeconds2 = 0;

                                    timerText.setText(seconds1 + "" + seconds2 + ":" + mSeconds1 + "" + mSeconds2);
                                    goalTime.setText("目指せ5秒ジャスト！");
                                    btnStartStop.setEnabled(true);
                                }
                            }, 2000);
                        } else {
                            lifePoint--;

                            if (lifePoint == 0) {
                                goalTime.setText("ライフが0になりました！");
                                (new Handler()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                                        intent.putExtra("clearCount", clearCount);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 2000);
                            } else {
                                goalTime.setText("残念！\nライフが1減っちゃった！");
                                lifeText.setText("残りライフ：" + lifePoint);

                                (new Handler()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        seconds1 = 0;
                                        seconds2 = 0;
                                        mSeconds1 = 0;
                                        mSeconds2 = 0;

                                        timerText.setText(seconds1 + "" + seconds2 + ":" + mSeconds1 + "" + mSeconds2);
                                        goalTime.setText("目指せ5秒ジャスト！");
                                        btnStartStop.setEnabled(true);
                                    }
                                }, 2000);
                            }

                        }
                    }
                } else {
                    isStart = true;

                    btnStartStop.setText("ストップ！");

                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            timerMethod();
                        }
                    }, 1, 10);
                }

            }
        });

    }

    private void timerMethod() {
        this.runOnUiThread(generate);
    }

    private Runnable generate = new Runnable() {
        @Override
        public void run() {
            timerText.setText(seconds1 + "" + seconds2 + ":" + mSeconds1 + "" + mSeconds2);
            mSeconds2++;
            if (mSeconds2 == 10) {
                mSeconds2 = 0;
                mSeconds1++;
            }

            if (mSeconds1 == 10) {
                mSeconds1 = 0;
                seconds2++;
            }

            if (seconds2 == 10) {
                seconds2 = 0;
                seconds1++;
            }
        }
    };

    private boolean checkClear() {
        if (seconds2 > 3 && seconds2 < 5) {
            if (mSeconds1 > 8) {
                return true;
            }
        } else if (seconds2 > 4 && seconds2 < 6) {
            if (mSeconds1 < 1) {
                return true;
            }
        }
        return false;
    }

}
