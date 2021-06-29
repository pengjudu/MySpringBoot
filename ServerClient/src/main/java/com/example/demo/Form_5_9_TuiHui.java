package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * 重要：联调测试时请仔细阅读注释！
 * 产品：资金结算接入系统<br>
 * 交易：退汇：<br>
 * 日期： 2018-02<br>
 */
@Controller
@RequestMapping("/api")
public class Form_5_9_TuiHui  {
	@Autowired
	private JavaMailSender javaMailSender;

	@ResponseBody
	@RequestMapping("/msg")
	public Object receiveMessage(HttpServletResponse response)
	{

		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setSubject("这是一封测试邮件");
			message.setFrom("m18703889961@163.com"); //发送邮件的人
			message.setTo("2641822201@qq.com"); //接受邮件的人
			message.setSentDate(new Date ());
			message.setText("这是测试邮件的正文");
			javaMailSender.send(message);
		}catch (Exception e){
			e.printStackTrace ();
		}

		return "OK";
	}




	}

