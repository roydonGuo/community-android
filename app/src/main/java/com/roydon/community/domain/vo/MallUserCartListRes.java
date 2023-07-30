package com.roydon.community.domain.vo;

import com.roydon.community.domain.entity.MallUserCartVO;
import com.roydon.community.domain.response.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @author roydon
 * @date 2023/6/25 3:36
 * @description community-android
 */
public class MallUserCartListRes extends BaseResponse implements Serializable {

    private int total;
    private List<MallUserCartVO> data;

    public MallUserCartListRes() {
    }

    public MallUserCartListRes(int total, List<MallUserCartVO> data) {
        this.total = total;
        this.data = data;
    }

    public MallUserCartListRes(String msg, int code, int total, List<MallUserCartVO> data) {
        super(msg, code);
        this.total = total;
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MallUserCartVO> getData() {
        return data;
    }

    public void setData(List<MallUserCartVO> data) {
        this.data = data;
    }
}
