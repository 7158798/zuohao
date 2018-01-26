package com.ruizton.main.service.front;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ruizton.main.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FaboutDAO;
import com.ruizton.main.dao.FarticleDAO;
import com.ruizton.main.dao.FarticletypeDAO;
import com.ruizton.main.dao.FbankinDAO;
import com.ruizton.main.dao.FcapitaloperationDAO;
import com.ruizton.main.dao.FentrustlogDAO;
import com.ruizton.main.dao.FfriendlinkDAO;
import com.ruizton.main.dao.FintrolinfoDAO;
import com.ruizton.main.dao.FmessageDAO;
import com.ruizton.main.dao.FperiodDAO;
import com.ruizton.main.dao.FscoreDAO;
import com.ruizton.main.dao.FuserDAO;
import com.ruizton.main.dao.FvirtualcointypeDAO;
import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.main.service.admin.SystemArgsService;

@Service
public class FrontOthersService {
	@Autowired
	private FfriendlinkDAO ffriendlinkDAO ;
	@Autowired
	private FarticleDAO farticleDAO ;
	@Autowired
	private FaboutDAO faboutDAO ;
	@Autowired
	private FarticletypeDAO farticletypeDAO ;
	@Autowired
	private FperiodDAO fperiodDAO ;
	@Autowired
	private FentrustlogDAO fentrustlogDAO ;
	@Autowired
	private FbankinDAO fbankinDAO ;
	@Autowired
	private FuserDAO fuserDao;
	@Autowired
	private FscoreDAO fscoreDAO;
	@Autowired
	private FintrolinfoDAO fintrolinfoDAO;
	@Autowired
	private SystemArgsService systemArgsService;
	@Autowired
	private FcapitaloperationDAO fcapitaloperationDAO ;
	@Autowired
	private FvirtualcointypeDAO virtualcointypeDAO;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	@Autowired
	private FmessageDAO fmessageDAO;
	
	public List<Ffriendlink> findFfriendLink(int type){
		return this.ffriendlinkDAO.findByProperty("ftype",type) ;
	}
	
	public Fabout findFabout(int fid){
		return this.faboutDAO.findById(fid) ;
	}
	
	public List<Fabout> findFaboutAll(){
		return this.faboutDAO.findAll() ;
	}
	
	public List<Farticle> findFarticle(int farticletype,int firstResult,int maxResult){
		List<Farticle> farticles = this.farticleDAO.findFarticle(farticletype, firstResult, maxResult);
		return farticles ;
	}

	//web端资讯列表，根据类型查询，（仅显示审核通过、免审核的）
	public List<Farticle> findFarticleByTypeOut(int farticletype,int firstResult,int maxResult){
		List<Farticle> farticles = this.farticleDAO.findFarticleByTypeOut(farticletype, firstResult, maxResult);
		return farticles ;
	}

	public List<Farticle> findOutArticle(int firstResult,int maxResult){
		List<Farticle> farticles = this.farticleDAO.findOutArticle(firstResult, maxResult);
		return farticles ;
	}
	public List<Farticle> findHostArticle(int firstResult,int maxResult){
		List<Farticle> farticles = this.farticleDAO.findHostArticle(firstResult, maxResult);
		return farticles ;
	}
	public int findFarticleCount(int farticletype){
		return this.farticleDAO.findFarticleCount(farticletype) ;
	}

	public int findFarticle(int farticletype){
		return this.farticleDAO.findFarticleCount(farticletype) ;
	}
	
	public List<Ffriendlink> findFriendLink(int type,int firstResult,int maxResult){
		return this.ffriendlinkDAO.findFriendLink(type, firstResult, maxResult) ;
	}
	
	public Farticle findFarticleById(int id){
		Farticle farticle = this.farticleDAO.findById(id) ;
		return farticle ;
	}
	
	public Farticletype findFarticleTypeById(int id){
		return this.farticletypeDAO.findById(id) ;
	}
	
	public List<Farticletype> findFarticleTypeAll(){
		return this.farticletypeDAO.findAll() ;
	}
	
	public List<Fperiod> findAllFperiod(long fromTime,int fvirtualCoinType){
		return this.fperiodDAO.findAllFperiod(fromTime, fvirtualCoinType) ;
	}

	public List<Fperiod> findFperiodList(int firstResult, int maxResults, String filter, boolean isFYe){
		return this.fperiodDAO.list(firstResult, maxResults, filter, isFYe);
	}
	
	public void addFperiod(Fperiod fperiod){
		this.fperiodDAO.save(fperiod) ;
	}
	public void addFperiods(List<Fperiod> fperiods){
		for (Fperiod fperiod : fperiods) {
			this.fperiodDAO.save(fperiod) ;
		}
	}
	
	public Fperiod getLastFperiod(Fvirtualcointype fvirtualcointype){
		return this.fperiodDAO.getLastFperiod(fvirtualcointype) ;
	}
	
	public Fentrustlog getLastFpeFentrustlog(int fvirtualcointype){
		return this.fentrustlogDAO.getLastFpeFentrustlog(fvirtualcointype) ;
	}
	
	
	public List<Fbankin> findFbankin(){
		return this.fbankinDAO.findByProperty("ok", 0) ;
	}


	/**
	 * 查询用户的
	 * @param startTime  开始时间
	 * @param endTime  结束时间
	 * @param enType  委托类型
	 * @param userId  用户id
	 * @param fees 手续费百分比(买卖手续费可能不同，不同会员有不同等级，有不同手续费)
	 * @param fvi_fid 虚拟币id集合
	 * @return
	 */
	public Map<Integer, FentrustlogVo> queryLogByTime(String startTime, String endTime, Integer enType, Integer userId, BigDecimal fees, String fvi_fid) {
		return this.fentrustlogDAO.queryLogByTime(startTime, endTime, enType, userId, fees, fvi_fid);
	}

	public FentrustlogVo queryLowAndHigh(String start,String end,Integer mappingId){
		return this.fentrustlogDAO.queryLowAndHighByTime(start, end, mappingId);
	}
	
}
