package com.huifeideyema.net.download;

import java.io.File;

/**
 * @author : mr.zang
 * description
 * createDate: 2020-01-02 17:14
 */
public class DownloadedCallBack implements DownloadCallback {
    /**
     * 开始
     *
     * @param downloadData
     */
    @Override
    public void onStart(DownloadData downloadData) {

    }

    /**
     * 下载中
     *
     * @param downloadData
     */
    @Override
    public void onProgress(DownloadData downloadData) {

    }

    /**
     * 暂停
     */
    @Override
    public void onPause() {

    }

    /**
     * 取消
     */
    @Override
    public void onCancel() {

    }

    /**
     * 完成
     *
     * @param downloadData
     * @param file
     */
    @Override
    public void onFinish(DownloadData downloadData, File file) {

    }

    /**
     * 等待下载
     */
    @Override
    public void onWait() {

    }

    /**
     * 出错
     *
     * @param error
     */
    @Override
    public void onError(String error) {

    }
}
