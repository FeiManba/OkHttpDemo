package com.huifeideyema.net.download;

import static com.huifeideyema.net.download.Consts.NONE;

public class DownloadData {
    /**
     * 批次Id
     */
    private String batchId;
    /**
     * 任务Id
     */
    private String id;
    /**
     * 资源下载路径
     */
    private String url;
    /**
     * 本地保存路径
     */
    private String path;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务类型
     */
    private int type;
    /**
     * 当前长度
     */
    private int currentLength;
    /**
     * 总长度
     */
    private int totalLength;
    /**
     * 下载百分比
     */
    private float percentage;
    /**
     * 下载状态
     */
    private int status = NONE;
    /**
     * 下载所需线程个数
     */
    private int childTaskCount;
    /**
     * 添加任务的时间戳
     */
    private long date;

    public String getBatchId() {
        return batchId;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getCurrentLength() {
        return currentLength;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public float getPercentage() {
        return percentage;
    }

    public int getStatus() {
        return status;
    }

    public int getChildTaskCount() {
        return childTaskCount;
    }

    public long getDate() {
        return date;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setChildTaskCount(int childTaskCount) {
        this.childTaskCount = childTaskCount;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public static final class Builder {
        /**
         * 批次Id
         */
        private String batchId;
        /**
         * 任务Id
         */
        private String id;
        /**
         * 资源下载路径
         */
        private String url;
        /**
         * 本地保存路径
         */
        private String path;
        /**
         * 任务名称
         */
        private String name;
        /**
         * 任务类型
         */
        private int type;
        /**
         * 当前长度
         */
        private int currentLength;
        /**
         * 总长度
         */
        private int totalLength;
        /**
         * 下载百分比
         */
        private float percentage;
        /**
         * 下载状态
         */
        private int status = NONE;
        /**
         * 下载所需线程个数
         */
        private int childTaskCount;
        /**
         * 添加任务的时间戳
         */
        private long date;


        public Builder setBatchId(String batchId) {
            this.batchId = batchId;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setCurrentLength(int currentLength) {
            this.currentLength = currentLength;
            return this;
        }

        public Builder setTotalLength(int totalLength) {
            this.totalLength = totalLength;
            return this;
        }

        public Builder setPercentage(float percentage) {
            this.percentage = percentage;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder setChildTaskCount(int childTaskCount) {
            this.childTaskCount = childTaskCount;
            return this;
        }

        public Builder setDate(long date) {
            this.date = date;
            return this;
        }

        public DownloadData build() {
            DownloadData downloadData = new DownloadData();
            downloadData.setBatchId(this.batchId);
            downloadData.setId(this.id);
            downloadData.setUrl(this.url);
            downloadData.setPath(this.url);
            downloadData.setName(this.name);
            downloadData.setType(this.type);
            downloadData.setCurrentLength(this.currentLength);
            downloadData.setTotalLength(this.totalLength);
            downloadData.setPercentage(this.percentage);
            downloadData.setStatus(this.status);
            downloadData.setChildTaskCount(this.childTaskCount);
            downloadData.setDate(this.date);
            return downloadData;
        }
    }
}
