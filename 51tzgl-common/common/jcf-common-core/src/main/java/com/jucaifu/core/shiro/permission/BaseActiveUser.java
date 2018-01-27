package com.jucaifu.core.shiro.permission;

import java.util.List;


/**
 * 说明：用户身份信息，存入session。
 * 由于tomcat将session会序列化在本地硬盘上，所以使用Serializable接口
 * <p>
 * The type Base active user.
 *
 * @param <MenuType>       the type parameter
 * @param <PermissionType> the type parameter
 */
public class BaseActiveUser<MenuType, PermissionType> implements java.io.Serializable {

    /**
     * 用户id（主键）
     */
    protected String uuid;

    /**
     * 数据库加密后的密码字符串
     */
    protected String dbPwd;

    /**
     * 数据库中密码加密哈希盐
     */
    protected String dbSalt;
    /**
     * 用户账号
     */
    protected String account;
    /**
     * 用户名称
     */
    protected String name;

    /**
     * 菜单列表
     */
    protected List<MenuType> menuList;
    /**
     * 权限列表
     */
    protected List<PermissionType> permissionList;


    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * Sets account.
     *
     * @param account the account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets menu list.
     *
     * @return the menu list
     */
    public List<MenuType> getMenuList() {
        return menuList;
    }

    /**
     * Sets menu list.
     *
     * @param menuList the menu list
     */
    public void setMenuList(List<MenuType> menuList) {
        this.menuList = menuList;
    }

    /**
     * Gets permission list.
     *
     * @return the permission list
     */
    public List<PermissionType> getPermissionList() {
        return permissionList;
    }

    /**
     * Sets permission list.
     *
     * @param permissionList the permission list
     */
    public void setPermissionList(List<PermissionType> permissionList) {
        this.permissionList = permissionList;
    }


    public String getDbSalt() {
        return dbSalt;
    }

    public String getDbPwd() {
        return dbPwd;
    }

    public void setDbPwd(String dbPwd) {
        this.dbPwd = dbPwd;
    }

    public void setDbSalt(String dbSalt) {
        this.dbSalt = dbSalt;
    }
}
