package com.example.model_lib;

/**
 * Created by takahiro on 2015/06/28.
 */
public class OsInterfaceSettings {

    private static OsInterfaceSettings instance = new OsInterfaceSettings();
    private MainThreadInterface mainThreadIF;
    private FilePathInterface filePathIF;

    public static OsInterfaceSettings getInstance(){
        return instance;
    }

    private OsInterfaceSettings() {
    }

    public void init(MainThreadInterface mainThreadIF,FilePathInterface filePathIF){
        this.mainThreadIF = mainThreadIF;
        this.filePathIF = filePathIF;
    }

    public MainThreadInterface getMainThreadIF(){
        return mainThreadIF;
    }
    public FilePathInterface getFilePathIF(){
        return filePathIF;
    }

}
