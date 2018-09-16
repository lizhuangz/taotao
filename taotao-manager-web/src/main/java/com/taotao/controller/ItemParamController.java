package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 李壮壮 on 2018/9/5.
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable long itemCatId){
        TaotaoResult taotaoResult = itemParamService.getItemParamByCid(itemCatId);
        return taotaoResult;
    }

    @RequestMapping("/save/{cid}")
    @ResponseBody

    public TaotaoResult addItemParam(@PathVariable long cid, String paramData){
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(cid);
        tbItemParam.setParamData(paramData);
        TaotaoResult taotaoResult = itemParamService.addItemParam(tbItemParam);
        return taotaoResult;
    }

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemParamList(Integer page, Integer rows){
        EasyUIDataGridResult easyUIDataGridResult = itemParamService.getItemParamList(page, rows);
        return easyUIDataGridResult;
    }
}