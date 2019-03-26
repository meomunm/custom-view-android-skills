package techkids.vn.customviewskills.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import techkids.vn.customviewskills.R;

public class PageOneIndicatorFragment extends Fragment{
    private static final String TAG = PageOneIndicatorFragment.class.toString();
    private static final int MESSAGE_COUNTING = 100;
    private static final int MESSAGE_QUIT = 101;

    private TextView tvTittle;
    private Button btPageOne;
    private Handler handler;
    private boolean isCounting = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_indicator, container, false);
        tvTittle = view.findViewById(R.id.tv_tittle_page_one);
        Animation animRotate = AnimationUtils.loadAnimation(Objects.requireNonNull(getActivity()).getApplicationContext(), R.anim.rotato);
        tvTittle.setAnimation(animRotate);
        btPageOne = view.findViewById(R.id.bt_page_one);

        listenHandler();
        listen();

        return view;
    }

    private void countNumber() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    Message message = new Message();
                    message.what = MESSAGE_COUNTING;
                    message.arg1 = i;
                    handler.sendMessage(message);
                    Log.d(TAG, "run: send  i");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "run: send quit");
                handler.sendEmptyMessage(MESSAGE_QUIT);
            }
        }).start();
    }

    private void listenHandler() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_COUNTING:
                        isCounting = true;
                        tvTittle.setText(String.valueOf(msg.arg1));
                        Log.d(TAG, "handleMessage: counting");
                        break;
                    case MESSAGE_QUIT:
                        tvTittle.setText("DONE !");
                        isCounting = false;
                        Log.d(TAG, "handleMessage: quit");
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void listen(){
        btPageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCounting){
                    Log.d(TAG, "onClick: cack");
                    countNumber();
                }
            }
        });
    }
}
