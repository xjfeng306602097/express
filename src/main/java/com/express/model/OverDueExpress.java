package com.express.model;

import java.util.Date;

public class OverDueExpress {
	
	private int overDueShelfId;

	private Express express;

	private Date createDate;

	private String status;

	public int getOverDueShelfId() {
		return overDueShelfId;
	}

	public void setOverDueShelfId(int overDueShelfId) {
		this.overDueShelfId = overDueShelfId;
	}

    public Express getExpress() {
        return express;
    }

    public void setExpress(Express express) {
        this.express = express;
    }

    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
