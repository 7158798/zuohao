package com.base.facade.laud.service;


import com.base.facade.laud.pojo.cond.LaudCond;
import com.base.facade.laud.pojo.po.Laud;
import com.base.spi.UserLockService;

/**
 * Created by lx on 16-11-11.
 */
public interface LaudFacade {

    /**
     * Gets laud by condition.
     *
     * @param cond the cond
     * @return the laud by condition
     */
    Laud getLaudByCondition(LaudCond cond);

    /**
     * Gets laud by condition.
     *
     * @param relationId the relation id
     * @param type       the type
     * @param userId     the user id
     * @param ipAddress  the ip address
     * @param macAddress the mac address
     * @return the laud by condition
     */
    Laud getLaudByCondition(Long relationId, String type, Long userId, String ipAddress, String macAddress);

    /**
     * Cancel laud boolean.
     *
     * @param laud the laud
     * @return the boolean
     */
    boolean cancelLaud(Laud laud);

    /**
     * Save laud boolean.
     *
     * @param laud the laud
     * @return the boolean
     */
    boolean saveLaud(Laud laud);

    /**
     * Merge laud.
     *
     * @param sourcesUserId the sources user id
     * @param targetUserId  the target user id
     */
    void mergeLaud(Long sourcesUserId, Long targetUserId);
}
