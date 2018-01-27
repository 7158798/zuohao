package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by a123 on 17-5-4.
 */
public interface WebApiCommon extends ApiBasePathConstant {

    String PREFIX_WEB_API = CONSOLE_API+"/common";

    String UPLOAD_FILE_API = PREFIX_WEB_API+"/upload";

    String GET_UPLOAD_IMG_URL = PREFIX_WEB_API+"/upload/getImgUrl"+PATH_QUERY_JSON;

    String TEST_TOKEN_API = PREFIX_WEB_API + "/getTestToken" + PATH_QUERY_JSON;
}
