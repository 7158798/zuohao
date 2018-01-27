package com.otc.service.advertisement.biz;

import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.DigitalHelper;
import com.jucaifu.common.util.ValueHelper;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.core.cache.FquoteCacheManager;
import com.otc.core.cache.GeneralListCache;
import com.otc.core.cache.HolidayCacheManager;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.advertisement.exceptions.AdvertisementBizException;
import com.otc.facade.advertisement.pojo.dto.AdvertisementDto;
import com.otc.facade.advertisement.pojo.dto.CalculatePricedto;
import com.otc.facade.advertisement.pojo.enums.AdvertisementStatusEnum;
import com.otc.facade.advertisement.pojo.enums.AdvertisementTimeStatusEnum;
import com.otc.facade.advertisement.pojo.enums.AdvertisementTypeEnum;
import com.otc.facade.advertisement.pojo.enums.PriceTypeEnum;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.po.PriceFormula;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.pojo.vo.AdvertisementWebVo;
import com.otc.facade.advertisement.pojo.vo.CalculatePriceVo;
import com.otc.facade.advertisement.pojo.vo.OtherPlatformPriceVo;
import com.otc.facade.base.CountVo;
import com.otc.facade.sys.enums.SystemArgsEnum;
import com.otc.facade.user.enums.UserAutStatusEnum;
import com.otc.facade.user.pojo.po.UserAuthentication;
import com.otc.facade.virtual.pojo.cond.VirtualWalletCond;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.VirtualWallet;
import com.otc.pool.OTCBizPool;
import com.otc.service.advertisement.dao.AdvertisementMapper;
import com.otc.service.advertisement.dao.PriceFormulaMapper;
import com.otc.service.holiday.dao.HolidayMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zygong on 17-4-25.
 */
@Service
@Transactional
public class AdvertisementBiz extends AbsBaseBiz<Advertisement,AdvertisementVo,AdvertisementMapper> {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Autowired
    private PriceFormulaMapper priceFormulaMapper;

    @Autowired
    private HolidayMapper holidayMapper;

    @Override
    public AdvertisementMapper getDefaultMapper() {
        return advertisementMapper;
    }

    /**
     * 保存广告
     * @param advertisement
     * @return
     */
    public boolean saveAdvertisement(Advertisement advertisement) {
        StringBuffer payTypeName = null;
        boolean flag = false;
        if(advertisement == null){
            return flag;
        }

        UserAuthentication userAuthentication = OTCBizPool.getInstance().userAuBiz.selectByUserId(advertisement.getUserId());
        if(userAuthentication == null || Integer.valueOf(userAuthentication.getStatus()) <= Integer.valueOf(UserAutStatusEnum.NO_REALNAME.getCode())){
            throw new AdvertisementBizException("请先进行实名认证");
        }

        if(!ValueHelper.checkParam(advertisement.getCoinType(), advertisement.getCity(), advertisement.getMaxTransCount(), advertisement.getMinTransCount(),
                advertisement.getIsOpenSafetyVeri(), advertisement.getPayType(), advertisement.getPayTypeName(),
                advertisement.getTransactionType(), advertisement.getPriceType())){
            throw new AdvertisementBizException("请求参数不能为空");
        }
        if(PriceTypeEnum.YJ.getValue() == advertisement.getPriceType()){
            if(!ValueHelper.checkParam(advertisement.getTradePlatform(), advertisement.getPremiumRate(), advertisement.getTransactionPrice())){
                throw new AdvertisementBizException("请求参数不能为空");
            }
        }else if(PriceTypeEnum.GDJG.getValue() == advertisement.getPriceType()){
            if(!ValueHelper.checkParam(advertisement.getTransactionPrice())){
                throw new AdvertisementBizException("请求参数不能为空");
            }
        }else{
            throw new AdvertisementBizException("数据不正确");
        }
        // 同一个用户只能发布5条广告
        List<Advertisement> list = userAdCount(advertisement.getUserId());
        if(list != null && list.size() >= 5){
            throw new AdvertisementBizException("最多可以发布5条广告");
        }
        advertisement.setMaxTransAmount(advertisement.getMaxTransCount().multiply(advertisement.getTransactionPrice()));
        advertisement.setMinTransAmount(advertisement.getMinTransCount().multiply(advertisement.getTransactionPrice()));
        // 如果是在线出售 最大交易量不能超过可用总量
        if(advertisement.getTransactionType().intValue() == AdvertisementTypeEnum.SELL.getValue()){
            VirtualWalletCond cond = new VirtualWalletCond();
            cond.setUserId(advertisement.getUserId());
            cond.setCoinId(advertisement.getCoinType());
            VirtualWallet virtualWallet = OTCBizPool.getInstance().virtualWalletBiz.queryVirtualWallet(cond);
            if(virtualWallet == null || (virtualWallet.getTotal() != null && virtualWallet.getTotal().compareTo(advertisement.getMaxTransCount()) < 0)){
                throw new AdvertisementBizException("最大交易量已超出钱包总量");
            }
        }

        advertisement.setCode(getCode());

        int saveResult = advertisementMapper.insert(advertisement);
        if(saveResult != 0){
            flag = Boolean.TRUE;
        }

        return flag;
    }

