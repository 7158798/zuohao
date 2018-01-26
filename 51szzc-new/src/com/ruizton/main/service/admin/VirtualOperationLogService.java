package com.ruizton.main.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FintrolinfoDAO;
import com.ruizton.main.dao.FmessageDAO;
import com.ruizton.main.dao.FvirtualoperationlogDAO;
import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.main.model.Fintrolinfo;
import com.ruizton.main.model.Fvirtualoperationlog;
import com.ruizton.main.model.Fvirtualwallet;

@Service
public class VirtualOperationLogService {
	@Autowired
	private FvirtualoperationlogDAO virtualoperationlogDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDao;
	@Autowired
	private FmessageDAO messageDAO;
	@Autowired
	private FintrolinfoDAO introlinfoDAO;

	public Fvirtualoperationlog findById(int id) {
		Fvirtualoperationlog operationLog = this.virtualoperationlogDAO.findById(id);;
		return operationLog;
	}

	public void saveObj(Fvirtualoperationlog obj) {
		this.virtualoperationlogDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fvirtualoperationlog obj = this.virtualoperationlogDAO.findById(id);
		this.virtualoperationlogDAO.delete(obj);
	}

	public void updateObj(Fvirtualoperationlog obj) {
		this.virtualoperationlogDAO.attachDirty(obj);
	}

	public List<Fvirtualoperationlog> findByProperty(String name, Object value) {
		return this.virtualoperationlogDAO.findByProperty(name, value);
	}

	public List<Fvirtualoperationlog> findAll() {
		return this.virtualoperationlogDAO.findAll();
	}

	public List<Fvirtualoperationlog> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Fvirtualoperationlog> all = this.virtualoperationlogDAO.list(firstResult, maxResults, filter,isFY);
		for (Fvirtualoperationlog foperationlog : all) {
			foperationlog.getFuser().getFemail();
			if(foperationlog.getFcreator() != null){
				foperationlog.getFcreator().getFname();
			}
			foperationlog.getFvirtualcointype().getFname();
		}
		return all;
	}
	
	public void updateVirtualOperationLog(Fvirtualwallet virtualwallet,Fvirtualoperationlog virtualoperationlog,Fintrolinfo info) {
		try {
			this.virtualwalletDao.attachDirty(virtualwallet);
			this.virtualoperationlogDAO.attachDirty(virtualoperationlog);
			if(info != null){
				introlinfoDAO.save(info);
			}
		} catch (Exception e) {
			throw new  RuntimeException();
		}
	}

}