package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by 李壮壮 on 2018/8/26.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows){
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItem(TbItem item, String desc, String itemParams){
        TaotaoResult taotaoResult = itemService.addItem(item ,desc, itemParams);
        return taotaoResult;
    }

    @RequestMapping(value = "/item/delete")
    @ResponseBody
    public TaotaoResult deleteItem(String ids){
        String[] idStringList = ids.split(",");
        LinkedList<Long> idList = new LinkedList<>();
        for (String id : idStringList) {
            idList.add(Long.parseLong(id));
        }
        TaotaoResult taotaoResult = itemService.deleteItemByIds(idList);
        return taotaoResult;
    }

    @RequestMapping(value = "/item/reshelf")
    @ResponseBody
    public TaotaoResult reshelfItem(String ids){
        String[] idStringList = ids.split(",");
        LinkedList<Long> idList = new LinkedList<>();
        for (String id : idStringList) {
            idList.add(Long.parseLong(id));
        }
        TaotaoResult taotaoResult = itemService.reshelfItemByIds(idList);
        return taotaoResult;
    }

    @RequestMapping(value = "/item/instock")
    @ResponseBody
    public TaotaoResult instockItem(String ids){
        String[] idStringList = ids.split(",");
        LinkedList<Long> idList = new LinkedList<>();
        for (String id : idStringList) {
            idList.add(Long.parseLong(id));
        }
        TaotaoResult taotaoResult = itemService.instockItemByIds(idList);
        return taotaoResult;
    }
}
