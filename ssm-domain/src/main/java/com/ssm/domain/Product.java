package com.ssm.domain;

import com.ssm.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

// 产品信息
public class Product {

    private Integer id; // 主键
    private String productNum; //编号，唯一
    private String productName; //名称
    private String cityName; //出发城市
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date departureTime; //出发时间
    private String departureTimeStr;
    private Double productPrice; //价格
    private String productDesc; //描述
    private Integer productStatus; //状态
    private String productStatusStr;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public String getProductNum() {
        return productNum;
    }

    public String getProductName() {
        return productName;
    }

    public String getCityName() {
        return cityName;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public String getDepartureTimeStr() {
        if(departureTime != null){
            departureTimeStr = DateUtils.date2String(departureTime,"yyyy-MM-dd HH:mm:ss");
        }
        return departureTimeStr;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public String getProductStatusStr() {
        if(productStatus != null){
            // 状态 0关闭 1开启
            if(productStatus ==0){
                productStatusStr="关闭";
            }
            if(productStatus ==1){
                productStatusStr="开启";
            }
        }
        return productStatusStr;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public void setDepartureTimeStr(String departureTimeStr) {
        this.departureTimeStr = departureTimeStr;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public void setProductStatusStr(String productStatusStr) {
        this.productStatusStr = productStatusStr;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productNum='" + productNum + '\'' +
                ", productName='" + productName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", departureTime=" + departureTime +
                ", departureTimeStr='" + departureTimeStr + '\'' +
                ", productPrice=" + productPrice +
                ", productDesc='" + productDesc + '\'' +
                ", productStatus=" + productStatus +
                ", productStatusStr='" + productStatusStr + '\'' +
                '}';
    }
}
