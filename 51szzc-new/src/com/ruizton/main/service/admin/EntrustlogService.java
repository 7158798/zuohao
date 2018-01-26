package com.ruizton.main.service.admin;

import com.ruizton.main.dao.FentrustlogDAO;
import com.ruizton.main.model.vo.TradeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class EntrustlogService {
	@Autowired
	private FentrustlogDAO entrustlogDAO;
	@Autowired
	private HttpServletRequest request;

	public List list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List all = this.entrustlogDAO.list(firstResult, maxResults, filter,isFY);
		return all;
	}
	
	public Double getTotalTradeAmt() {
		return this.entrustlogDAO.getTotalTradeAmt();
	}

	@Cacheable(value = "trade")
	public List<TradeVo> findTrade(Integer symbol, Integer since){
		if(null == since){
			since = 60;
		}
		return this.entrustlogDAO.findTrade(symbol, since);
	}
	
}