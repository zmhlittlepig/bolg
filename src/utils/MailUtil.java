package utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * 邮箱类工具
 * @author sudada
 *
 */

public class MailUtil {
	
	/**
	 * 
	 * @param to:发送给谁
	 * @param text:发送内容
	 * @throws MessagingException 
	 * @throws AddressException 
	 */

	public static void send_mail(String to, String text) throws MessagingException {
		
		//创建连接对象 连接到邮件服务器
		Properties properties = new Properties();
		//设置发送邮件的基本参数
        //发送邮件服务器
		properties.put("mail.smtp.host", "smtp.163.com");
        //发送端口
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
		
        //设置发送邮件的账号和密码
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //两个参数分别是发送邮件的账户和密码
                return new PasswordAuthentication("m18296160406@163.com","zmh19960719");
            }
        });

        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        message.setFrom(new InternetAddress("m18296160406@163.com"));
        //设置收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
        //设置主题
        message.setSubject("请在验证码框输入验证码");
        //设置邮件正文  第二个参数是邮件发送的类型
        message.setContent(text,"text/html;charset=UTF-8");
        //发送一封邮件
        Transport.send(message);
       // System.out.println("发送成功");
    
	}
	
}