    /**
     *  获取用户已发布广告数量
     * @param userId
     * @return
     */
    private List<Advertisement> userAdCount(Long userId) {
        AdvertisementVo vo = new AdvertisementVo();
        vo.setUserId(userId);
        vo.setStatus(AdvertisementStatusEnum.PUBLISHED.getValue());
        return this.getList(vo);
    }

    /**
     * 生成编码  170508118001（年后两位+月+日+100-200随机数+当日广告自然数）自然数001，为三位
     * @return
     */
    private synchronized String getCode(){
        String code = "";
        //年后两位+月+日
        code += sdf.format(new Date());

        // 100-200随机数
        int i = new Random().nextInt(100) + 100;
        code += i;

        // 当日广告自然数
        // 获取当天最后一条数据
        Advertisement last = this.getLast();
        if(last == null){
            code += "001";
        }else {
            String lastCode = last.getCode();
            lastCode  = lastCode.substring(lastCode.length() - 3, lastCode.length());
            int newNumber = Integer.valueOf(lastCode) + 1;
            // 固定三位 不足补0
            code += String.format("%03d", newNumber);
        }
        return code;
    }

    /**
     * 后台获取广告列表
     * @param vo
     * @return
     */
    public AdvertisementVo queryCountByConditionPage(AdvertisementVo vo) {
        List<Advertisement> advertisements = advertisementMapper.queryCountByConditionPage(vo);
        if (advertisements != null) {
            priceFormulaList(advertisements);
            vo.setResultList(advertisements);
        }
        return vo;
    }

    public Advertisement aPriceFormula(Advertisement advertisement){
        List<Advertisement> list = null;
        if(advertisement != null){
            list = new ArrayList<Advertisement>();
            list.add(advertisement);
            list = priceFormula(list);
            advertisement = list.get(0);
        }
        return advertisement;
    }

    public List<Advertisement> priceFormulaList(List<Advertisement> list){
        return priceFormula(list);
    }

    /**
     * 计算公式
     * @param adList
     * @return
     */
    public List<Advertisement> priceFormula(List<Advertisement> adList) {
        // 从缓存中获取其他平台的价格
        Map<String, Map> allCache = FquoteCacheManager.getCacheObj();

        if (adList != null && adList.size() > 0 && allCache != null) {
            for (Advertisement advertisement : adList) {
                advertisement.setTransactionPriceStr(DigitalHelper.decimalFormat(advertisement.getTransactionPrice().doubleValue(), 2));
                if (advertisement.getPriceType() != PriceTypeEnum.YJ.getValue()) {
                    continue;
                }
                Map<String, String> priceMap = allCache.get(advertisement.getCoinType().toString());
                if (priceMap != null) {
                    String price = priceMap.get(advertisement.getTradePlatform());
                    // P*(1+ 0.001*C)
                    String priceFormula = advertisement.getPriceFormula();
                    if (StringUtils.isBlank(priceFormula)) {
                        continue;
                    }
                    // 截取(1+ 0.001*C)
                    priceFormula = priceFormula.substring(2, priceFormula.length());
                    // 计算(1+ 0.001*C)
                    Double result = (Double)DigitalHelper.calculate(priceFormula.replaceAll("C", String.valueOf(advertisement.getPremiumRate())));
                    if (result != null) {
                        advertisement.setTradePlatformFormula(advertisement.getTradePlatform() + "*" + DigitalHelper.decimalFormat(result, 2));
                    } else {
                        advertisement.setPriceFormula("");
                    }
                }
            }
        }
        return adList;
    }

