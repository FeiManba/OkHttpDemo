package com.huifeideyema.net.download;

import java.io.File;

public interface DownloadCallback {
    /**
     * 开始
     */
    void onStart(DownloadData downloadData);

    /**
     * 下载中
     */
    void onProgress(DownloadData downloadData);

    /**
     * 暂停
     */
    void onPause();

    /**
     * 取消
     */
    void onCancel();

    /**
     * 完成
     */
    void onFinish(DownloadData downloadData, File file);

    /**
     * 等待下载
     */
    void onWait();

    /**
     * 出错
     *
     * @param error
     */
    void onError(String error);
}
