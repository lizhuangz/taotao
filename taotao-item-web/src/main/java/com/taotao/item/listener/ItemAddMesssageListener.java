package com.taotao.item.listener;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李壮壮 on 2018/9/6.
 */
public class ItemAddMesssageListener implements MessageListener{

    @Autowired
    ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage)message;
        try {
            String strId = textMessage.getText();
            long id = Long.parseLong(strId);
            TbItem tbItem = itemService.getItemById(id);
            TbItemDesc tbItemDesc = itemService.getItemDescById(id);
            TbItemParamItem tbItemParamItem = itemService.getItemParamItemById(id);
            //生成静态页面
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Map data = new HashMap();
            data.put("item", new Item(tbItem));
            data.put("itemDesc", tbItemDesc);
            data.put("itemParam", tbItemParamItem);

            Writer out = new FileWriter(new File(HTML_OUT_PATH + strId + ".html"));
            template.process(data, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
