package com.ruizton.main.service.front;

import com.ruizton.main.dao.FrontEassyContestDao;
import com.ruizton.main.model.EassyContestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mibook3 on 17-4-12.
 */

@Service
public class FrontEassyContestService {

//
    @Autowired
    private FrontEassyContestDao frontEassyContestDao;


    public EassyContestInfo findByTelephone(String telephone) {
   String queryString = "from EassyContestInfo where telephone = ? ";

     List<EassyContestInfo> list=  frontEassyContestDao.getHibernateTemplate().find(queryString,telephone);

        if (list.size()==0) {
            return null;
        } else {
            return list.get(0);
        }

    }

    public void saveOrUpdate(EassyContestInfo eassyContestInfo) {

        frontEassyContestDao.getHibernateTemplate().saveOrUpdate(eassyContestInfo);

    }
}
