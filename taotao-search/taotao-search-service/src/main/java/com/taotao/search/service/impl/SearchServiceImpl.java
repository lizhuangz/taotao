package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 李壮壮 on 2018/9/3.
 */
@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    SearchDao searchDao;

    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(queryString);
        if (page < 1) page = 1;
        if (rows < 1) rows = 10;
        solrQuery.setStart((page - 1) * rows);
        solrQuery.setRows(rows);
        solrQuery.set("df","item_title");
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font");
        SearchResult searchResult = searchDao.search(solrQuery);
        long recordCount = searchResult.getRecordCount();
        long pages = recordCount / rows;
        if (recordCount % rows != 0){
            page += 1;
        }
        searchResult.setTotalPages(pages);
        return searchResult;
    }
}
