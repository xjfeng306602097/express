package com.express.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wshibiao on 2017/4/6.
 */
public class ExpressShelf implements Serializable{
        private static final long serialVersionUID = 1L;
        private Long shelfId;
        private Express express;
        private Date createDate;
        private String shelfStatus;

        public Long getShelfId() {
                return shelfId;
        }

        public void setShelfId(Long shelfId) {
                this.shelfId = shelfId;
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

        public String getShelfStatus() {
                return shelfStatus;
        }

        public void setShelfStatus(String shelfStatus) {
                this.shelfStatus = shelfStatus;
        }
}
