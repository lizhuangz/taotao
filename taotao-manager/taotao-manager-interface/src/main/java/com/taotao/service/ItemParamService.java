package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * Created by 李壮壮 on 2018/9/5.
 */
public interface ItemParamService {
    TaotaoResult getItemParamByCid(long cid);
    TaotaoResult addItemParam(TbItemParam tbItemParam);
    EasyUIDataGridResult getItemParamList(int page, int rows);
}
