package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 李壮壮 on 2018/9/5.
 */
@Service
public class ItemParamServiceImpl implements ItemParamService{

    @Autowired
    TbItemParamMapper tbItemParamMapper;
    @Autowired
    TbItemCatMapper itemCatMapper;
    @Override
    public TaotaoResult getItemParamByCid(long cid) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = tbItemParamExample.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        if (tbItemParams != null && tbItemParams.size() > 0) {
            return TaotaoResult.ok(tbItemParams.get(0));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult addItemParam(TbItemParam tbItemParam) {
        tbItemParam.setCreated(new Date());
        tbItemParam.setUpdated(new Date());
        tbItemParamMapper.insert(tbItemParam);
        return TaotaoResult.ok();
    }

    @Override
    public EasyUIDataGridResult getItemParamList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        //查询规格对应的名称并封装
        List<TbItemParamWithCatName> tbItemParamWithCatNames = new ArrayList<>(list.size());
        for (TbItemParam tbItemParam : list) {
            String name = getItemCatName(tbItemParam.getItemCatId());
            TbItemParamWithCatName tbItemParamWithCatName = new TbItemParamWithCatName(name);
            tbItemParamWithCatName.setTbItemParam(tbItemParam);
            tbItemParamWithCatNames.add(tbItemParamWithCatName);
        }
        //取查询结果
        PageInfo<TbItemParamWithCatName> pageInfo = new PageInfo<>(tbItemParamWithCatNames);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(tbItemParamWithCatNames);
        return result;
    }

    private String getItemCatName(long itemCatId) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemCatId);

        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0).getName();
        }
        return "";
    }

}
