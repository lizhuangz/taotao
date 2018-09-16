package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * 商品管理Service
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource(name = "itemAddTopic")
    private Destination destination;
    @Autowired
    private JedisClient jedisClient;
    @Value("${ITEM_INFO}")
    private String ITEM_INFO;
    @Value("${ITEM_EXPIRE}")
    private int ITEM_EXPIRE;
	@Override
	public TbItem getItemById(long itemId) {

        String redisItemKey = ITEM_INFO + ":" + itemId + ":BASE";
        //查询缓存
	    try{
            String s = jedisClient.get(redisItemKey);
            if (s != null && !"".equals(s.trim())){
                TbItem tbItem = JsonUtils.jsonToPojo(s, TbItem.class);
                return tbItem;
            }
        }catch (Exception e){
	        e.printStackTrace();
        }
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
		//添加缓存
		try{
            jedisClient.set(redisItemKey, JsonUtils.objectToJson(item));
            jedisClient.expire(redisItemKey, ITEM_EXPIRE);
        }catch (Exception e){
		    e.printStackTrace();
        }
		return item;
	}

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
	    //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample tbItemExample = new TbItemExample();
        tbItemExample.setOrderByClause("updated desc");
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andStatusNotEqualTo((byte) 3);
        List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);
        //取查询结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public TaotaoResult addItem(TbItem item, String desc, String itemParams) {
        //生成商品id
        final long itemId = IDUtils.genItemId();
        //补全item的属性
        item.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        tbItemMapper.insert(item);
        //创建一个商品描述表对应的pojo
        TbItemDesc itemDesc = new TbItemDesc();
        //补全pojo的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(new Date());
        itemDesc.setCreated(new Date());
        //向商品描述表插入数据
        tbItemDescMapper.insert(itemDesc);
        //创建一个商品规格表对应的pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        //补全pojo的属性
        itemParamItem.setParamData(itemParams);
        itemParamItem.setItemId(itemId);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        //向商品规格表插入数据
        tbItemParamItemMapper.insert(itemParamItem);
        //向Activemq发送商品添加消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                //发送商品id
                TextMessage textMessage = session.createTextMessage(itemId + "");
                return textMessage;
            }
        });
        //返回结果
        return TaotaoResult.ok();
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {
        String redisDescKey = ITEM_INFO + ":" + itemId + ":DESC";
        //查询缓存
        try{
            String s = jedisClient.get(redisDescKey);
            if (s != null && !"".equals(s.trim())){
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(s, TbItemDesc.class);
                return tbItemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        //添加缓存
        try{
            jedisClient.set(redisDescKey, JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire(redisDescKey, ITEM_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tbItemDesc;
    }

    @Override
    public TbItemParamItem getItemParamItemById(long itemId) {
        String redisParamKey = ITEM_INFO + ":" + itemId + ":PARAM";
        //查询缓存
        try{
            String s = jedisClient.get(redisParamKey);
            if (s != null && !"".equals(s.trim())){
                TbItemParamItem tbItemParamItem = JsonUtils.jsonToPojo(s, TbItemParamItem.class);
                return tbItemParamItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
        if (tbItemParamItems != null && tbItemParamItems.size() > 0){
            TbItemParamItem tbItemParamItem = tbItemParamItems.get(0);
            //添加缓存
            try{
                jedisClient.set(redisParamKey, JsonUtils.objectToJson(tbItemParamItem));
                jedisClient.expire(redisParamKey, ITEM_EXPIRE);
            }catch (Exception e){
                e.printStackTrace();
            }
            return tbItemParamItem;
        }
        return null;
    }

    @Override
    public TaotaoResult deleteItemByIds(List<Long> ids) {
	    for (Long id : ids){
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
            tbItem.setStatus((byte) 3);
            tbItem.setUpdated(new Date());
            tbItemMapper.updateByPrimaryKeySelective(tbItem);
        }
        return TaotaoResult.ok();
    }

    //上架商品
    @Override
    public TaotaoResult reshelfItemByIds(List<Long> ids) {
        for (Long id : ids){
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
            tbItem.setStatus((byte) 1);
            tbItem.setUpdated(new Date());
            tbItemMapper.updateByPrimaryKeySelective(tbItem);
        }
        return TaotaoResult.ok();
    }
    //下架商品
    @Override
    public TaotaoResult instockItemByIds(List<Long> ids) {
        for (Long id : ids){
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
            tbItem.setStatus((byte) 2);
            tbItem.setUpdated(new Date());
            tbItemMapper.updateByPrimaryKeySelective(tbItem);
        }
        return TaotaoResult.ok();
    }

}
