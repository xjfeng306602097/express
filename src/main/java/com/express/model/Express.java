package com.express.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wshibiao on 2017/4/5.
 */
public class Express implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 快递单号
     */
    private String expressNo;
    /**
     * 寄件时间
     */
    private Date fromDate;
    /**
     * 到达时间
     */
    private Date arriveDate;
    /**
     * 取件时间
     */
    private Date receiveDate;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 寄件源地址
     */
    private String addressSource;
    /**
     * 到达地址
     */
    private String addressDest;
    /**
     * 收件人
     */
    private String consignee;
    /**
     * 快递公司
     */
    private String company;
    /**
     *验证码
     */
    private String verificationCode;
    /**
     * 取件状态
     */
    private String status;

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddressSource() {
        return addressSource;
    }

    public void setAddressSource(String addressSource) {
        this.addressSource = addressSource;
    }

    public String getAddressDest() {
        return addressDest;
    }

    public void setAddressDest(String addressDest) {
        this.addressDest = addressDest;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReciveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
