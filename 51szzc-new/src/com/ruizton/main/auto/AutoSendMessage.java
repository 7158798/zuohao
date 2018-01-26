package com.ruizton.main.auto;

import java.util.TimerTask;

import com.google.gson.Gson;
import com.ruizton.main.log.LOG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.Enum.ValidateMessageStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.model.Fvalidatemessage;
import com.ruizton.main.service.front.FrontSystemArgsService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.util.ConstantKeys;
import com.ruizton.util.MessagesUtils;
import com.ruizton.util.Utils;

public class AutoSendMessage extends TimerTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(AutoSendMessage.class);

	private static final String CLASS_NAME = AutoSendMessage.class.getSimpleName();


	@Autowired
	private TaskList taskList ;
	@Autowired
	private ConstantMap map;
	@Autowired
	private FrontValidateService frontValidateService ;
	
	
	@Override
	public void run() {
		Integer id = this.taskList.getOneMessage() ;
		if (id != null) {
			Fvalidatemessage fvalidatemessage = this.frontValidateService.findFvalidateMessageById(id) ;
			if(fvalidatemessage==null){
				LOGGER.info(CLASS_NAME + " run，短信发送,从数据库查询短信发送内容为空,流水id:{}", id);
				return ;
			}
			
			int retCode;
			try {
				retCode = MessagesUtils.send(
						map.getString("messageName"),
						map.getString("messagePassword"),
						map.getString("messageURL"),
						map.getString("sms_template_code"),
						map.getString("webName"),
						fvalidatemessage.getFcontent(),
						fvalidatemessage.getFphone());
				if(retCode == 1){
					fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Send) ;
					LOG.i(this,"验证码发送成功:"+retCode);
				}else{
					fvalidatemessage.setFstatus(ValidateMessageStatusEnum.ERROR_send) ;
					LOG.i(this,"验证码发送失败:"+retCode);
				}
				fvalidatemessage.setFsendTime(Utils.getTimestamp()) ;
				this.frontValidateService.updateFvalidateMessage(fvalidatemessage) ;
			} catch (Exception e) {
				LOGGER.info(CLASS_NAME + " run，验证码发送异常，发送手机号:{}", fvalidatemessage.getFphone()+"，验证码:{}", fvalidatemessage.getFcontent());
				LOGGER.info(CLASS_NAME + " run，异常exception:{}", e);
				taskList.returnMessageList(id) ;
				e.printStackTrace();
			}
		}
		
	}
}
