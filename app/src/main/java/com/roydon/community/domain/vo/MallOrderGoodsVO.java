package com.roydon.community.domain.vo;

import com.roydon.community.domain.entity.MallGoods;
import com.roydon.community.domain.entity.MallOrderGoods;

public class MallOrderGoodsVO extends MallOrderGoods {

    private MallGoods mallGoods;

    public MallOrderGoodsVO() {
    }

    public MallOrderGoodsVO(MallGoods mallGoods) {
        this.mallGoods = mallGoods;
    }

    public MallGoods getMallGoods() {
        return mallGoods;
    }

    public void setMallGoods(MallGoods mallGoods) {
        this.mallGoods = mallGoods;
    }
}