package com.ruizton.main.service.admin;

import com.ruizton.main.dao.zuohao.FhelptypeDAO;
import com.ruizton.main.model.Fhelptype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FhelptypeService {
	@Autowired
	private FhelptypeDAO fhelptypeDAO;

	public Fhelptype findById(int id) {
		return this.fhelptypeDAO.findById(id);
	}

	public void saveObj(Fhelptype obj) {
		this.fhelptypeDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fhelptype obj = this.fhelptypeDAO.findById(id);
		this.fhelptypeDAO.delete(obj);
	}

	public void updateObj(Fhelptype obj) {
		this.fhelptypeDAO.attachDirty(obj);
	}

	public List<Fhelptype> findByProperty(String name, Object value) {
		return this.fhelptypeDAO.findByProperty(name, value);
	}

	public List<Fhelptype> findAll() {
		return this.fhelptypeDAO.findAll();
	}

	public List<Fhelptype> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.fhelptypeDAO.list(firstResult, maxResults, filter,isFY);
	}

	public Fhelptype getLastType(){
		return this.fhelptypeDAO.getLastType();
	}

	public Fhelptype getPreviousHelpType(int forder){
		return this.fhelptypeDAO.getPreviousHelpType(forder);
	}

	public Fhelptype getNextHelpType(int forder){
		return this.fhelptypeDAO.getNextHelpType(forder);
	}
}