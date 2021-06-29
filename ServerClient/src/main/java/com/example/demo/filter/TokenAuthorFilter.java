package com.example.demo.filter;

import com.alibaba.fastjson.JSON;
import com.example.demo.util.Message;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Component
@WebFilter(urlPatterns = { "/sys/*" }, filterName = "tokenAuthorFilter")
public class TokenAuthorFilter implements Filter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		// 设置允许跨域访问的域，*表示支持所有的来源
		rep.setHeader("Access-Control-Allow-Origin", "*");
        // 设置允许跨域访问的方法
		rep.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
		rep.setHeader("Access-Control-Max-Age", "3600");
		rep.setHeader("Access-Control-Allow-Headers", "x-requested-with,token");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		String token = req.getHeader("token");// header方式
		Message resultInfo = new Message();
		boolean isFilter = false;

		String method = ((HttpServletRequest) request).getMethod();
		if (method.equals("OPTIONS")) {
			rep.setStatus(HttpServletResponse.SC_OK);
		} else {
			if (null == token || token.isEmpty()) {
				resultInfo.setRetCode("01");
				resultInfo.setRetMsg("用户授权认证没有通过!客户端请求参数中无token信息");
			} else {
				
				Claims claims;
				try {
					claims = jwtUtil.verifyToken(token);
//					if(claims.getIssuer()!=null||claims.getSubject()!=null) {
//						resultInfo.setRetCode("01");
//						resultInfo.setRetMsg("用户无有效token信息");
//					}else {
						resultInfo.setRetCode("00");
						token = jwtUtil.generToken(claims.getId(), null, null);
						isFilter=true;
						rep.setHeader("token",token);
						Message message = new Message();
						message.setToken(token);
						req.setAttribute("message", message);
						
//					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
					logger.error("用户授权认证没有通过!客户端请求参数token信息无效");
					resultInfo.setRetCode("01");
					resultInfo.setRetMsg("用户授权认证没有通过!客户端请求参数token信息无效");
				}
			}
			if (!resultInfo.getRetCode().equals("00")) {// 验证失败
				PrintWriter writer = null;
				OutputStreamWriter osw = null;
				try {
					osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
					writer = new PrintWriter(osw, true);
					String jsonStr = JSON.toJSONString(resultInfo);
					writer.write(jsonStr);
					writer.flush();
					writer.close();
					osw.close();
				} catch (UnsupportedEncodingException e) {
					logger.error("过滤器返回信息失败:" + e.getMessage(), e);
				} catch (IOException e) {
					logger.error("过滤器返回信息失败:" + e.getMessage(), e);
				} finally {
					if (null != writer) {
						writer.close();
					}
					if (null != osw) {
						osw.close();
					}
				}
				return;
			}
			if (isFilter) {
				logger.info("token filter过滤ok!");
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
