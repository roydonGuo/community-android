package com.roydon.community.domain.vo;

import com.roydon.community.domain.entity.MallOrder;
import com.roydon.community.domain.entity.MallUserAddress;

import java.util.List;

public class MallOrderVO extends MallOrder {

    private MallUserAddress mallUserAddress;

    private List<MallOrderGoodsVO> mallOrderGoodsVOList;

    public MallOrderVO() {
    }

    public MallOrderVO(MallUserAddress mallUserAddress, List<MallOrderGoodsVO> mallOrderGoodsVOList) {
        this.mallUserAddress = mallUserAddress;
        this.mallOrderGoodsVOList = mallOrderGoodsVOList;
    }

    public MallUserAddress getMallUserAddress() {
        return mallUserAddress;
    }

    public void setMallUserAddress(MallUserAddress mallUserAddress) {
        this.mallUserAddress = mallUserAddress;
    }

    public List<MallOrderGoodsVO> getMallOrderGoodsVOList() {
        return mallOrderGoodsVOList;
    }

    public void setMallOrderGoodsVOList(List<MallOrderGoodsVO> mallOrderGoodsVOList) {
        this.mallOrderGoodsVOList = mallOrderGoodsVOList;
    }
}