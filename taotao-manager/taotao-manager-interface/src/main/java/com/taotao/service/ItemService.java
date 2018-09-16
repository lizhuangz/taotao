package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

import java.util.List;

public interface ItemService {

	TbItem getItemById(long itemId);
	EasyUIDataGridResult getItemList(int page, int rows);
	TaotaoResult addItem(TbItem tbItem, String desc, String itemParams);
	TbItemDesc getItemDescById(long itemId);
	TbItemParamItem getItemParamItemById(long itemId);
	TaotaoResult deleteItemByIds(List<Long> ids);
	TaotaoResult reshelfItemByIds(List<Long> ids);
	TaotaoResult instockItemByIds(List<Long> ids);
}
