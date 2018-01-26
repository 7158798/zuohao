package com.ruizton.main.quartz;

import com.ruizton.main.model.FhostRecommended;
import com.ruizton.main.model.Ftag;
import com.ruizton.main.service.admin.FtagService;
import com.ruizton.main.service.admin.HostRecommendedService;
import com.ruizton.util.Constant;
import com.ruizton.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

/**
 * 推荐上限/每天不同的15个随机标签
 * Created by zygong on 17-3-8.
 */
public class MainQuartz{
    @Autowired
    protected HostRecommendedService hostRecommendedService;

    @Autowired
    protected FtagService ftagService;

    public void hostNumber(){
        List<FhostRecommended> all = this.hostRecommendedService.findAll();
        if(all != null && all.size() >= 2){
            Constant.setHostNumber(all.get(0).getfNumber());
            Constant.setSingleNumber(all.get(1).getfNumber());
        }
    }

    public void tagList(){
        List<String> tagList = null;
        List<Ftag> all = this.ftagService.findAll();

        if(null != all && all.size() > 0){
            tagList = new ArrayList<String>();
            for(Ftag tag : all){
                tagList.add(tag.getFname());
            }
            Constant.setTagList(tagList);
        }

    }
}
