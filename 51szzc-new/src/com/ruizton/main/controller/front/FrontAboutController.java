package com.ruizton.main.controller.front;

import com.alibaba.fastjson.JSON;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fhelp;
import com.ruizton.main.model.Fhelptype;
import com.ruizton.main.model.Fvideotype;
import com.ruizton.main.model.vo.FvideoVo;
import com.ruizton.main.model.vo.HotHelpVo;
import com.ruizton.util.ExParamExtendsKeyValue;
import com.ruizton.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FrontAboutController extends BaseController {


	/**
	 * 帮助中心，接口
	 * @return
	 * @throws Exception
	 *
	 * 2017-03-02 update：增加参数type，2表示查询视频课程
	 */
	@RequestMapping("/about/help")
	public ModelAndView help() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		ExParamExtendsKeyValue kav = null;
		List<ExParamExtendsKeyValue> kavList = null;
		Map<String, List<ExParamExtendsKeyValue>> map = null;
		List<Map<String, List<ExParamExtendsKeyValue>>> hotHelpList = null;
		List<Object> objList = null;

		// 获取推荐帮助类型列表
		List<HotHelpVo> hotHelpVos = this.fhelpService.hotHelp();
		if(null != hotHelpVos && hotHelpVos.size() > 0){
			String temp = "";
			int item = 1;
			kavList = new ArrayList<ExParamExtendsKeyValue>();
			map = new HashMap<String, List<ExParamExtendsKeyValue>>();
			hotHelpList = new ArrayList<Map<String, List<ExParamExtendsKeyValue>>>();
			objList = new ArrayList<Object>();
			int size = hotHelpVos.size();

			for(HotHelpVo vo : hotHelpVos){
				kav = new ExParamExtendsKeyValue();
				if(item == 1){
					map.put(vo.getFtype(), null);
				}

				kav.setKey(vo.getFid());
				kav.setValue(vo.getFtitle());
				kav.setType(vo.getFtypeid());
				// 如果map中包含title新建map,新建kavList
				if(!map.containsKey(vo.getFtype())){
					map.put(temp, kavList);
					hotHelpList.add(map);
					kavList = new ArrayList<ExParamExtendsKeyValue>();
					map = new HashMap<String, List<ExParamExtendsKeyValue>>();
					map.put(vo.getFtype(), null);
				}
				kavList.add(kav);
				if(item == size){
					map.put(vo.getFtype(), kavList);
					if(hotHelpList.size() == 6){
						objList.add(hotHelpList);
						hotHelpList = new ArrayList<Map<String, List<ExParamExtendsKeyValue>>>();
					}
					hotHelpList.add(map);
				}

				// 每一页是6个，所以hotHelpList.size 等于6就把hotHelpList放到objList里面
				if(hotHelpList.size() == 6 || item == size){
					objList.add(hotHelpList);
					hotHelpList = new ArrayList<Map<String, List<ExParamExtendsKeyValue>>>();
				}
				item++;
				temp = vo.getFtype();
			}
		}
		modelAndView.addObject("objList", objList);

		//查询视频课程
		List<Fvideotype> fvideotypeList = this.fvideoService.findAllVideoType();
		modelAndView.addObject("fvideotypeList", fvideotypeList);

		modelAndView.setViewName("front/about/help") ;
		return modelAndView ;
	}
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();

	/**
	 * produces = "text/html;charset=UTF-8" 防止中文乱码
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value = "about/videoIndex", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String videoIndex(@RequestParam(required = false, defaultValue = "1") int typeId){
		List<String> strList = new ArrayList<String>();
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 \n");
		filter.append(" and fTypeId = "+typeId+" \n");
		filter.append(" order by fPriority \n");
		filter.append(" asc \n");
		List<FvideoVo> videoList = this.fvideoService.listAllSql(filter.toString());

		return JSON.toJSONString(videoList);
	}

	/**
	 * 帮助中心，接口
	 * @param id
	 * @param type  查询类型
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/about/index")
	public ModelAndView helpIndex(
			@RequestParam(required=false,defaultValue="0")int id,
			@RequestParam(required = false, defaultValue = "1") int type
	) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fhelp fhelp = null;
		List<Fhelp> helpList = new ArrayList<Fhelp>();
		List<Fhelptype> fhelptypeList = new ArrayList<Fhelptype>();

		fhelptypeList = this.fhelptypeService.findAll();
		if(null != fhelptypeList && fhelptypeList.size() > 0){
			for(Fhelptype fhelptype : fhelptypeList ){
				List<Fhelp> fhelps = this.fhelpService.list(0,0," where fhelptype.fid =  "+fhelptype.getFid()+" order by  forder asc",false);
				if(null != fhelps && fhelps.size() > 0){
					fhelptype.setFhelp_index(fhelps);
				}
			}
		}

		if(id != 0){
			fhelp = fhelpService.findById(id);
		}else if(null != helpList && helpList.size() > 0){
			fhelp = helpList.get(0);
		}

		modelAndView.addObject("id", id);
		modelAndView.addObject("type", type);
		modelAndView.addObject("fhelptypeList", fhelptypeList);
		modelAndView.addObject("helpList", helpList);
		modelAndView.addObject("fhelp", fhelp);
		modelAndView.setViewName("front/about/index");

		return modelAndView ;
	}

	@RequestMapping("/about/appDownload")
	public ModelAndView appDown() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;

		modelAndView.setViewName("front/about/appDownload") ;
		return modelAndView ;
	}


	/**
	 * 新手引导页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/about/guide")
	public ModelAndView userGuide() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;

		modelAndView.setViewName("front/about/user_guide") ;
		return modelAndView ;
	}

	/**
	 * 技术支持
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/support/index")
	public ModelAndView supportIndex() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;

		modelAndView.setViewName("front/support/index") ;
		return modelAndView ;
	}
}
