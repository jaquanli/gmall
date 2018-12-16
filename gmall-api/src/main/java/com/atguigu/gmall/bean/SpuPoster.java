package com.atguigu.gmall.bean;
/**
 * 暂用不着
 */
public class SpuPoster {
    private Long id;

    private Long spuId;

    private String fileName;

    private String fileType;

    public SpuPoster(Long id, Long spuId, String fileName, String fileType) {
        this.id = id;
        this.spuId = spuId;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public SpuPoster() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }
}