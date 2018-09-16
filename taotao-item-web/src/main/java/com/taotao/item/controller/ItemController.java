package com.taotao.item.controller;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 李壮壮 on 2018/9/5.
 */
@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    @RequestMapping("/index")
    @ResponseBody
    public String showIndex(String page){
        if (page == null){
            return "taotao";
        }
        return page;
    }

    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model){
        //取商品信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);
        TbItemParamItem itemParam = itemService.getItemParamItemById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        if (itemParam != null){
            model.addAttribute("itemParam", itemParam);
        }
        return "item";
    }
}
