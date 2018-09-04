package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;

/**
 * Created by 李壮壮 on 2018/9/3.
 */
public interface SearchService {
    SearchResult search(String queryString, int page, int rows) throws Exception;
}
