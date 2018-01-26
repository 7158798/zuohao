package com.ruizton.main.service.admin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FentrustDAO;
import com.ruizton.main.model.Fentrust;

@Service
public class EntrustService {
	@Autowired
	private FentrustDAO entrustDAO;
	@Autowired
	private HttpServletRequest request;


	public Fentrust findById(int id) {
		return this.entrustDAO.findById(id);
	}

	public void saveObj(Fentrust obj) {
		this.entrustDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fentrust obj = this.entrustDAO.findById(id);
		this.entrustDAO.delete(obj);
	}

	public void updateObj(Fentrust obj) {
		this.entrustDAO.attachDirty(obj);
	}

	public List<Fentrust> findByProperty(String name, Object value) {
		return this.entrustDAO.findByProperty(name, value);
	}

	public List<Fentrust> findAll() {
		return this.entrustDAO.findAll();
	}

	public List<Fentrust> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fentrust> all = this.entrustDAO.list(firstResult, maxResults, filter,isFY);
		for (Fentrust fentrust : all) {
			if(fentrust.getFuser() != null){
				fentrust.getFuser().getFnickName();
			}
			if(fentrust.getFtrademapping() != null){
				fentrust.getFtrademapping().getFvirtualcointypeByFvirtualcointype1().getFname();
				fentrust.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFname();
			}
		}
		return all;
	}
	
	public List<Map> getTotalQty(int fentrustType,String entrustFilter) {
		return this.entrustDAO.getTotalQty(fentrustType,entrustFilter);
	}
	
	public List<Map> getTotalQty(int fentrustType,boolean isToady,String entrustFilter) {
		return this.entrustDAO.getTotalQty(fentrustType,isToady,entrustFilter);
	}

	public BigDecimal getTotalQtyByTrade(int userId,int fentrustType){
		return this.entrustDAO.getTotalQtyByTrade(userId, fentrustType);
	}
}