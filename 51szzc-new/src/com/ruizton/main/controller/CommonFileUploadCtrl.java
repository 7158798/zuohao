package com.ruizton.main.controller;

import com.ruizton.main.Enum.FileUploadTypeEnum;
import com.ruizton.main.log.LOG;
import com.ruizton.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 公共文件上传
 * Created by luwei on 17-2-28.
 */
@Controller
public class CommonFileUploadCtrl {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyyMMddHHmmsss");


    /**
     * 公共文件上传
     * @param request
     * @param file
     * @param code
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value="/common/upload")
    public String uploadFile(HttpServletRequest request
            , @RequestParam MultipartFile file, String code) throws Exception {
        JSONObject js = new JSONObject();
        String resultUrl = "";  //绝对路径
        if (file != null && !file.isEmpty()) {
            InputStream inputStream = file.getInputStream();
            String fileRealName = file.getOriginalFilename();
            if (fileRealName != null && fileRealName.trim().length() > 0) {
                String[] nameSplit = fileRealName.split("\\.");
                String ext = nameSplit[nameSplit.length - 1];

                String realPath = request.getSession().getServletContext().getRealPath("/") + FileUploadTypeEnum.getObjectByCode(code).getFolderName();
                String time = simpleDateFormat.format(new Date());
                String fileName = Utils.getRandomImageName()+"."+ext;
                LOG.i(this, "common upload file name：" + fileName);
//                boolean flag = Utils.saveFile(realPath, fileName, inputStream);
                boolean flag = Utils.saveFile(fileName, inputStream);
                LOG.i(this, "common upload file result：" + flag);
                if (flag) {
                    resultUrl = OSSPostObject.URL + "/" + fileName;
                    js.accumulate("resultUrl", resultUrl);
                    js.accumulate("code", 0);
                }else{
                    js.accumulate("code", 1);
                }
            }
        }
        return js.toString();
    }


    @ResponseBody
    @RequestMapping(value="/common/sendmsg")
    public String sendMsg(HttpServletRequest request) throws  Exception{
        String msg  = "";

        //读取本地txt文件，获取手机号并存储到List
        List<String> phoneList = new ArrayList<>();
        String realPath = request.getSession().getServletContext().getRealPath("/") + "upload/手机号.txt";
        LOG.i(this, "文件路径：" + realPath);
        File file = new File(realPath);
        BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
        String s = null;
        while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            if(StringUtils.isNotBlank(s)) {
                phoneList.add(s);
            }
        }
        br.close();

        //没有手机号，就停止
        if(phoneList == null || phoneList.size() == 0) {
            msg = "-1";
            return msg;
        }

        LOG.i(this, "手机号个数："+phoneList.size());

        LOG.i(this, "本次发送的手机号有："+JsonHelper.obj2JsonStr(phoneList));

        Map<String, String> map = new HashMap<>();
        map.put("messageName","LTAIga54CEad0Tee");
        map.put("messagePassword","xDUQ5Ye6NFlkXOjemwBwi7Fk9ThqCS");
        map.put("messageURL","sms.aliyuncs.com");
        map.put("sms_template_code","SMS_49190190");
        map.put("webName","51数字资产");
        map.put("content", "注册就送价值10元红包，带你玩转数字货币！详请关注微信i51szzc或查看51szzc.com官网！");
//        map.put("phone", "18217010220");
        int retCode = -1;
        for(int i=0;i< phoneList.size();i++) {
            map.put("phone", phoneList.get(i));
            LOG.i(this, "短信发送开始，手机号："+map.get("phone") +"顺序号："+i);
            retCode = sendSMS.send(map.get("messageName"),
                    map.get("messagePassword"),
                    map.get("messageURL"),
                    map.get("sms_template_code"),
                    map.get("webName"),
                    map.get("content"),
                    map.get("phone"));
            if(retCode == -1) {
                LOG.i(this, "短信发送失败，手机号："+map.get("phone"));
                break;
            }
        }


        msg = retCode+"";
        return msg;
    }

}
