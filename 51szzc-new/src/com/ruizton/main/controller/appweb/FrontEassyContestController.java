package com.ruizton.main.controller.appweb;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.EassyContestInfo;
import com.ruizton.main.service.front.FrontEassyContestService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by mibook3 on 17-4-12.
 */

@Controller
public class FrontEassyContestController extends BaseController {

@Autowired
private FrontEassyContestService frontEassyContestService;

    //保存征文信息

    @RequestMapping(value = "/eassycontest/save", produces = {JsonEncode})
    @ResponseBody
    public String saveEassy(String school, String name, String telephone, String department, String major, String grade, String photoUrl, String eassyUrl) {

        JSONObject jsonObject = new JSONObject();


        //判断手机是否存在，不存在则新增，如存在则更新

        if (StringUtils.isEmpty(telephone)) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", "电话号码不能为空");
            return jsonObject.toString();
        }

        //判断手机是否正确

        EassyContestInfo eassyContestInfo = frontEassyContestService.findByTelephone(telephone.trim());

        if (eassyContestInfo == null) {
            eassyContestInfo = new EassyContestInfo();
            eassyContestInfo.setSchool(school);
            eassyContestInfo.setName(name);
            eassyContestInfo.setTelephone(telephone);
            eassyContestInfo.setDepartment(department);
            eassyContestInfo.setMajor(major);
            eassyContestInfo.setGrade(grade);
            eassyContestInfo.setPhotoUrl(photoUrl);
            eassyContestInfo.setEassyUrl(eassyUrl);
            eassyContestInfo.setCreateDate(new Date());
            frontEassyContestService.saveOrUpdate(eassyContestInfo);

        } else {
            eassyContestInfo.setSchool(school);
            eassyContestInfo.setName(name);
            eassyContestInfo.setTelephone(telephone);
            eassyContestInfo.setDepartment(department);
            eassyContestInfo.setMajor(major);
            eassyContestInfo.setGrade(grade);
            eassyContestInfo.setPhotoUrl(photoUrl);
            eassyContestInfo.setEassyUrl(eassyUrl);
            eassyContestInfo.setUpdateDate(new Date());
            frontEassyContestService.saveOrUpdate(eassyContestInfo);

        }
        jsonObject.accumulate("code", 0);
        jsonObject.accumulate("msg", "征文信息保存成功");
        return jsonObject.toString();
    }

}
