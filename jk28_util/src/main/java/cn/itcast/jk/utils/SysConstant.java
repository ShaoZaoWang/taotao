package cn.itcast.jk.utils;

import org.apache.log4j.Logger;

/**
 * @Description:
 * @Author:		���ǲ��� javaѧԺ	����.�ν�
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2014��10��31��
 */
/*
 * ϵͳȫ�ֳ���������
 */
public class SysConstant {
	private static Logger log = Logger.getLogger(SysConstant.class);

	public static String CURRENT_USER_INFO = "_CURRENT_USER";	//��ǰ�û�session name
	public static String USE_DATABASE = "MySql";				//ʹ�õ����ݿ� Oracle/SQLServer
	public static String USE_DATABASE_VER = "5.0";				//ʹ�õ����ݿ�汾 10g/2000

	public static String DEFAULT_PASS = "123456";				//Ĭ������
	public static int PAGE_SIZE = 10;							//��ҳʱһҳ��ʾ��������¼
}
