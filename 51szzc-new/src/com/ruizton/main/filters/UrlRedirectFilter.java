package com.ruizton.main.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruizton.main.Enum.UserStatusEnum;
import com.ruizton.main.model.Fuser;
/**
 * 请求重定向
 * **/
public class UrlRedirectFilter implements Filter {
	public void init(FilterConfig arg0) throws ServletException {}
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response ;
		
		String uri = req.getRequestURI().toLowerCase().trim() ;
		
		//不接受任何jsp请求
		if(uri.endsWith(".jsp")){
			return ;
		}
		
		//只拦截.html结尾的请求
		if(!uri.endsWith(".html")){
			chain.doFilter(request, response) ;
			return ;
		}

		///////////////////////////////////////////////////////////////////////////////
		if(	uri.startsWith("/index.html")//首页
				||uri.startsWith("/app/article.html")
				||uri.startsWith("/appApi.html")
				||uri.startsWith("/quickpay/qrpay_result.html")//app apiiiiii
				||uri.startsWith("/appapi.html")//app apiiiiii
				||uri.startsWith("/qqLogin")//qq
				||uri.startsWith("/link/wx/callback")//qq
				||uri.startsWith("/link/qq/call")//qq
				||uri.startsWith("/error/")//error
				||uri.startsWith("/api/")//api
				||uri.startsWith("/data/ticker.html")
				||uri.startsWith("/user/sendfindpasswordmsg")//api
				||uri.startsWith("/user/sendregmsg")//api
				||uri.startsWith("/json/findpassword")//api
				||uri.startsWith("/line/period-btcdefault.html")
				||uri.startsWith("/data/depth-btcdefault.html")
				||uri.startsWith("/data/stock-btcdefault.html")
				||uri.startsWith("/index_chart.html")
			||uri.startsWith("/user/login")//登陆
			||uri.startsWith("/user/logout")//退出
			||uri.startsWith("/user/reg")//注册
			||uri.startsWith("/app/reg.html")//注册
			||uri.startsWith("/app/suc.html")//注册
			||uri.startsWith("/real/")//行情
			||uri.startsWith("/market")//行情
				||uri.startsWith("/market_new")
			||uri.startsWith("/block/")
			||uri.startsWith("/kline/")//行情
			||uri.startsWith("/json.html")//行情
			||uri.startsWith("/json/")//行情
			||uri.startsWith("/validate/")//邮件激活,找回密码
			||uri.startsWith("/about/")//文章管理
			||(uri.startsWith("/crowd/index"))
			||uri.startsWith("/service/")//文章管理
	        ||uri.startsWith("/user/uploadfile")
			||uri.startsWith("/invite/invite")
			||uri.startsWith("/invite/invite")
			||uri.startsWith("/mobile/index")
			||uri.startsWith("/support/index")
			||uri.startsWith("/mobile/e-active")
			||uri.startsWith("/mobile/apply")
			||uri.startsWith("/app/")
//			||uri.startsWith("/trade/coin.html")//文章管理
//			||uri.startsWith("/trade/entrustlog.html")//文章管理
//			||uri.startsWith("/trade/entrustinfo.html")//文章管理
//			||uri.startsWith("/appcenter/index")//文章管理

			
			||uri.startsWith("/user/sendMailCode".toLowerCase())//注册邮件
			||uri.startsWith("/user/sendMsg".toLowerCase())//注册短信
				|| uri.startsWith("/new_user/register")  //后台新用户注册
				|| uri.startsWith("/common/upload")  //上传
				|| uri.startsWith("/common/sendmsg.html")
				|| uri.startsWith("/download.html")  //下载
				|| uri.startsWith("/articledetail.html")  //资讯详情、分享
				|| uri.startsWith("/quickpay/alipay_notify.html")  //异步通知
				|| uri.startsWith("/quickpay/alipay_result.html")  //回调通知
				|| uri.startsWith("/quickpay/app_alipay_notify.html")   //异步回调通知
				|| uri.startsWith("/language.html")  //语言切换
				|| uri.startsWith("/getmessage.html")  //根据key获取对应语言的内容
				|| uri.startsWith("/i18npro.html")  //读取某个语言的所有配置
				|| uri.startsWith("/mobile/getmarketdata.html")
				|| uri.startsWith("/user/checkQuestionVal".toLowerCase())  //找回密码时，校验安全问题
				|| uri.startsWith("/external/")   //外部用户操作
				|| uri.startsWith("/api/trade/depth.html")
				|| uri.startsWith("/eassycontest/save.html")
				||uri.startsWith("/trade/depth.html")
				|| uri.startsWith("/account/email/withdrawvirtual") //系统自动提币，邮件确认
			){
			chain.doFilter(request, response) ;
			return ;
		}else{
			
			

			if(uri.startsWith("/ssadmin/")){
				//后台
				if(uri.startsWith("/ssadmin/2bcf8d4e-e2aa-11e6-bddf-005056ab18e8.html")
						||uri.startsWith("/ssadmin/95afee23-e2ab-11e6-bddf-005056ab18e8.html")
						|| req.getSession().getAttribute("login_admin")!=null){
					chain.doFilter(request, response) ;
				}else{
					resp.sendRedirect("/") ;
				}
				return ;
			}else{
				
				Object login_user = req.getSession().getAttribute("login_user") ;
				if(login_user==null){
					resp.sendRedirect("/user/login.html?type=1&forwardUrl="+req.getRequestURI().trim()) ;
					return ;
				}else{

                    if( ((Fuser)login_user).getFpostRealValidate() && ((Fuser)login_user).getFstatus() == UserStatusEnum.NORMAL_VALUE){
                        //提交身份认证信息了 并且帐号状态为正常状态
                        chain.doFilter(request, response) ;
                        return ;
                    }else{
                        if(uri.startsWith("/user/realCertification.html".toLowerCase())
                                ||uri.startsWith("/user/validateidentity.html")
                                ||uri.startsWith("/user/validategoup.html")
								||(uri.startsWith("/user/security.html") && ((Fuser)login_user).getFstatus() == UserStatusEnum.NORMAL_VALUE)
                                ){
                            chain.doFilter(request, response) ;
                        }else{
                            resp.sendRedirect("/user/realCertification.html") ;
                        }
                        return ;
                    }

                }
				
			}
			
			
		}
		
	}


}
