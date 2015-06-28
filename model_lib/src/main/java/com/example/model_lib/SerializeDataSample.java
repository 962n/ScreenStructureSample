package com.example.model_lib;


import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeDataSample {
    Logger log = Logger.getRootLogger();


    private MainThreadInterface mainThreadIF;
    private FilePathInterface filePathIF;
    private ThreadTaskQueue taskQueue;
    private static Object saveLock = new Object();
    private static final String FILE_NAME = "SerializeDataSample.dat";

    public interface WriteListener {
        public void onWriteFinished();
    }

    ;

    public interface ReadListener {
        public void onReadFinished(SampleEntity entity);
    }

    ;


    public SerializeDataSample() {

        OsInterfaceSettings settings = OsInterfaceSettings.getInstance();
        mainThreadIF = settings.getMainThreadIF();
        filePathIF = settings.getFilePathIF();
        taskQueue = ThreadTaskQueue.getInstance();
    }

    public void write(final SampleEntity entity, final WriteListener listener) {

        //コールバック用の処理を用意
        final Runnable uiCallback = new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onWriteFinished();
                }
            }
        };

        Runnable r = new Runnable() {
            @Override
            public void run() {
                log.debug("hogehoge write Thread name = "+Thread.currentThread().getName());
                write(entity);
                mainThreadIF.runUiThread(uiCallback);
            }
        };


        taskQueue.addTask(r);
    }


    private void write(SampleEntity entity) {
        String path = filePathIF.getFilesDir() + "/" + FILE_NAME;
        synchronized (saveLock) {

            try {
                FileOutputStream fileOut = new FileOutputStream(path);
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
                outputStream.writeObject(entity);
                outputStream.close();
            } catch (FileNotFoundException e) {
                //saveができなかった場合の復旧ができないのでそのままスルーする
                //そもそも発生し得ない。
                e.printStackTrace();
            } catch (IOException e) {
                //saveができなかった場合の復旧ができないのでそのままスルーする
                //そもそも発生し得ない。
                e.printStackTrace();
            }
        }
    }

    public void read(final ReadListener listener) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                log.debug("hogehoge read Thread name = "+Thread.currentThread().getName());

                final SampleEntity entity = read();
                mainThreadIF.runUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(listener != null){
                            listener.onReadFinished(entity);
                        }
                    }
                });
            }
        };


        taskQueue.addTask(r);
    }

    private SampleEntity read() {
        String path = filePathIF.getFilesDir() + "/" + FILE_NAME;

        SampleEntity entity = null;

        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream inputStream = new ObjectInputStream(fileIn);
            entity = (SampleEntity)inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException e) {
            //最後にデータがnullかどうかでエラー処理するのでここではしない
            e.printStackTrace();
        } catch (IOException e) {
            //最後にデータがnullかどうかでエラー処理するのでここではしない
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            //最後にデータがnullかどうかでエラー処理するのでここではしない
            e.printStackTrace();
        }
        if(entity == null){
            entity = new SampleEntity();
        }
        return entity;
    }

}
