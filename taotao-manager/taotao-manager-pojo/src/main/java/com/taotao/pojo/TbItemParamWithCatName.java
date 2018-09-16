package com.taotao.pojo;

import java.io.Serializable;

/**
 * Created by 李壮壮 on 2018/9/5.
 */
public class TbItemParamWithCatName extends TbItemParam {
    private String itemCatName;

    public String getItemCatName() {
        return itemCatName;
    }

    public TbItemParamWithCatName(String itemCatName) {
        this.itemCatName = itemCatName;
    }

    public void setTbItemParam(TbItemParam itemParam) {
        this.setUpdated(itemParam.getUpdated());
        this.setCreated(itemParam.getCreated());
        this.setId(itemParam.getId());
        this.setItemCatId(itemParam.getItemCatId());
        this.setParamData(itemParam.getParamData());
    }

    public void setItemCatName(String itemCatName) {
        this.itemCatName = itemCatName;
    }
}
