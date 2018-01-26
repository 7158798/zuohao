<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    request.setAttribute("isIndex", "1");
%>

<!doctype html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${requestScope.constant['webinfo'].ftitle }</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="description" content="${requestScope.constant['webinfo'].fdescription }"/>
    <meta name="keywords" content="${requestScope.constant['webinfo'].fkeywords }" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta name="format-detection" content="telephone=no" />
    <meta name="format-detection" content="email=no" />
    <link rel="stylesheet" href="${oss_url}/static/front/css/plugin/bootstrap.css" type="text/css"></link>
    <link rel="stylesheet" href="${oss_url}/static/front/css/comm/comm.css" type="text/css"></link>
    <script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.min.js"></script>
    <link rel="stylesheet" href="${oss_url}/static/front/css/user/login.css" type="text/css"></link>
    <link rel="stylesheet" href="${oss_url}/static/front/css/plugin/slick.css" type="text/css"></link>
    <link rel="stylesheet" href="${oss_url}/static/front/css/e-active/e-active.css?r=<%=new java.util.Date().getTime() %>" type="text/css"></link>

</head>
<body >
    <div class="active-top">
        <a href="#" class="active-to">
            <img src="${oss_url}/static/front/images/mobile/e-active/banner.png" alt="banner">
        </a>
    </div>
    <div class="ac-contribute">
        <a href="/mobile/apply.html" class="ac-click">点击报名</a>
    </div>
    <div class="clearfix">
        <div class="ac-detail">
            <img src="${oss_url}/static/front/images/mobile/e-active/image.png">
            <div class="ac-de-text">
                <p><span>Who is the Next?</span></p>
                <p>只要你对金融信息有读到的见解, <br>愿意发现和探索, <br>将你的作品发投稿我们,<br><span>千元现金大奖,实习机会</span>...统统都有!</p>
            </div>
        </div>
    </div>
    <div class="ac-conpec">
        <h class="ac-play">参赛方式</h>
        <div class="ac-article">参赛作品可用数字货币、金融创新、区块链、股票、宏观策略等任选一个主题，写一篇八百字以上走势分析的记叙
文或者议论文。</div>
    </div>
    <div class="ac-conpec">
        <h class="ac-play">活动时间</h>
        <div class="ac-article">
            1、投稿时间：2017年04月17日9：00-2017年04月21日18:00截止；
            <br>2、公布入围名单：2017年04月24日10：00；
            <br>3、投票时间：2017年04月25日9：00-2017年04月26日18：00；
            <br>4、公布获奖名单：2017年04月27日10:00；
            <br>5、奖品颁发时间：2017年05月05日。
        </div>
    </div>
    <div class="ac-conpec">
        <h class="ac-play">活动流程</h>
        <div class="ac-article">我们将所收到的优质文章发布在微信公众号上，并公开投票，活动结果以最终票数为准。</div>
    </div>
    <div class="ac-setPrize">
        <div class="ac-setTop">奖项设置</div>
        <div class="ac-setBot clearfix">
            <div class="ac-firstPrize">
                <div class="money">1000元现金</div>
                <div class="level">一等奖</div>
            </div>
            <div class="ac-firstPrize">
                <div class="money">500元现金</div>
                <div class="level">二等奖</div>
            </div>
            <div class="ac-firstPrize">
                <div class="money">200元现金</div>
                <div class="level">三等奖</div>
            </div>
            <div class="ac-firstPrize">
                <div class="money">100元现金</div>
                <div class="level">人气等奖</div>
            </div>
            <div class="ac-firstPrize">
                <div class="money">100元现金</div>
                <div class="level">创新奖</div>
            </div>
            <div class="ac-firstPrize">
                <div class="money">一枚莱特币</div>
                <div class="level">入围奖</div>
            </div>
        </div>
    </div>
    <div class="ac-des">*各奖项均有金融公司实习机会,兼职机会,表现优异者可留用.</div>
    <div class="ac-req">征文要求</div>
    <div class="ac-pad clearfix">
        <div class="ac-req-det">
            <img src="${oss_url}/static/front/images/mobile/e-active/bg_default.png">
            <div class="ac-con">
                <div class="num">1.来稿要求原创作品,严禁抄袭,一旦发现后果自负.</div>
                <div class="num">2.作品请注明作者姓名,年龄,院系,专业,联系方式及本人照片一张;</div>
                <div class="num">3.题材不限,字数八百字以上,标题用三号字,文章内容四号宋体;</div>
                <div class="num">4.语言流畅,内容充实,文字精炼;</div>
                <div class="num">5.参赛作品以数字货币,金融创新,区块链,股票,基金,宏观策略等,任选一个主题写一篇走势分析;</div>
                <div class="num">6.文章将统一发布至微信公众号51数字资产进行公开评选.</div>
            </div>
        </div>
    </div>
    <div class="ac-req">联系方式</div>
    <div class="ac-school">51数字资产：17301679118</div>
    <div class="ac-erCode">
        <img src="${oss_url}/static/front/images/mobile/e-active/erweiam.png">
    </div>
    <div class="ac-school" style="text-align: center;width:100%;">关注"51数字资产"官方微信公众号 <br>
        自定义菜单中找到活动入口
    </div>
    <div class="clearfix">
        <div class="ac-bottom ">
            <img src="${oss_url}/static/front/images/mobile/e-active/bottom.png">
            <div class="ac-text">本活动最终解释权归51数字资产所有 <br>请参赛者务必按照要求交稿</div>
        </div>
    </div>

    <script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js">

    </script><script type="text/javascript" src="${oss_url}/static/ssadmin/js/comm/common.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/language.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/language/language_cn.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/slick.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/mobile/e-active.js?r=<%=new java.util.Date().getTime() %>"></script>


</body>
</html>