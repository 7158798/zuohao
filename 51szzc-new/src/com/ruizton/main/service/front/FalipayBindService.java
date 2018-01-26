package com.ruizton.main.service.front;

import com.ruizton.main.Enum.BankInfoStatusEnum;
import com.ruizton.main.Enum.SystemBankTypeEnum;
import com.ruizton.main.dao.zuohao.FalipayBindDao;
import com.ruizton.main.model.FalipayBind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付宝绑定
 * Created by luwei on 17-3-15.
 */
@Service
public class FalipayBindService {

    @Autowired
    private FalipayBindDao falipayBindDao;


    public FalipayBind findById(int id) {
        return falipayBindDao.findById(id);
    }

    public void save(FalipayBind transientInstance) {
        falipayBindDao.save(transientInstance);
    }

    public List<FalipayBind> list(int firstResult, int maxResults,
                                  String filter, boolean isFY) {
        List<FalipayBind> list = this.falipayBindDao.list(firstResult, maxResults, filter, isFY);
        if(list != null && list.size() > 0) {
            for(FalipayBind bind : list) {
                bind.getFuser().getFloginName();
                bind.getFuser().getFnickName();
                bind.getFuser().getFrealName();
            }
        }
        return list;
    }

    public List<FalipayBind> findListByUserId(int userId) {
        StringBuffer filter = new StringBuffer();
        filter.append(" where fstatus = " + BankInfoStatusEnum.NORMAL_VALUE);
        filter.append(" and fuser.fid = " + userId);
        filter.append(" order by fid desc");
        return falipayBindDao.list(0, 0, filter.toString(), false);
    }

    public void delete(FalipayBind instance) {
        falipayBindDao.delete(instance);
    }

    public FalipayBind findByAccount(String account) {
        List<FalipayBind> list =  falipayBindDao.findByProperty("faccount", account);
        return list.size() > 0 ? list.get(0) : null;
    }

}
