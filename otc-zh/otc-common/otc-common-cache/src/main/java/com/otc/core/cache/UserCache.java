package com.otc.core.cache;

import com.otc.core.pojo.PushDevicePo;
import com.otc.core.version.CacheVersion;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * UserCache
 *
 * @param <T> the type parameter
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/23.
 */
public class UserCache<T extends Serializable> implements Serializable {

    private String uid;

    private Integer version;

    private HashSet<String> tokenSet = new HashSet<>();

    /**
     * Gets push device map.
     *
     * @return the push device map
     */
    public HashMap<String, PushDevicePo> getPushDeviceMap() {
        return pushDeviceMap;
    }

    /**
     * Sets push device map.
     *
     * @param pushDeviceMap the push device map
     */
    public void setPushDeviceMap(HashMap<String, PushDevicePo> pushDeviceMap) {
        this.pushDeviceMap = pushDeviceMap;
    }

    private HashMap<String, PushDevicePo> pushDeviceMap = new HashMap<>();

    private T cacheObj;

    /**
     * 实例化一个空的用户缓存对象
     *
     * @param uid the uid
     * @return the console cache
     */
    public static UserCache newInstance(String uid) {
        UserCache userCache = new UserCache();
        userCache.setUid(uid);
        userCache.setVersion(CacheVersion.CURRENT_VERSION);
        return userCache;
    }

    /**
     * Bind push device.
     *
     * @param token      the token
     * @param deviceType the device type
     * @param deviceId   the device id
     * @return the boolean
     */
    public boolean bindPushDevice(String token, String deviceType, String deviceId) {

        boolean success = false;

        if (StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(deviceType) && StringUtils.isNotEmpty(deviceId)) {

            PushDevicePo po = new PushDevicePo();
            po.setDeviceId(deviceId);
            po.setDeviceType(deviceType);
            po.setToken(token);
            po.setUid(uid);

            pushDeviceMap.put(deviceId, po);

            success = true;
        }

        return success;
    }

    /**
     * Gets uid.
     *
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets uid.
     *
     * @param uid the uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Gets token set.
     *
     * @return the token set
     */
    public HashSet<String> getTokenSet() {
        if (tokenSet == null) {
            tokenSet = new HashSet<>();
        }
        return tokenSet;
    }

    /**
     * Sets token set.
     *
     * @param tokenSet the token set
     */
    public void setTokenSet(HashSet<String> tokenSet) {
        if (tokenSet == null) {
            this.tokenSet.clear();
        } else {
            this.tokenSet = tokenSet;
        }
    }

    /**
     * Gets cache obj.
     *
     * @return the cache obj
     */
    public T getCacheObj() {
        return cacheObj;
    }

    /**
     * Sets cache obj.
     *
     * @param cacheObj the cache obj
     */
    public void setCacheObj(T cacheObj) {
        this.cacheObj = cacheObj;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
