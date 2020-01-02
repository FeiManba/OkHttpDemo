package com.huifeideyema.net.download;

public interface IBatchListener {
    /**
     * 批次任务完成回调
     */
    void batchTaskFinished(DownloadData filePoint);

    /**
     * 任务下载完毕回调
     *
     * @param filePoint
     */
    void onFinished(DownloadData filePoint);

    /**
     * 批次下载任务进度
     * @param filePoint
     */
    void onProgress(DownloadData filePoint);

    /**
     * 任务下载暂停
     *
     * @param filePoint
     */
    void onPause(DownloadData filePoint);

    /**
     * 取消批次任务下载
     *
     * @param filePoint
     */
    void onCancel(DownloadData filePoint);

    /**
     * 开始下载
     * @param filePoint
     */
    void onStart(DownloadData filePoint);
}
