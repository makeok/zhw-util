package com.zhw.core.net;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.zhw.core.util.MyPropertyUtil;
/**
 * 发送邮件的测试程序
 * 
 * @author lwq
 * 
 */
public class MailUtil {
	private static String smtp_host;
	private static String smtp_user;
	private static String smtp_password;
	
	static{
		smtp_host = MyPropertyUtil.getValue("mail.properties", "smtp_host");
		smtp_user = MyPropertyUtil.getValue("mail.properties", "smtp_user");
		smtp_password = MyPropertyUtil.getValue("mail.properties", "smtp_password");
	}

    public static void sendEmail(String addr,String title,String content) throws MessagingException {
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        /*
         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtp_host);
//        props.put("mail.smtp.port", 25);    
        // 发件人的账号
        props.put("mail.user", smtp_user);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", smtp_password);

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人
        InternetAddress to = new InternetAddress(addr);
        message.setRecipient(RecipientType.TO, to);

        // 设置抄送
//        InternetAddress cc = new InternetAddress("luo_aaaaa@yeah.net");
//        message.setRecipient(RecipientType.CC, cc);

        // 设置密送，其他的收件人不能看到密送的邮件地址
//        InternetAddress bcc = new InternetAddress("aaaaa@163.com");
//        message.setRecipient(RecipientType.CC, bcc);

        // 设置邮件标题
        message.setSubject(title);

        // 设置邮件的内容体
//        message.setContent("<a href='http://www.fkjava.org'>测试的HTML邮件</a>", "text/html;charset=UTF-8");
        message.setContent(content, "text/html;charset=UTF-8");

        // 发送邮件
        Transport.send(message);

    }
    
}