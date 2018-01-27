package com.jucaifu.common.enums;

import java.util.Map;

import com.jucaifu.common.util.EnumHelper;

/**
 * EnumHttpStatusCode
 *
 * @author scofieldcai
 *         <p>
 *         Created by scofieldcai-dev on 15/9/2.
 *         <p>
 *         说明：提供给调试定位使用的枚举
 */
public enum EnumHttpStatusCode implements IEnum<Integer> {

//    1xx（临时响应）

    /**
     * The CODE_100.
     */
    CODE_100(100, "（继续） 请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。"),
    /**
     * The CODE_101.
     */
    CODE_101(101, "（切换协议） 请求者已要求服务器切换协议，服务器已确认并准备切换。"),

//    2xx （成功）

    /**
     * The CODE_200.
     */
    CODE_200(200, "（成功） 服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。"),
    /**
     * The CODE_201.
     */
    CODE_201(201, "（已创建） 请求成功并且服务器创建了新的资源。 "),
    /**
     * The CODE_202.
     */
    CODE_202(201, "（已创建） 请求成功并且服务器创建了新的资源。 "),
    /**
     * The CODE_203.
     */
    CODE_203(201, "（已创建） 请求成功并且服务器创建了新的资源。 "),
    /**
     * The CODE_204.
     */
    CODE_204(201, "（已创建） 请求成功并且服务器创建了新的资源。 "),
    /**
     * The CODE_205.
     */
    CODE_205(201, "（已创建） 请求成功并且服务器创建了新的资源。 "),
    /**
     * The CODE_206.
     */
    CODE_206(201, "（已创建） 请求成功并且服务器创建了新的资源。 "),


    /**
     * The CODE_300.
     */
//    3xx （重定向）
    CODE_300(300, "（多种选择） 针对请求，服务器可执行多种操作。 服务器可根据请求者 (user agent) 选择一项操作，或提供操作列表供请求者选择。"),
    /**
     * The CODE_301.
     */
    CODE_301(301, "（永久移动） 请求的网页已永久移动到新位置。 服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。"),
    /**
     * The CODE_302.
     */
    CODE_302(302, "（临时移动） 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。"),
    /**
     * The CODE_303.
     */
    CODE_303(303, "（查看其他位置） 请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。"),
    /**
     * The CODE_304.
     */
    CODE_304(304, "（未修改） 自从上次请求后，请求的网页未修改过。 服务器返回此响应时，不会返回网页内容。"),
    /**
     * The CODE_305.
     */
    CODE_305(305, "（使用代理） 请求者只能使用代理访问请求的网页。 如果服务器返回此响应，还表示请求者应使用代理。"),
    /**
     * The CODE_307.
     */
    CODE_307(307, "（临时重定向） 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。"),

    /**
     * The CODE_400.
     */
//    4xx（请求错误）这些状态代码表示请求可能出错，妨碍了服务器的处理。
    CODE_400(400, "（错误请求） 服务器不理解请求的语法"),
    /**
     * The CODE_401.
     */
    CODE_401(401, "（未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。"),
    /**
     * The CODE_403.
     */
    CODE_403(403, "（禁止） 服务器拒绝请求。"),
    /**
     * The CODE_404.
     */
    CODE_404(404, "（未找到） 服务器找不到请求的网页。"),
    /**
     * The CODE_405.
     */
    CODE_405(405, "（方法禁用） 禁用请求中指定的方法。"),
    /**
     * The CODE_406.
     */
    CODE_406(406, "（不接受） 无法使用请求的内容特性响应请求的网页。"),
    /**
     * The CODE_407.
     */
    CODE_407(407, "（需要代理授权） 此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。"),
    /**
     * The CODE_408.
     */
    CODE_408(408, "（请求超时） 服务器等候请求时发生超时。"),
    /**
     * The CODE_409.
     */
    CODE_409(409, "（冲突） 服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。"),
    /**
     * The CODE_410.
     */
    CODE_410(410, "（已删除） 如果请求的资源已永久删除，服务器就会返回此响应。"),
    /**
     * The CODE_411.
     */
    CODE_411(411, "（需要有效长度） 服务器不接受不含有效内容长度标头字段的请求。"),
    /**
     * The CODE_412.
     */
    CODE_412(412, "（未满足前提条件） 服务器未满足请求者在请求中设置的其中一个前提条件。"),
    /**
     * The CODE_413.
     */
    CODE_413(413, "（请求实体过大） 服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。"),
    /**
     * The CODE_414.
     */
    CODE_414(414, "（请求的 URI 过长） 请求的 URI（通常为网址）过长，服务器无法处理。"),
    /**
     * The CODE_415.
     */
    CODE_415(415, "（不支持的媒体类型） 请求的格式不受请求页面的支持。"),
    /**
     * The CODE_416.
     */
    CODE_416(416, "（请求范围不符合要求） 如果页面无法提供请求的范围，则服务器会返回此状态代码。"),
    /**
     * The CODE_417.
     */
    CODE_417(417, "（未满足期望值） 服务器未满足\"期望\"请求标头字段的要求。"),

    /**
     * The CODE_500.
     */
//    5xx（服务器错误)
//    这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。
    CODE_500(500, "（服务器内部错误）服务器遇到错误，无法完成请求"),
    /**
     * The CODE_501.
     */
    CODE_501(501, "（尚未实施） 服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。"),
    /**
     * The CODE_502.
     */
    CODE_502(502, "（错误网关） 服务器作为网关或代理，从上游服务器收到无效响应。"),
    /**
     * The CODE_503.
     */
    CODE_503(503, "（服务不可用)服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。"),
    /**
     * The CODE_504.
     */
    CODE_504(504, "（网关超时） 服务器作为网关或代理，但是没有及时从上游服务器收到请求。"),
    /**
     * The CODE_505.
     */
    CODE_505(505, "（HTTP 版本不受支持） 服务器不支持请求中所用的 HTTP 协议版本"),;

    /**
     * The Value.
     */
    private Integer value;
    /**
     * The Desc.
     */
    private String desc;

    /**
     * Instantiates a new Enum http status code.
     *
     * @param value the value
     * @param desc  the desc
     */
    EnumHttpStatusCode(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "EnumHttpStatusCode{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                '}';
    }


    private static Map<Integer, EnumHttpStatusCode> sMap;

    /**
     * Get by status code.
     *
     * @param statusCode the status code
     * @return the string
     */
    public static String getByStatusCode(Integer statusCode) {
        if (sMap == null) {
            sMap = EnumHelper.buildIEnumMap(EnumHttpStatusCode.class);
        }
        EnumHttpStatusCode enumHttpStatusCode = sMap.get(statusCode);
        if (enumHttpStatusCode != null) {
            return enumHttpStatusCode.toString();
        } else {
            return null;
        }
    }


}
