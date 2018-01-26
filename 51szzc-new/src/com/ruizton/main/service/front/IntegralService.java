package com.ruizton.main.service.front;

import com.ruizton.main.Enum.IntegralTypeEnum;
import com.ruizton.main.dao.FscoreDAO;
import com.ruizton.main.dao.FuserDAO;
import com.ruizton.main.dao.integral.FintegralactivityDAO;
import com.ruizton.main.dao.integral.FusergradeDAO;
import com.ruizton.main.dao.integral.FuserintegraldetailDAO;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fscore;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.integral.Fintegralactivity;
import com.ruizton.main.model.integral.Fusergrade;
import com.ruizton.main.model.integral.Fuserintegraldetail;
import com.ruizton.util.Utils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by a123 on 17-3-1.
 */
@Service
public class IntegralService {

    @Autowired
    private FintegralactivityDAO fintegralactivityDAO;

    @Autowired
    private FusergradeDAO fusergradeDAO;

    @Autowired
    private FuserintegraldetailDAO fuserintegraldetailDAO;

    @Autowired
    private FuserDAO fuserDAO;

    @Autowired
    private FscoreDAO fscoreDAO;


    /**
     *  新手任务送积分
     * @param typeEnum
     * @param userId
     * @param relationId
     */
    public int addUserIntegralFirst(IntegralTypeEnum typeEnum,int userId,int relationId){

         int  integral = 500;  //积分  写死
         if(typeEnum == null ){
             return 0;
         }

        try{
            Fuser fuser = this.fuserDAO.findById(userId);
            Fscore fscore = fuser.getFscore();
            //已有积分记录
            if(this.hasIntegralFirst(fscore,typeEnum)){
                return 0;
            }
            LOG.i(this.getClass(),"新手任务送积分");
            Fuserintegraldetail fuserintegraldetail = new Fuserintegraldetail();
            fuserintegraldetail.setFuser(fuser);
            fuserintegraldetail.setCreateDate(Utils.getTimestamp());
            fuserintegraldetail.setType(typeEnum.getCode());
            fuserintegraldetail.setOperateAmount(integral);
            fuserintegraldetail.setRelationId(relationId);
            fuserintegraldetailDAO.save(fuserintegraldetail);

            //根据积分获取对应等级
            Fusergrade fusergrade = getUserLevel(fuser.getIntegral()+integral);
            if(fusergrade != null && fuser.getFscore().getFlevel() != fusergrade.getFid()){
                fscore.setFlevel(fusergrade.getFid());
            }
            //修改获取标识
            setIntegralFirst(fscore,typeEnum);
            this.fscoreDAO.attachDirty(fscore);

            fuser.setIntegral(fuser.getIntegral()+integral);
            this.fuserDAO.attachDirty(fuser);

        }catch (Exception e){
            return 0;
        }
        return integral;
    }


    /**
     *  增加积分   ---  活动 、多次
     * @param integralTyp
     * @param fuser
     * @param allAmount
     * @param fid
     */
    public void addUserIntegral(IntegralTypeEnum integralTyp, Fuser fuser,double allAmount,int fid){
        LOG.i(this.getClass(),"增加用户积分开始:"+integralTyp.getDesc());
        if(fuser == null || integralTyp == null){
            return;
        }
        try{
            Fintegralactivity fintegralactivity = this.fintegralactivityDAO.findOpenActivity(integralTyp.getCode());
            if(fintegralactivity == null) return;
           // if(!isActivty(fintegralactivity)) return;

            //获取今天已获取积分数
            int todaySum = 0;
            if(fintegralactivity.getDayMax() > 0){
                todaySum = fuserintegraldetailDAO.getTodayIntegral(integralTyp.getCode(),fuser.getFid());
            }

            int integral = getIntegralAmount(fintegralactivity.getIntegral(),todaySum,fintegralactivity.getDayMax(),allAmount,fintegralactivity.getNeedAmount());

            if(integral == 0){
                return;
            }

            //根据积分获取对应等级
            Fusergrade fusergrade = getUserLevel(fuser.getIntegral()+integral);
            if(fusergrade != null && fuser.getFscore().getFlevel() != fusergrade.getFid()){
                Fscore fscore = this.fscoreDAO.findById(fuser.getFscore().getFid());
                fscore.setFlevel(fusergrade.getFid());
                this.fscoreDAO.attachDirty(fscore);
            }
            fuser.setIntegral(fuser.getIntegral()+integral);
            this.fuserDAO.attachDirty(fuser);

            Fuserintegraldetail fuserintegraldetail = new Fuserintegraldetail();
            fuserintegraldetail.setFuser(fuser);
            fuserintegraldetail.setCreateDate(Utils.getTimestamp());
            fuserintegraldetail.setType(integralTyp.getCode());
            fuserintegraldetail.setOperateAmount(integral);
            fuserintegraldetail.setRelationId(fid);

            fuserintegraldetailDAO.save(fuserintegraldetail);
        }catch (Exception e){
            e.printStackTrace();
            LOG.e(this.getClass(),"添加用户积分失败");
        }

    }

