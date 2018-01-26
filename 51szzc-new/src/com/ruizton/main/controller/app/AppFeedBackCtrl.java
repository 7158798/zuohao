package com.ruizton.main.controller.app;

import com.ruizton.main.Enum.QuestionStatusEnum;
import com.ruizton.main.Enum.QuestionTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.app.request.FeedbackReq;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fquestion;
import com.ruizton.main.model.Fuser;
import com.ruizton.util.Constant;
import com.ruizton.util.IdAndName;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 问题反馈模块  app接口
 * Created by zygong on 17-3-27.
 */
@Controller
public class AppFeedBackCtrl extends BaseController implements ApiConstants {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    int maxResult = Constant.AppRecordPerPage;

    /**
     * 保存问题反馈
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_FEEDBACK_ADD, method = RequestMethod.POST, produces = JsonEncode)
    public String saveFeedback(@RequestBody FeedbackReq req) {
        JSONObject jsonObject = new JSONObject();
        try {
            //验证是否登录
            String loginToken = req.getLoginToken();
            String description = req.getDescription();
            Integer type = req.getType();
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }
            if (StringUtils.isEmpty(description) || null == type) {
                return ApiConstant.getRequestError("请求参数不能为空");
            }
            if (StringUtils.isEmpty(description)) {
                return ApiConstant.getRequestError("内容不能为空");
            }
            if (null == type && type.intValue() == 0) {
                return ApiConstant.getRequestError("请选择类型");
            }

            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }else if(StringUtils.isBlank(user.getFtelephone())){
                return ApiConstant.getRequestError("请绑定手机号");
            }
            // 判断用户时候绑定手机号
            if(null == user.getFtelephone() || StringUtils.isBlank(user.getFtelephone())){
                return ApiConstant.getRequestError("请先绑定手机号");
            }
            String dateString = this.sdf.format(new java.util.Date());
            Timestamp tm = Timestamp.valueOf(dateString);
            Fquestion question = new Fquestion();
            question.setFuser(user);
            question.setFtype(type);
            question.setFtelephone(user.getFtelephone());
            question.setFdesc(description);
            question.setFcreateTime(tm);
            question.setFstatus(QuestionStatusEnum.NOT_SOLVED);
            this.questionService.saveObj(question);
        } catch (Exception e) {
            LOG.e(this, "保存问题反馈失败", e);
            return ApiConstant.getRequestError("操作失败");
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }

    /**
     * 获取问题反馈类型
     * @param loginToken
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_FEEDBACK_TITLE, method = RequestMethod.GET, produces = JsonEncode)
    public String getTitle(String loginToken){
        JSONObject jsonObject = new JSONObject();

        try{
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }
            List<IdAndName> list = new ArrayList<IdAndName>();
            IdAndName idAndName = null;
            for(int i = 1; i<= QuestionTypeEnum.OTHERS; i++){
                idAndName = new IdAndName();
                idAndName.setId(i);
                idAndName.setName(QuestionTypeEnum.getEnumString(i));
                list.add(idAndName);
            }

            jsonObject.accumulate(LIST, list);
        }catch (Exception e){
            LOG.e(this, "获取问题反馈列表失败", e);
            return ApiConstant.getRequestError("获取失败");
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }

    /**
     * 获取问题反馈列表
     * @param loginToken
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_FEEDBACK_LIST, method = RequestMethod.GET, produces = JsonEncode)
    public String getQuestionList(String loginToken, Integer currentPage){
        JSONObject jsonObject = new JSONObject();
        try{
            if (!this.realTimeData.isAppLogin(loginToken, true)) {
                return ApiConstant.getNoLoginError();
            }
            Fuser user = this.realTimeData.getAppFuser(loginToken);
            if (user == null) {
                return ApiConstant.getRequestError("用户不存在");
            }
            ObjectMapper mapper = new ObjectMapper();

            StringBuffer filter = new StringBuffer();
            filter.append("where fuser.fid = " + user.getFid());
            filter.append(" order by fstatus asc, fcreateTime desc ");
            List<Fquestion> list = this.questionService.list((currentPage-1)*maxResult, maxResult, filter+"",true);
            jsonObject.accumulate(LIST, mapper.writeValueAsString(list));

            // 总页数
            int allCount = this.adminService.getAllCount("Fquestion", filter + "");
            if(allCount != 0){
                int i = allCount / maxResult;
                i++;
                if(allCount%maxResult != 0 && allCount > maxResult){
                    i++;
                }
                jsonObject.accumulate(TOTALPAGE, i);
            }
        }catch (Exception e){
            LOG.e(this, "获取问题反馈列表失败", e);
            return ApiConstant.getRequestError("获取失败");
        }
        return ApiConstant.getSuccessResp(jsonObject);
    }


}