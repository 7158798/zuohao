package com.ruizton.main.controller.front;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.ruizton.main.Enum.QuestionStatusEnum;
import com.ruizton.main.Enum.QuestionTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fquestion;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.front.FrontQuestionService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Utils;
import sun.nio.cs.ext.IBM1097;

@Controller
public class FrontQuestionJsonController extends BaseController{
	

	/*
	 * var param={questionType:questionType,desc:desc,name:name,phone:phone};
	 * */
	@ResponseBody
	@RequestMapping("/question/submitQuestion")
	public String submitQuestion(
			HttpServletRequest request,
			@RequestParam(required=true)int questiontype,
			@RequestParam(required=true)String questiondesc
			)throws Exception{
		JSONObject js = new JSONObject();
		String type = QuestionTypeEnum.getEnumString(questiontype);
		if(type == null || type.trim().length() ==0){
			js.accumulate("code", -1);
//			js.accumulate("msg", "非法提交");
			js.accumulate(MSG, i18nMsg("illegal_submit"));
			return js.toString();
		}
		
		questiondesc = HtmlUtils.htmlEscape(questiondesc) ;
        if(questiondesc.length() >500){
        	js.accumulate("code", -1);
//			js.accumulate("msg", "提交内容太长");
			js.accumulate(MSG, i18nMsg("submit_content_too_long"));
			return js.toString();
        }
        
        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
        if(!fuser.isFisTelephoneBind()){
        	js.accumulate("code", -1);
//			js.accumulate("msg", "请先绑定手机");
			js.accumulate(MSG, i18nMsg("please_bind_phone"));
			return js.toString();
        }
        if(fuser.getFtradePassword() == null ||fuser.getFtradePassword().trim().length() ==0){
        	js.accumulate("code", -1);
//			js.accumulate("msg", "请先设置交易密码");
			js.accumulate(MSG, i18nMsg("please_set_trade_pwd"));
			return js.toString();
        }
        if(!fuser.getFpostRealValidate()){
        	js.accumulate("code", -1);
//			js.accumulate("msg", "请先进行实名认证");
			js.accumulate(MSG, i18nMsg("please_do_real_name"));
			return js.toString();
        }
		
		Fquestion fquestion = new Fquestion() ;
		fquestion.setFuser(GetSession(request)) ;
		fquestion.setFcreateTime(Utils.getTimestamp()) ;
		fquestion.setFdesc(questiondesc) ;
		fquestion.setFstatus(QuestionStatusEnum.NOT_SOLVED) ;
		fquestion.setFtype(questiontype) ;

		this.frontQuestionService.save(fquestion) ;
		js.accumulate("code",0);
//		js.accumulate("msg", "问题提交成功，请耐心等待管理员回复");
		js.accumulate(MSG, i18nMsg("question_submit_desc"));
		return js.toString();
	}
	
	/* * /question/cancelQuestion.html?qid="+id+"&currentPage type=
	 * */
	@ResponseBody
	@RequestMapping("/question/delquestion")
	public String delquestion(
			HttpServletRequest request,
			@RequestParam(required=true)int fid
			) throws Exception{
		JSONObject js = new JSONObject();
		try {
			Fquestion fquestion = this.frontQuestionService.findById(fid) ;
			if(fquestion!=null && fquestion.getFuser().getFid()==GetSession(request).getFid()){
				this.frontQuestionService.delete(fquestion) ;
			}else{
				js.accumulate("code", -1);
//				js.accumulate("msg", "非法操作");
				js.accumulate(MSG, i18nMsg("s_illegal_operation"));
				return js.toString();
			}
		} catch (Exception e) {
			js.accumulate("code", -1);
//			js.accumulate("msg", "网络异常");
			js.accumulate(MSG, i18nMsg("s_network_anomaly"));
			return js.toString();
		}
		
		js.accumulate("code",0);
//		js.accumulate("msg", "删除成功");
		js.accumulate(MSG, i18nMsg("delete_success"));
		return js.toString();
	}
}
