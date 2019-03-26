package techkids.vn.customviewskills.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import techkids.vn.customviewskills.R;

public class PageTwoIndicatorFragment extends Fragment {
    private static final String TAG = PageTwoIndicatorFragment.class.toString();
    private TextView tvTittle;
    private Button btPageTwo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framgent_page_indicator_two, container, false);
        tvTittle = view.findViewById(R.id.tv_tittle_page_two);
        btPageTwo = view.findViewById(R.id.bt_page_two);

        listen();

        return view;
    }

    private void listen() {
        btPageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: new AsyncTask");
                new ProgressAsyncTask().execute(); //buoc phai co excute nhu thread.run();

            }
        });
    }

    private class ProgressAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            for (int i = 0; i <= 100; i++) {
                publishProgress(String.valueOf(i));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "DONE";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "onPostExecute: done");
            tvTittle.setText(result);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.d(TAG, "onProgressUpdate: update");
            tvTittle.setText(values[0]);
        }
    }
}
