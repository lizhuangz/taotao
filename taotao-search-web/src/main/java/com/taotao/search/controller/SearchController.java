package com.taotao.search.controller;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 李壮壮 on 2018/9/3.
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;
    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
        queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
        SearchResult search = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
        model.addAttribute("query", queryString);
        model.addAttribute("itemList", search.getItemList());
        model.addAttribute("page", page);
        model.addAttribute("totalPages", search.getTotalPages());
        return "search";
    }
}
