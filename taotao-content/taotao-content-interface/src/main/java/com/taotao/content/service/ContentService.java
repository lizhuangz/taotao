package com.taotao.content.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

public interface ContentService {

	TaotaoResult addContent(TbContent content);
	EasyUIDataGridResult contentQueryList(long categoryId, int page, int rows);
    List<TbContent> getContentByCid(long categoryId);
}
