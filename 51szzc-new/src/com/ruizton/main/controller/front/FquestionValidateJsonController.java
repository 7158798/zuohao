package com.ruizton.main.controller.front;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fquestionvalidate;
import com.ruizton.main.model.Fuser;
import com.ruizton.util.DateHelper;
import com.ruizton.util.JsonHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.*;

/**
 * 安全问题验证
 * Created by luwei on 17-3-28.
 */
@Controller
public class FquestionValidateJsonController extends BaseController {

    //获取所有的安全问题列表  已移动至FrontUserController>security
   /* @RequestMapping(value = "/user/allquest", method = RequestMethod.POST, produces = {JsonEncode})
    @ResponseBody
    public String getAllQuestionValidate() {
        JSONObject jsonObject = new JSONObject();
        String question_str = this.constantMap.get("safequestion").toString();
        if (StringUtils.isBlank(question_str)) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("un_set_quest_desc"));  //未设置安全验证问题
            return jsonObject.toString();
        }

        String[] question_arr = question_str.split("#");
        if (question_arr == null || question_arr.length == 0) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("un_set_quest_desc"));
            return jsonObject.toString();
        }

        jsonObject.accumulate("question_arr", question_arr);
        jsonObject.accumulate(CODE, 0);

        return jsonObject.toString();
    }*/

    /**
     * 校验安全校验问题是否重复
     * @param list
     * @return
     */
   private boolean checkQuestionRepeat(List<Fquestionvalidate> list) {
        if(list == null || list.size() == 0) {
            return false;
        }
       Set<String> set = new TreeSet<>();
        for(Fquestionvalidate question : list) {
            set.add(question.getFquestion());
        }

        if(set.size() == list.size()) {
            return true;
        }else{
            return false;
        }
   }


