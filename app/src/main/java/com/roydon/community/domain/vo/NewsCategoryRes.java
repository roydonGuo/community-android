package com.roydon.community.domain.vo;

import java.io.Serializable;
import java.util.List;

public class NewsCategoryRes implements Serializable {
    /**
     * {
     * "msg": "操作成功",
     * "code": 200,
     * "data": [
     * {
     * "dictCode": 42,
     * "dictLabel": "热点"
     * },
     * {
     * "dictCode": 32,
     * "dictLabel": "财经"
     * }
     * ]
     * }
     */
    private String msg;
    private int code;
    private List<DataBean> data;

    public static class DataBean {
        private Long dictCode;
        private String dictValue;
        private String dictLabel;

        public DataBean() {
        }

        public DataBean(Long dictCode, String dictValue, String dictLabel) {
            this.dictCode = dictCode;
            this.dictValue = dictValue;
            this.dictLabel = dictLabel;
        }

        public Long getDictCode() {
            return dictCode;
        }

        public void setDictCode(Long dictCode) {
            this.dictCode = dictCode;
        }

        public String getDictValue() {
            return dictValue;
        }

        public void setDictValue(String dictValue) {
            this.dictValue = dictValue;
        }

        public String getDictLabel() {
            return dictLabel;
        }

        public void setDictLabel(String dictLabel) {
            this.dictLabel = dictLabel;
        }
    }

    public NewsCategoryRes() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public NewsCategoryRes(String msg, int code, List<DataBean> data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
}