    /**
     *   获取本次积分数
     * @param integral   单次积分数
     * @param today      今天获取积分数
     * @param dayMax        每日上限
     * @param allAmount  消耗总单位
     * @param single     单个基数
     * @return
     */
    public int getIntegralAmount(int integral,int today,int dayMax,double allAmount,double single){
        if(today >= dayMax && today > 0) return 0;
        int num = 0;
        if(allAmount >0 && single >0){
            num = (int)(allAmount/single);
            integral = integral*num;
        }
        if(dayMax == 0){
            return integral;
        }
        return integral>dayMax-today?dayMax-today:integral;

    }


    public static void main(String[] args) {
        int num = (int)(100.32232/22);

        System.out.print(num);
    }


    /**
     *  用户增加积分  单次
     * @param integralTyp
     * @param fuser
     */
    public void addUserIntegral(IntegralTypeEnum integralTyp, Fuser fuser){
        addUserIntegral(integralTyp,fuser,0,0);
    }


    /**
     *  用户增加积分  通过userid
     * @param integralTyp
     */
    public void addUserIntegral(IntegralTypeEnum integralTyp, int userId,double allAmount,int fid){
        Fuser fuser = this.fuserDAO.findById(userId);
        addUserIntegral(integralTyp,fuser,allAmount,fid);
    }

    /**
     * 根据积分获取用户等级
     * @param integral
     * @return
     */
    private Fusergrade getUserLevel(int integral){
         List<Fusergrade> list = fusergradeDAO.list(0,0," order by fneedintegral desc",false);
         for(Fusergrade fusergrade:list){
             if(integral >= fusergrade.getFneedintegral()){
                 return fusergrade;
             }
         }
         return  null;
    }


    /**
     * 获取开启的积分活动
     * @param integralTypeEnum
     * @return
     */
    public Fintegralactivity getIntegralActivy(IntegralTypeEnum integralTypeEnum){
        return this.fintegralactivityDAO.findOpenActivity(integralTypeEnum.getCode());
    }

    /**
     * 后台 修改积分
     * @param fuser
     * @param type
     * @param integral
     * @param remark
     */
    public void updateIntegralBySystem(Fuser fuser,int type,int integral,String remark){
        fuser.setFlastUpdateTime(Utils.getTimestamp());
        fuser.setIntegral(fuser.getIntegral()+integral);

        this.fuserDAO.attachDirty(fuser);

        Fuserintegraldetail fuserintegraldetail = new Fuserintegraldetail();
        fuserintegraldetail.setFuser(fuser);
        fuserintegraldetail.setCreateDate(Utils.getTimestamp());
        fuserintegraldetail.setType(type);
        fuserintegraldetail.setOperateAmount(integral);
        fuserintegraldetail.setRemark(remark);

        fuserintegraldetailDAO.save(fuserintegraldetail);


        //根据积分获取对应等级
        Fusergrade fusergrade = getUserLevel(fuser.getIntegral()+integral);
        if(fusergrade != null && fuser.getFscore().getFlevel() != fusergrade.getFid()){
            Fscore fscore = this.fscoreDAO.findById(fuser.getFscore().getFid());
            fscore.setFlevel(fusergrade.getFid());
            this.fscoreDAO.attachDirty(fscore);
        }

    }


    public int[] getUserIntegralDetails(int userId){
        return  fuserintegraldetailDAO.getDetailAccount(userId);
    }


    /**
     * 是否领取过积分
     * @param integralTypeEnum
     * @return
     */
    public Boolean hasIntegralFirst(Fscore fscore,IntegralTypeEnum integralTypeEnum){
        Boolean result;
        switch (integralTypeEnum.getCode()){
            case 10:
                result = fscore.isIntegralPhone();
                break;
            case 11:
                result =  fscore.isIntegralEmail();
                break;
            case 12:
                result = fscore.isIntegralRealName();
                break;
            case 13:
                result = fscore.isIntegralBankInfo();
                break;
            case 14:
                result = fscore.isIntegralTradePWD();
                break;
            case 15:
                result = fscore.isIntegralGA();
                break;
            case 16:
                result = fscore.isIntegralCapitalIn();
                break;
            case 17:
                result = fscore.isIntegralFirstTrade();
                break;
            default:
                result = true;
        }

          return result;
    }


    /**
     * 设置积分领取标识
     * @param integralTypeEnum
     * @return
     */
    public void setIntegralFirst(Fscore fscore,IntegralTypeEnum integralTypeEnum){
        switch (integralTypeEnum.getCode()){
            case 10:
                fscore.setIntegralPhone(true);
                break;
            case 11:
                fscore.setIntegralEmail(true);
                break;
            case 12:
                fscore.setIntegralRealName(true);
                break;
            case 13:
                fscore.setIntegralBankInfo(true);
                break;
            case 14:
                fscore.setIntegralTradePWD(true);
                break;
            case 15:
                fscore.setIntegralGA(true);
                break;
            case 16:
                fscore.setIntegralCapitalIn(true);
                break;
            case 17:
                fscore.setIntegralFirstTrade(true);
                break;
        }
    }


    public int getIntegralByRalationId(int userId,int integralType,int relationId){
        return this.fuserintegraldetailDAO.getIntegralByRalationId(userId,integralType,relationId);
    }



}
