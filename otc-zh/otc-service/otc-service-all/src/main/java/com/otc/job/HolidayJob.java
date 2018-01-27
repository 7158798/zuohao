package com.otc.job;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.otc.core.cache.GeneralListCache;
import com.otc.core.cache.HolidayCacheManager;
import com.otc.pool.OTCBizPool;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 获取节假日期
 * Created by zygong on 17-3-8.
 */
@Component
public class HolidayJob {
    private static final String url = "http://www.easybots.cn/api/Holiday.php";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        @Scheduled(cron = "0 0 1 1 * ?")  // 每个月1号 1点执行
//        @Scheduled(cron = "30 * * * * ?")  // 每个月1号 1点执行
        public static void init() {
            String yearMonth = sdf.format(new Date());
            String s = HttpClientHelper.sendGetRequest(url + "?m=" + yearMonth, Boolean.FALSE);
            if (StringUtils.isBlank(s)) {
                return;
            }

            Map<String, Object> stringTMap = JsonHelper.jsonStr2MapObj(s, Object.class);
            if (stringTMap == null || stringTMap.isEmpty()) {
                return;
            }
            Map<String, String> map = (Map) stringTMap.get(yearMonth);
            if (map == null || map.isEmpty()) {
                return;
            }
            List<String> holidayList = new ArrayList<>();
            for (Map.Entry<String, String> m : map.entrySet()) {
                String key = m.getKey();
                holidayList.add(key);
            }

            HolidayCacheManager.deleteCache();

            GeneralListCache cache = new GeneralListCache();
            cache.setList(holidayList);

            boolean b = HolidayCacheManager.saveCache(cache);
            if (!b) {
                LOG.w(HolidayJob.class, "保存数据进缓存失败");
            }
            OTCBizPool.getInstance().holidayBiz.saveList(holidayList);
        }

    public static void main(String[] args) {
//        init();
    }
}
