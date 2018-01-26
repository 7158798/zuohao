package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.FhelpDAO;
import com.ruizton.main.model.Fhelp;
import com.ruizton.main.model.vo.HotHelpVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class FhelpService {
	@Autowired
	private FhelpDAO fhelpDAO;

	public Fhelp findById(int id) {
		return this.fhelpDAO.findById(id);
	}

	public void saveObj(Fhelp obj) {
		this.fhelpDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fhelp obj = this.fhelpDAO.findById(id);
		this.fhelpDAO.delete(obj);
	}

	public void updateObj(Fhelp obj) {
		this.fhelpDAO.attachDirty(obj);
	}

	public List<Fhelp> findByProperty(String name, Object value) {
		return this.fhelpDAO.findByProperty(name, value);
	}

	public List<Fhelp> findAll() {
		return this.fhelpDAO.findAll();
	}

	public List<Fhelp> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.fhelpDAO.list(firstResult, maxResults, filter,isFY);
	}

	public List<HotHelpVo> hotHelp(){
		return this.fhelpDAO.hotHelp();
	}

    public Fhelp getLastHelp() {
		return this.fhelpDAO.getLastHelp();
    }

	public Fhelp getNextHelp(Fhelp fhelp) {
		return this.fhelpDAO.getNextHelp(fhelp);

	}

	public Fhelp getPreviousHelp(Fhelp fhelp) {
		return this.fhelpDAO.getPreviousHelp(fhelp);

	}
}