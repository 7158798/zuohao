package com.otc.api.console.utils;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Created by zhaiyz on 15-11-30.
 */
final class SessionUtils {

    private static final String USER_ID = "SESSION_USER_ID";

    private SessionUtils() {
    }

    /**
     * 通过token取得userId
     *
     * @param token 认证token
     * @return 员工主键
     */
    public static String getUserId(String token) {
        Subject subject = new Subject.Builder().sessionId(token).buildSubject();

        Session session = subject.getSession();

        return (String) session.getAttribute(USER_ID);
    }

    /**
     * 设置员工主键到session中
     *
     * @param session 　session
     * @param userId  员工主键
     */
    public static void addUserIdToSession(Session session, String userId) {
        session.setAttribute(USER_ID, userId);
    }

}