    //保存安全问题验证
    @RequestMapping(value = "/user/savequest", method = RequestMethod.POST, produces = {JsonEncode})
    @ResponseBody
    public String saveQuestion(@RequestParam(required=true) String jsonstr){
        JSONObject jsonObject = new JSONObject();

        List<Fquestionvalidate> list = JsonHelper.jsonArrayStr2List(jsonstr, Fquestionvalidate.class);
        if(list == null || list.size() == 0) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("param_execption"));  //参数异常
            return jsonObject.toString();
        }

        Fuser fuser = GetSession(request);
        if(fuser == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("s_user_not_login"));  //用户未登录
            return jsonObject.toString();
        }

        boolean flag = checkQuestionRepeat(list);
        if(!flag) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, "安全验证问题不能重复选择");  //用户未登录
            return jsonObject.toString();
        }

        Timestamp tm = Timestamp.valueOf(DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));

        int index=1;
        for(Fquestionvalidate vo : list) {
            vo.setForder(index);
            vo.setFcreateTime(tm);
            vo.setFuser(fuser);
            fquestionvalidateService.save(vo);  //保存问题进数据库
            index++;
        }

        //设置用户的“是否安全验证问题”标识位为true
        Fuser user = this.userService.findById(fuser.getFid());
        user.setFsafebind(Boolean.TRUE);
        this.userService.updateObj(user);

        jsonObject.accumulate(MSG, i18nMsg("submit_success"));  //提交成功
        jsonObject.accumulate(CODE, 0);

        return jsonObject.toString();
    }


    //修改安全问题验证
    @RequestMapping(value = "/user/updatequest", method = RequestMethod.POST, produces = {JsonEncode})
    @ResponseBody
    public String updateQuestion(@RequestParam(required=true) String jsonstr) {
        JSONObject jsonObject = new JSONObject();

        List<Fquestionvalidate> list = JsonHelper.jsonArrayStr2List(jsonstr, Fquestionvalidate.class);
        if(list == null || list.size() == 0) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("param_execption"));  //参数异常
            return jsonObject.toString();
        }

        Fuser fuser = GetSession(request);
        if(fuser == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("s_user_not_login"));  //用户未登录
            return jsonObject.toString();
        }

        boolean check_flag = checkQuestionRepeat(list);
        if(!check_flag) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, "安全验证问题不能重复选择");  //用户未登录
            return jsonObject.toString();
        }

        //将用户删除的问题，从数据库记录中删除掉
        //根据用户id获取用户已设置的验证问题
        List<Fquestionvalidate> temp_list = this.fquestionvalidateService.findByProperty("fuser.fid", fuser.getFid());
        if(temp_list != null && temp_list.size() > 0) {
            for(Fquestionvalidate vo2 : temp_list) {
                boolean flag = true;
                for(Fquestionvalidate vo : list) {
                    if(vo.getFquestion().equalsIgnoreCase(vo2.getFquestion())) {
                        flag = true;
                        break;
                    }else{
                        flag = false;  //不存在
                    }
                }
                if(!flag) { //用户挑选了新的问题进行验证，旧的问题需要删除掉
                    this.fquestionvalidateService.delete(vo2);
                }
            }
        }

        Timestamp tm = Timestamp.valueOf(DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));


        int index=1;
        for(Fquestionvalidate vo : list) {
            vo.setForder(index);
            vo.setFcreateTime(tm);
            vo.setFuser(fuser);
            if(vo.getFid() != null) {
                fquestionvalidateService.update(vo);  //修改
            }else{
                fquestionvalidateService.save(vo);  //保存新问题进数据库
            }
            index++;
        }

        jsonObject.accumulate(MSG, i18nMsg("submit_success"));  //提交成功
        jsonObject.accumulate(CODE, 0);

        return jsonObject.toString();

    }


    //随机获取1个用户已设置的问题
    @RequestMapping(value = "/user/onequest", method = RequestMethod.POST, produces = {JsonEncode})
    @ResponseBody
    public String getOneQuestion() {
        JSONObject jsonObject = new JSONObject();

        Fuser fuser = GetSession(request);
        if(fuser == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("s_user_not_login"));  //用户未登录
            return jsonObject.toString();
        }

        //根据用户id获取用户已设置的验证问题
        List<Fquestionvalidate> list = this.fquestionvalidateService.findByProperty("fuser.fid", fuser.getFid());
        if (list == null || list.size() == 0) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("user_set_quest_desc"));  //安全验证问题未设置
            return jsonObject.toString();
        }

        //随机获取一个
        Random random = new Random();
        int max =  list.size();
        int min = 1;
        int s = random.nextInt(max)%(max-min+1) + min;
        s = s -1;
        String question = list.get(s).getFquestion();
        String answer = list.get(s).getFanswer();

        jsonObject.accumulate(CODE,  0);
        jsonObject.accumulate("fid", list.get(s).getFid());
        jsonObject.accumulate("question", question);
        jsonObject.accumulate("answer", answer);
        return jsonObject.toString();
    }

    //获取用户的已设置问题列表
    @RequestMapping(value = "/user/questlist", method = RequestMethod.POST, produces = {JsonEncode})
    @ResponseBody
    public String getQuestionList() {
        JSONObject jsonObject = new JSONObject();

        Fuser fuser = GetSession(request);
        if(fuser == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("s_user_not_login"));  //用户未登录
            return jsonObject.toString();
        }

        //根据用户id获取用户已设置的验证问题
        List<Fquestionvalidate> list = this.fquestionvalidateService.findByProperty("fuser.fid", fuser.getFid());
        if (list == null || list.size() == 0) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("user_set_quest_desc"));  //安全验证问题未设置
            return jsonObject.toString();
        }

        JSONArray jsonArray = new JSONArray();
        for(Fquestionvalidate vo : list) {
            JSONObject js1 = new JSONObject();
            js1.accumulate("fid", vo.getFid());
            js1.accumulate("fquestion", vo.getFquestion());
            js1.accumulate("fanswer", vo.getFanswer());
            js1.accumulate("forder", vo.getForder());
            jsonArray.add(js1);
        }

        jsonObject.accumulate(CODE,  0);
        jsonObject.accumulate("list", jsonArray);
        return jsonObject.toString();
    }

    /**
     * 校验问题答案
     * @param fid
     * @param user_answer
     * @return
     */
    @RequestMapping(value = "/user/checkQuestionVal", method = RequestMethod.POST, produces = {JsonEncode})
    @ResponseBody
    public String checkQuestionVal(@RequestParam(required=true) Integer fid,
                                   @RequestParam(required = true) String user_answer,
                                   @RequestParam(required = false) Integer userId) {
        JSONObject jsonObject = new JSONObject();

        if(fid == null || StringUtils.isBlank(user_answer)) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("param_execption"));
            return jsonObject.toString();
        }

        List<Fquestionvalidate> list =  null;
        if(userId == null) {  //是登录后，进行修改校验，此参数不必传     //找回密码校验则userId不为空
            Fuser fuser = GetSession(request);
            if(fuser == null) {
                jsonObject.accumulate(CODE, -1);
                jsonObject.accumulate(MSG, i18nMsg("s_user_not_login"));  //用户未登录
                return jsonObject.toString();
            }
            //取登录用户的id
            userId = fuser.getFid();
        }

        if(userId == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("param_execption"));  //参数异常
            return jsonObject.toString();
        }

        //根据用户id获取用户已设置的验证问题
        list = this.fquestionvalidateService.findByProperty("fuser.fid", userId);
        if (list == null || list.size() == 0) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("user_set_quest_desc"));  //安全验证问题未设置
            return jsonObject.toString();
        }


        boolean flag = false;
        for(Fquestionvalidate question : list) {
            if(question.getFid().intValue() !=  fid.intValue()) {
                continue;
            }

            if(question.getFanswer().trim().equals(user_answer.trim())) {
                flag = true;
                break;
            }
        }

        if(flag) {
            jsonObject.accumulate(CODE, 0);
        }else{
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, i18nMsg("answer_error"));
        }

        return jsonObject.toString();
    }

}