    public AdvertisementWebVo indexQueryCountByConditionPage(AdvertisementWebVo vo) {
        if (vo == null) {
            return vo;
        }
        vo.setAdvertisementTimeStatus(AdvertisementTimeStatusEnum.GZR.getValue());
        // 从缓存中获取节假日期
        List<String> list = null;
        GeneralListCache cacheObj = HolidayCacheManager.getCacheObj();
        if(cacheObj == null || cacheObj.getList() == null || cacheObj.getList().isEmpty()){
            list = holidayMapper.getHoliday();
            // 将数据库中的数据放到缓存中
            GeneralListCache cache = new GeneralListCache();
            cache.setList(list);
            HolidayCacheManager.saveCache(cache);
        }else{
            list = cacheObj.getList();
        }
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_MONTH);
        String time = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        vo.setTime(time);
        if(list != null && !list.isEmpty()){

            // 判断当天是否为节假日
            for(String day : list){
                if(Integer.valueOf(day) == i){
                    vo.setAdvertisementTimeStatus(AdvertisementTimeStatusEnum.ZMJJR.getValue());
                    break;
                }
            }
        }

        List<AdvertisementDto> advertisementDtos = advertisementMapper.indexQueryCountByConditionPage(vo);
        if (advertisementDtos != null) {
            for(AdvertisementDto dto : advertisementDtos){
                dto.setTransactionPriceStr(DigitalHelper.decimalFormat(dto.getTransactionPrice().doubleValue(),2));
                dto.setMaxTransAmountStr(DigitalHelper.decimalFormat(dto.getMaxTransAmount().doubleValue(), 2));
                dto.setMinTransAmountStr(DigitalHelper.decimalFormat(dto.getMinTransAmount().doubleValue(), 2));
            }
            vo.setResultList(advertisementDtos);
        }

