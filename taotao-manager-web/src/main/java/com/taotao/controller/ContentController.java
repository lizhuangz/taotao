package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 李壮壮 on 2018/8/31.
 */
@Controller
public class ContentController {

    @Autowired
    ContentService contentService;

    @RequestMapping("/content/save")
    @ResponseBody
    public TaotaoResult addContent(TbContent content){
        TaotaoResult taotaoResult = contentService.addContent(content);
        return taotaoResult;
    }

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult contentQueryList(long categoryId, int page, int rows){
        EasyUIDataGridResult result = contentService.contentQueryList(categoryId, page, rows);
        return result;
    }
}
