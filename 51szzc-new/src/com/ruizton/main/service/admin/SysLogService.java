package com.ruizton.main.service.admin;

import com.ruizton.main.dao.FaboutDAO;
import com.ruizton.main.dao.FsyslogDAO;
import com.ruizton.main.model.Fabout;
import com.ruizton.main.model.Fsystemoperatorlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class SysLogService {
	@Autowired
	private FsyslogDAO fsyslogDAO;

	public Fsystemoperatorlog findById(int id) {
		return this.fsyslogDAO.findById(id);
	}

	public void saveObj(Fsystemoperatorlog obj) {
		this.fsyslogDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fsystemoperatorlog obj = this.fsyslogDAO.findById(id);
		this.fsyslogDAO.delete(obj);
	}

	public void updateObj(Fsystemoperatorlog obj) {
		this.fsyslogDAO.attachDirty(obj);
	}

	public List<Fsystemoperatorlog> findByProperty(String name, Object value) {
		return this.fsyslogDAO.findByProperty(name, value);
	}

	public List<Fsystemoperatorlog> findAll() {
		return this.fsyslogDAO.findAll();
	}

	public List<Fsystemoperatorlog> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.fsyslogDAO.list(firstResult, maxResults, filter,isFY);
	}
}