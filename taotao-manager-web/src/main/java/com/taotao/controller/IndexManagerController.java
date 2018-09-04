package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 李壮壮 on 2018/9/2.
 */
@Controller
public class IndexManagerController {

    @Autowired
    SearchItemService searchItemService;
    @RequestMapping("/index/import")
    @ResponseBody
    public TaotaoResult importIndex(){
        TaotaoResult taotaoResult = searchItemService.importItemsToIndex();
        return taotaoResult;
    }
}
