package com.taotao.cart.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * 购物车管理Controller
 */
@Controller
public class CartController {

    @Value("${CART_KEY}")
    private String CART_KEY;
    @Value("${CART_EXPIER}")
    private Integer CART_EXPIER;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/cart/add/{itemId}")
    public String addItemCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1")
            Integer num, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        //取购物车商品列表
        List<TbItem> catItemList = getCatItemList(httpServletRequest);
        boolean flag = false;
        for (TbItem tbItem : catItemList) {
            if (tbItem.getId() == itemId.longValue()) {
                tbItem.setNum(tbItem.getNum() + num);
                flag = true;
                break;
            }
        }
        if (!flag) {
            TbItem tbItem = itemService.getItemById(itemId);
            tbItem.setNum(num);
            String image = tbItem.getImage();
            if (StringUtils.isNoneBlank(image)) {
                String[] images = image.split(",");
                tbItem.setImage(images[0]);
            }
            catItemList.add(tbItem);
        }
        String json = JsonUtils.objectToJson(catItemList);
        CookieUtils.setCookie(httpServletRequest, httpServletResponse, CART_KEY, json, CART_EXPIER, true);
        return "cartSuccess";
    }

    private List<TbItem> getCatItemList(HttpServletRequest httpServletRequest) {
        String cart = CookieUtils.getCookieValue(httpServletRequest, CART_KEY, true);
        if (StringUtils.isBlank(cart)) {
            return new LinkedList<>();
        } else {
            List<TbItem> tbItems = JsonUtils.jsonToList(cart, TbItem.class);
            return tbItems;
        }
    }

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest httpServletRequest) {
        List<TbItem> catItemList = getCatItemList(httpServletRequest);
        httpServletRequest.setAttribute("cartList", catItemList);
        //返回逻辑视图
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateItemNum(@PathVariable Long itemId, @PathVariable Integer num
            , HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<TbItem> catItemList = getCatItemList(httpServletRequest);
        for (TbItem tbItem : catItemList) {
            if (tbItem.getId() == itemId.longValue()) {
                //更新商品数量
                tbItem.setNum(num);
                break;
            }
        }
        //把购车列表写入 cookie
        CookieUtils.setCookie(httpServletRequest, httpServletResponse, CART_KEY, JsonUtils.objectToJson(catItemList),
                CART_EXPIER, true);
        //返回成功
        return TaotaoResult.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest httpServletRequest ,
		HttpServletResponse httpServletResponse){
        List<TbItem> catItemList = getCatItemList(httpServletRequest);
        for (TbItem tbItem : catItemList){
            if (tbItem.getId() == itemId.longValue()){
                catItemList.remove(tbItem);
                break;
            }
        }
        CookieUtils.setCookie(httpServletRequest, httpServletResponse, CART_KEY, JsonUtils.objectToJson(catItemList),
                CART_EXPIER, true);
        return "redirect:/cart/cart.html";
    }

}
