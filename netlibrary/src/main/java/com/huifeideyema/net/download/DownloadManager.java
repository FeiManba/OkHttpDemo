//package com.huifeideyema.net.download;
//
//import android.os.Environment;
//import android.text.TextUtils;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 下载管理器，断点续传
// *
// * @author Cheny
// */
//public class DownloadManager implements IBatchListener {
//    /**
//     * 默认下载目录
//     */
//    private String DEFAULT_FILE_DIR;
//    /**
//     * 批次缓存 集合
//     */
//    private ConcurrentHashMap<String, BatchTaskList<DownloadData>> batchMap;
//    /**
//     * 任务缓存 key taskId value downloadTask
//     */
//    private Map<String, DownloadTask> mDownloadTasks;
//
//    private static DownloadManager mInstance;
//
//    private static final String TAG = "DownloadManager";
//
//    /**
//     * 下载文件
//     */
//    public void download(String... taskIds) {
//        //单任务开启下载或多任务开启下载
//        for (String taskId : taskIds) {
//            if (mDownloadTasks.containsKey(taskId)) {
//                DownloadTask downloadTask = mDownloadTasks.get(taskId);
//                if (downloadTask != null) {
//                    downloadTask.start();
//                }
//            }
//        }
//    }
//
//
//    // 获取下载文件的名称
//    public String getFileName(String url) {
//        return url.substring(url.lastIndexOf("/") + 1);
//    }
//
//    /**
//     * 暂停
//     */
//    public void pause(String... taskIds) {
//        //单任务暂停或多任务暂停下载
//        for (String taskId : taskIds) {
//            if (mDownloadTasks.containsKey(taskId)) {
//                DownloadTask downloadTask = mDownloadTasks.get(taskId);
//                if (downloadTask != null) {
//                    downloadTask.pause();
//                }
//            }
//        }
//    }
//
//    /**
//     * 取消下载
//     */
//    public void cancel(String... taskIds) {
//        //单任务取消或多任务取消下载
//        for (String taskId : taskIds) {
//            if (mDownloadTasks.containsKey(taskId)) {
//                DownloadTask downloadTask = mDownloadTasks.get(taskId);
//                if (downloadTask != null) {
//                    downloadTask.cancel();
//                }
//            }
//        }
//    }
//
//    /**
//     * 添加下载任务
//     *
//     * @param batchId       批次Id
//     * @param fileId        文件Id
//     * @param url           文件网路路径
//     * @param localFilePath 文件本地保存路径
//     * @param fileName      文件名称
//     * @param fileType      文件类型
//     * @return
//     */
//    public DownloadManager add(String batchId, String fileId, String url, String localFilePath, String fileName, int fileType) {
//        if (TextUtils.isEmpty(localFilePath)) {//没有指定下载目录,使用默认目录
//            localFilePath = getDefaultDirectory();
//        }
//        if (TextUtils.isEmpty(fileName)) {
//            fileName = getFileName(url);
//        }
//
//        DownloadData filePoint = new DownloadData.Builder()
//                .setBatchId(fileId)
//                .setUrl(url)
//                .setName(fileName)
//                .setType(fileType)
//                .setPath(localFilePath)
//                .build();
//        mDownloadTasks.put(fileId, new DownloadTask(filePoint, this));
//        return this;
//    }
//
//
//    /**
//     * 添加下载任务
//     *
//     * @param batchId      批次任务Id
//     * @param downloadData 任务对象
//     * @return
//     */
//    public DownloadManager addTasks(String batchId, List<DownloadData> downloadData) {
//        if (batchMap == null) return this;
//        if (TextUtils.isEmpty(batchId)) return this;
//
//        BatchTaskList<DownloadData> batchTaskList = null;
//        if (batchMap.containsKey(batchId)) {
//            batchTaskList = batchMap.get(batchId);
//        } else {
//            batchTaskList = new BatchTaskList<>();
//        }
//
//        for (DownloadData f : downloadData) {
//            //没有指定下载目录,使用默认目录
//            if (TextUtils.isEmpty(f.getPath())) {
//                f.setPath(getDefaultDirectory());
//            }
//
//            if (TextUtils.isEmpty(f.getName())) {
//                f.setName(getFileName(f.getUrl()));
//            }
//            mDownloadTasks.put(f.getBatchId(), new DownloadTask(batchId, f, this));
//            try {
//                if (batchTaskList != null) {
//                    batchTaskList.addTask(f.getBatchId(), f);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (batchTaskList != null) {
//            //添加批次Id
//            batchMap.put(batchId, batchTaskList);
//        }
//        return this;
//    }
//
//    /**
//     * 默认下载目录
//     *
//     * @return
//     */
//    private String getDefaultDirectory() {
//        if (TextUtils.isEmpty(DEFAULT_FILE_DIR)) {
//            DEFAULT_FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()
//                    + File.separator + "hm" + File.separator;
//        }
//        return DEFAULT_FILE_DIR;
//    }
//
//    public static DownloadManager getInstance() {//管理器初始化
//        if (mInstance == null) {
//            synchronized (DownloadManager.class) {
//                if (mInstance == null) {
//                    mInstance = new DownloadManager();
//                }
//            }
//        }
//        return mInstance;
//    }
//
//    private DownloadManager() {
//        batchMap = new ConcurrentHashMap<>();
//        mDownloadTasks = new HashMap<>();
//    }
//
//    /**
//     * 取消下载
//     */
//    public boolean isDownloading(String... taskIds) {
//        //这里传一个url就是判断一个下载任务
//        //多个url数组适合下载管理器判断是否作操作全部下载或全部取消下载
//        boolean result = false;
//        for (int i = 0, length = taskIds.length; i < length; i++) {
//            String taskId = taskIds[i];
//            if (mDownloadTasks.containsKey(taskId)) {
//                result = mDownloadTasks.get(taskId).isDownloading();
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public void batchTaskFinished(FilePoint filePoint) {
//        try {
//            String batchId = filePoint.getBatchId();
//            String taskId = filePoint.getFileId();
//            if (batchMap.containsKey(batchId)) {
//                BatchTaskList<FilePoint> batchTaskList = batchMap.get(batchId);
//                batchTaskList.removeTask(taskId);
//                int allTaskCount = batchTaskList.getAllTaskCount();
//                int currentTaskFinishedCount = batchTaskList.getCurrentTaskFinishedCount();
//
//                if (batchTaskList.getTaskCount() == 0) {
//                    batchMap.remove(batchId);
//                }
//                filePoint.setCacheType(CacheType.CACHE_TYPE_FINISHED);
//                filePoint.setAllTaskCount(allTaskCount);
//                filePoint.setCurrentTaskFinishedCount(currentTaskFinishedCount);
//                EventBus.getDefault().post(filePoint);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onFinished(FilePoint filePoint) {
//        if (filePoint == null) {
//            return;
//        }
//        if (filePoint.getFileType() == AREnumType.VIDEO_CACHE_FILE.ordinal()) {
//            if (filePoint.getCacheType() == CacheType.CACHE_TYPE_FINISHED) {
//                try {
//                    FileCacheModelDao fileCacheModelDao = BaseApplication.getDaoSession()
//                            .getFileCacheModelDao();
//                    FileCacheModel fileCacheModel = fileCacheModelDao.queryBuilder()
//                            .where(FileCacheModelDao.Properties.Id.eq(filePoint.getFileId())).unique();
//                    if (fileCacheModel.getCacheType() == CacheType.CACHE_TYPE_FINISHED) {
//                        return;
//                    }
//                    fileCacheModel.setLocalFilePath(filePoint.getCacheFilePath() + "/" + filePoint.getCacheFileName());
//                    fileCacheModel.setCacheType(filePoint.getCacheType());
//                    fileCacheModelDao.update(fileCacheModel); //更新数据
//                    //更新批处理进度
//                    BatchCacheModel batchCacheModel = BaseApplication.getDaoSession().getBatchCacheModelDao().queryBuilder()
//                            .where(BatchCacheModelDao.Properties.Id.eq(filePoint.getBatchId()))
//                            .unique();
//                    if (batchCacheModel != null) {
//                        batchCacheModel.setCurrentCount(batchCacheModel.getCurrentCount() + 1);
//                        BaseApplication.getDaoSession().getBatchCacheModelDao().insertOrReplace(batchCacheModel);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        EventBus.getDefault().post(filePoint);
//    }
//
//    @Override
//    public void onProgress(FilePoint filePoint) {
//        EventBus.getDefault().post(filePoint);
//    }
//
//    @Override
//    public void onPause(FilePoint filePoint) {
//        pause(filePoint.getFileId());
//        EventBus.getDefault().post(filePoint);
//    }
//
//    @Override
//    public void onCancel(FilePoint filePoint) {
//        cancel(filePoint.getFileId());
//        EventBus.getDefault().post(filePoint);
//    }
//
//    @Override
//    public void onStart(FilePoint filePoint) {
//        EventBus.getDefault().post(filePoint);
//    }
//
//    /**
//     * 当前列表是否存在该批次任务
//     *
//     * @param batchId
//     * @return
//     */
//    public boolean isBatchTaskList(String batchId) {
//        if (TextUtils.isEmpty(batchId)) throw new NullPointerException("批次Id不能为空");
//        return batchMap.get(batchId) != null;
//    }
//
//    public void downloadBatchTaskList(String batchId) {
//        if (TextUtils.isEmpty(batchId)) throw new NullPointerException("批次Id不能为空");
//        BatchTaskList<FilePoint> taskList = batchMap.get(batchId);
//        ConcurrentHashMap<String, FilePoint> taskListTs = taskList.getTs();
//        for (FilePoint f : taskListTs.values()) {
//            download(f.getFileId());
//        }
//    }
//}
