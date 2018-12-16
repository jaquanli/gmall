package com.atguigu.gmall.bean;
/**
 * 暂用不着
 */
public class WareSku {
    private Long id;

    private Long skuId;

    private Long warehouseId;

    private Integer stock;

    private String stockName;

    private Integer stockLocked;

    public WareSku(Long id, Long skuId, Long warehouseId, Integer stock, String stockName, Integer stockLocked) {
        this.id = id;
        this.skuId = skuId;
        this.warehouseId = warehouseId;
        this.stock = stock;
        this.stockName = stockName;
        this.stockLocked = stockLocked;
    }

    public WareSku() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName == null ? null : stockName.trim();
    }

    public Integer getStockLocked() {
        return stockLocked;
    }

    public void setStockLocked(Integer stockLocked) {
        this.stockLocked = stockLocked;
    }
}