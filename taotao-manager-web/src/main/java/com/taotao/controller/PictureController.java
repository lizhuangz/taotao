package com.taotao.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李壮壮 on 2018/8/27.
 */
@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String picUpload(MultipartFile uploadFile){
        Map resultMap = new HashMap();
        String result = new String();
        try{
            String origionFilename = uploadFile.getOriginalFilename();
            String extName = origionFilename.substring(origionFilename.lastIndexOf(".") + 1);
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
            String url = IMAGE_SERVER_URL + fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            resultMap.put("error", 0);
            resultMap.put("url", url);
            result = JsonUtils.objectToJson(resultMap);
        }catch (Exception e){
            resultMap.put("error", 1);
            resultMap.put("message", "图片上传失败");
            result = JsonUtils.objectToJson(resultMap);
        }finally {
            return result;
        }
    }
}
