package jk28_web;



import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class JavaMailTest03 {
	
	
	public void main(String [] args) throws Exception{
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:application-mail.xml");
		
		JavaMailSender sender = (JavaMailSender)ac.getBean("mailSender");//得到邮件的发送对象，专用于发送
		
		//发送一个允许带图片，同时带附件的邮件
		MimeMessage message = sender.createMimeMessage();//创建一封允许带图片，同时带附件的邮件
		//为了更好的操作MimeMessage对象，借用一个工具类来操作
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		
		//通过工具类设置猪蹄，内容，图片，附件
		helper.setFrom("itheima14@163.com");
		helper.setTo("997837397@163.com");
		helper.setSubject("zheshi ");
		helper.setText("<html><head></head><body><hi>hello???</hi>"
				+"<a href=http://www.baidu.com>百度</a>"
				+"<img src=cid:image/></body></html>",true
				);
		//添加图片
		FileSystemResource resource = new FileSystemResource(new File("C:\\Users\\Administrator\\Desktop\\spring事物属性.jpg"));
		helper.addInline("image", resource);
		sender.send(message);
		
	}
}
