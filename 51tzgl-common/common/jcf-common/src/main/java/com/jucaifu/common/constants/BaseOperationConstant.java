package com.jucaifu.common.constants;

/**
 * 主要提供给权限控制基本操作使用
 * <p>
 * BaseOperationConstant
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/21.
 */
public interface BaseOperationConstant {

    /**
     * The SEPARATOR _ colon.
     */
    String SEPARATOR_COLON = ":";

    /**
     * The add.
     */
    String ADD = "add";

    String _ADD = SEPARATOR_COLON + ADD;

    /**
     * The delete.
     */
    String DELETE = "delete";
    String _DELETE = SEPARATOR_COLON + DELETE;

    /**
     * The update.
     */
    String UPDATE = "update";
    String _UPDATE = SEPARATOR_COLON + UPDATE;

    /**
     * The query.
     */
    String QUERY = "query";
    String _QUERY = SEPARATOR_COLON + QUERY;

    /**
     * The upload.
     */
    String UPLOAD = "upload";
    String _UPLOAD = SEPARATOR_COLON + UPLOAD;

}
