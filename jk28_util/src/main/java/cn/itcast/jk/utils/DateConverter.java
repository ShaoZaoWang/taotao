package cn.itcast.jk.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/*
 * ʵ���Զ������ڸ�ʽת������ʽΪ��yyyy-MM-dd
 * 
 * 	
 * Ϊ����springmvc-servlet.xml�����ò������ã�ֱ��controller������������
	<!-- ������ -->
	<bean id="annotationMethodHandlerAdapter" class="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter">
		<!-- ���ڸ�ʽת�� -->
        <property name="webBindingInitializer">
         <bean class="cn.itcast.jk.util.DateConverter"/>
        </property>
	</bean>
	
	
 */
public class DateConverter implements WebBindingInitializer {
	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
		binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(df, true));
	}
}