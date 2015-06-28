package com.sample.structure.screen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.model_lib.SampleEntity;
import com.example.model_lib.SerializeDataSample;
import com.sample.structure.screen.R;

/**
 * ダミーフラグメントとりあえず表示する用
 */
public class DummyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dummy, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SerializeDataSample sample = new SerializeDataSample();
                sample.read(new SerializeDataSample.ReadListener() {
                    @Override
                    public void onReadFinished(SampleEntity entity) {
                        Log.d("hogehoge","entity = "+entity.toString());
                        Log.d("hogehoge","onReadFinished = "+Thread.currentThread().getName());
                    }
                });

            }
        });
        view.findViewById(R.id.write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SampleEntity entity = new SampleEntity();
                entity.setWeight("90きろ");
                entity.setHeight("196センチ");
                entity.setAge("19さい");
                entity.setName("ほげたろう");
                entity.setNo(9);
                SerializeDataSample sample = new SerializeDataSample();
                sample.write(entity, new SerializeDataSample.WriteListener() {
                    @Override
                    public void onWriteFinished() {
                        Log.d("hogehoge","書き込み完了 ");
                        Log.d("hogehoge","onWriteFinished = "+Thread.currentThread().getName());
                    }
                });
            }
        });

    }
}
