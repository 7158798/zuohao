package com.ruizton.main.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.Enum.SystemBankInfoEnum;
import com.ruizton.main.dao.SystembankinfoDAO;
import com.ruizton.main.model.Systembankinfo;

@Service
public class FrontBankInfoService {
	@Autowired
	private SystembankinfoDAO systembankinfoDAO ;

	/**
	 * 查询系统账户信息
	 * @param ftype  0银行卡  1支付宝
	 * @return
	 * @throws Exception
	 */
	public List<Systembankinfo> findAllSystemBankInfo(Integer ftype) throws Exception{
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 ");
		filter.append(" and fstatus = " + SystemBankInfoEnum.NORMAL_VALUE);
		filter.append(" and ftype = " + ftype);
		return this.systembankinfoDAO.list(0, 0, filter.toString(), false) ;
	}
	
	public Systembankinfo findSystembankinfoById(int id) throws Exception{
		return this.systembankinfoDAO.findById(id) ;
	}
}
