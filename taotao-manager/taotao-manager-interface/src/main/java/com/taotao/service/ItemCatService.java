package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by 李壮壮 on 2018/8/27.
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long parentId);
}