        return vo;
    }

    public boolean updateStatus(Integer status, Long id){
        boolean flag = false;
        int updateResult = advertisementMapper.updateStatus(status, id);
        if(updateResult != 0){
            flag = Boolean.TRUE;
        }
        return flag;
    }

    public Advertisement selectById(Long id){
        Advertisement advertisement = advertisementMapper.select(id);
        return advertisement;
    }

    public Advertisement detail(Long userId, Long id){
        Advertisement advertisement = null;
        if(userId == null || userId ==0 || id == null || id == 0){
            return advertisement;
        }
        advertisement = advertisementMapper.detail(userId, id);
        return aPriceFormula(advertisement);
    }

    /**
     * 获取广告详情和最多成交量
     * @param id
     * @param token
     * @return
     */
    public Advertisement getDetailAndMostDealCount(Long id, String token){
        Advertisement advertisement = null;
        Long userId = null;
        if(id == null){
            return advertisement;
        }
        advertisement = advertisementMapper.select(id);
        if(advertisement == null){
            throw new AdvertisementBizException("数据获取错误");
        }
        if(AdvertisementTypeEnum.SELL.getValue() == advertisement.getTransactionType().intValue()){
            userId = advertisement.getUserId();
        }else{
            userId = UserCacheManager.getUidWithToken(token);
            if(userId == null){
                userId = 0l;
            }
        }

        // 付款期限
        Long systemArgsForLong = OTCBizPool.getInstance().systemArgsBiz.getSystemArgsForLong(SystemArgsEnum.TRADE_TIME_LIMIT);
        if(systemArgsForLong != null){
            advertisement.setPayTime(systemArgsForLong);
        }else{
            advertisement.setPayTime(0l);
        }

        // 最多购买数
        VirtualWallet virtualWallet = OTCBizPool.getInstance().virtualWalletBiz.getByUserIdAndCoinId(userId, advertisement.getCoinType());
        if(virtualWallet != null && advertisement.getTransactionType().intValue() == AdvertisementTypeEnum.SELL.getValue()){
            //钱包余额判断
            VirtualCoin coin = OTCBizPool.getInstance().virtualCoinBiz.select(advertisement.getCoinType());
            if(coin == null){
                throw new BizException("交易币种不存在");
            }
            BigDecimal account = virtualWallet.getTotal();
            //手续费
            BigDecimal feerate = coin.getTradeFees();
            BigDecimal fee = feerate == null?BigDecimal.ZERO:feerate.multiply(account);

            if(coin.getLowTradeFees() != null && (fee == null || fee.compareTo(coin.getLowTradeFees()) < 0)){
                fee = coin.getLowTradeFees();
            }
            account = account.subtract(fee);

            if(account.compareTo(advertisement.getMaxTransCount()) > 0){
                advertisement.setMostDealCount(advertisement.getMaxTransCount());
            }else{
                advertisement.setMostDealCount(account);
            }
        }else{
            advertisement.setMostDealCount(new BigDecimal(0));
        }
        advertisement.setTransactionPriceStr(DigitalHelper.decimalFormat(advertisement.getTransactionPrice().doubleValue(), 2));
        return advertisement;
    }

    public Advertisement getLast(){
        return advertisementMapper.getLast();
    }

    public List<Advertisement> getList(AdvertisementVo vo){
        return advertisementMapper.getList(vo);
    }

    public CalculatePricedto calculatePrice(CalculatePriceVo vo){
        CalculatePricedto pricedto = null;
        Map<String, Map> allCache = FquoteCacheManager.getCacheObj();
        if(allCache == null){
            return null;
        }
        Advertisement advertisement = advertisementMapper.select(vo.getAdvertisementId());
        Map<String, String> priceMap = allCache.get(null != vo.getSymbol() ? vo.getSymbol().toString() : advertisement.getCoinType().toString());
        String price = priceMap.get(StringUtils.isNotBlank(vo.getPlatform()) ? vo.getPlatform() : advertisement.getTradePlatform());
        if(vo.getAdvertisementId() != null && vo.getAdvertisementId() != 0){
            pricedto = getPrice(price, advertisement.getCoinType(), String.valueOf(advertisement.getPremiumRate()), advertisement.getTradePlatform());
        }else{
            pricedto = getPrice(price, vo.getSymbol(), vo.getPremiumRate(), vo.getPlatform());
        }
        return pricedto;
    }

    /**
     * 计算最新单价
     * @return
     */
    private CalculatePricedto getPrice(String price, Long coinId, String premiumRate, String platForm) {
        CalculatePricedto dto = new CalculatePricedto();
        if (StringUtils.isNotBlank(price)) {
            PriceFormula priceFormula = priceFormulaMapper.detail(null, coinId);
            if(priceFormula == null || StringUtils.isBlank(priceFormula.getPriceFormula())){
                throw new AdvertisementBizException("该币暂不支持溢价");
            }

            String str = priceFormula.getPriceFormula();

            str = str.replaceAll("C", premiumRate);
            double rate = (Double) DigitalHelper.calculate(str.substring(2, str.length()));
            // 前端展示公式
            String indexStr = platForm + "*" + DigitalHelper.decimalFormat(rate, 2);
            // 数值计算公式
            str = str.replaceAll("P", price);

            // 计算结果
            Double calculate = (Double) DigitalHelper.calculate(str);
            dto.setPrice(DigitalHelper.decimalFormat(calculate, 2));
            dto.setFormula(indexStr);
        } else {
            dto.setPrice("");
            dto.setFormula("");
        }
        return dto;
    }

    /**
     * 综合统计-全部
     * @return
     */
    public List<CountVo> countAdvertisement(){
        return this.countAdvertisement(null);
    }

    /**
     * 综合统计-一天
     * @param dayTime
     * @return
     */
    public List<CountVo> countAdvertisement(Date dayTime){
        BigDecimal total = new BigDecimal(0);
        CountVo totalVo = new CountVo();
        List<CountVo> countVos = advertisementMapper.countAdvertisement(DateHelper.date2String(dayTime, DateHelper.DateFormatType.YearMonthDay));
        if(countVos != null && countVos.size() > 0){
            for(CountVo vo : countVos){
                total = total.add(vo.getCountTotal());
            }
        }
        totalVo.setCountName("total");
        totalVo.setCountTotal(total);
        countVos.add(totalVo);

        return countVos;
    }

    /**
     * 获取用户其他交易广告
     * @param number
     * @return
     */
    public List<Advertisement> getUserOtherList(Long id, Long userId, Integer number){
        if(userId == null || userId == 0){
            Advertisement select = advertisementMapper.select(id);
            if(select == null){
                throw new AdvertisementBizException("数据错误");
            }
            userId = select.getUserId();
        }
        return advertisementMapper.getUserOtherList(id, userId, number);
    }

    /**
     * 人民币和币种价格转换
     * @param type      rmb  coin
     * @param number    金额or数量
     * @param id        广告id
     * @return
     */
    public String transform(String type, BigDecimal number, Long id){
        if(!ValueHelper.checkParam(type, number, id)){
            throw new AdvertisementBizException("请求参数不能为空");
        }
        Advertisement advertisement = selectById(id);
        if(advertisement == null){
            throw new AdvertisementBizException("获取不到广告信息");
        }
        BigDecimal result = new BigDecimal(0);
        boolean isGetOtherPlatformData = true;

        if(advertisement.getPriceType() == PriceTypeEnum.GDJG.getValue()){
            return getPrice(type, advertisement.getTransactionPrice(), number, null);
        }
        String price = "0";
        // 获取缓存的第三方价格
        Map<String, String> map = FquoteCacheManager.getCacheObj(advertisement.getCoinType());
        if(isGetOtherPlatformData){
            if(map == null || map.isEmpty()){
                isGetOtherPlatformData = false;
            }else {
                String otherPrice = map.get(advertisement.getTradePlatform());
                if (StringUtils.isNotBlank(otherPrice)) {
                    price = getPrice(type, new BigDecimal(otherPrice), number, advertisement.getPremiumRate());
                } else {
                    isGetOtherPlatformData = false;
                }
            }
        }

        if(!isGetOtherPlatformData){
            return getPrice(type, advertisement.getTransactionPrice(), number, advertisement.getPremiumRate());
        }
        return price;
    }

    private String getPrice(String type, BigDecimal price, BigDecimal number, Float rate){
        if(rate != null){
            price = price.multiply(new BigDecimal(1 + 0.01 * rate));
        }
        if ("coin".equals(type)) {
            number = number.multiply(price);
            return DigitalHelper.decimalFormat(number.doubleValue(), 2);
        } else {
            return number.divide(price, 8, BigDecimal.ROUND_DOWN).toString();
        }
    }

    /**
     * 获取其他平台接口
     * @param coinId
     * @return
     */
    public List<OtherPlatformPriceVo> otherPlatformPrice(Long coinId){
        List<OtherPlatformPriceVo> list = new ArrayList<OtherPlatformPriceVo>();
        if(coinId == null && coinId == 0){
            return list;
        }
        OtherPlatformPriceVo otherPlatformPriceVo = null;

        Map<String, String> map = FquoteCacheManager.getCacheObj(coinId);
        if(map != null){
            for(Map.Entry<String, String> m : map.entrySet() ){
                otherPlatformPriceVo = new OtherPlatformPriceVo();
                otherPlatformPriceVo.setName(m.getKey());
                otherPlatformPriceVo.setPrice(m.getValue());
                list.add(otherPlatformPriceVo);
            }
        }
        return list;
    }

    /**
     * 判断用户币的数量
     * @param userId
     * @param coinId
     * @return
     */
    public BigDecimal userCoinNumber(Long userId, Long coinId){
        BigDecimal number = new BigDecimal(0);
        if(userId == null || coinId == null){
            throw new AdvertisementBizException("获取失败");
        }
        VirtualWallet virtualWallet = OTCBizPool.getInstance().virtualWalletBiz.getByUserIdAndCoinId(userId, coinId);
        if(virtualWallet != null){
            number = virtualWallet.getTotal();
        }
        return number;
    }

    /**
     * 获取最新价格
     * @param adId
     * @return
     */
    public String newPrice(Long adId){
        Advertisement advertisement = this.advertisementMapper.select(adId);
        if(advertisement == null){
            throw new AdvertisementBizException("不存在此条广告");
        }
        if(advertisement.getPriceType().intValue() != PriceTypeEnum.YJ.getValue()){
            throw new AdvertisementBizException("此条广告为固定价格");
        }
        Map<String, Map> allCache = FquoteCacheManager.getCacheObj();
        if(allCache == null){
            throw new AdvertisementBizException("获取不到第三方平台价格");
        }
        Map<String, String> priceMap = allCache.get(advertisement.getCoinType().toString());
        String price = priceMap.get(advertisement.getTradePlatform());
        if(StringUtils.isBlank(price)){
            throw new AdvertisementBizException("获取不到第三方平台价格");
        }
        CalculatePricedto pricedto = getPrice(price, advertisement.getCoinType(), String.valueOf(advertisement.getPremiumRate()), advertisement.getTradePlatform());

        return pricedto.getPrice();
    }
}
