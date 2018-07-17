package cn.itcast.jk.action.stat;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.dao.common.SqlDao;
import cn.itcast.jk.utils.file.FileUtil;

public class StatChartAction extends BaseAction {
	
	//最好要提供StatChartService接口及实现类
	private SqlDao sqlDao;//为了省事
	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}


	/**
	 * 新版amChart实现
	 */
	public String factorysale() throws Exception {
		//1.执行sql语句，得到统计结果
	    List<String> list = execSQL("select factory_id,sum(amount) samount from contract_product_c group by factory_id order by samount desc");
	    
	    //2.组织符合要求的json数据
	    StringBuilder sb = new StringBuilder();
	    sb.append("[");
	    /**
	     *      {
                    "country": "USA",
                    "visits": 4025,
                    "color": "#FF0F00"
                }
	     */
	    String colors[]={"#FF0F00","#FF6600","#FF9E01","#FCD202","#F8FF01","#B0DE09","#04D215","#0D52D1","#2A0CD0","#8A0CCF","#CD0D74","#754DEB"};
	    int j=0;
	    for(int i=0;i<list.size();i++){
	    	sb.append("{").append("\"factory\":\"").append(list.get(i)).append("\",")
	    	              .append("\"amount\":\"").append(list.get((++i))).append("\",")
	    	              .append("\"color\":\"").append(colors[j++]).append("\"")
	    	.append("}").append(",");
	    	if(j>=colors.length){
	    		j=0;
	    	}
	    }
	    sb.delete(sb.length()-1, sb.length());
	    
	    sb.append("]");
	    
	    //3.将json数据放入值栈中
	    super.put("result", sb.toString());
	    
	    //4.返回结果
		return "factorysale";
	}
	
	/**
	 * 厂家的销量排名(旧版amchart实现)
	 */
	/*public String factorysale() throws Exception {
		//1.执行sql语句，得到统计结果
	    List<String> list = execSQL("select factory_name,sum(amount) samount from contract_product_c group by factory_name order by samount desc");
	    
	    //2.组织符合要求的xml数据
	    String content = genPieDataSet(list);
	    
	    //3.将拼接好的字符串写入data.xml文件中
	    writeXML("stat\\chart\\factorysale\\data.xml",content);
		
		 //writeXML("stat\\chart\\factorysale\\data.xml",this.genPieDataSet(this.execSQL("select factory_name,sum(amount) samount from contract_product_c group by factory_name order by samount desc")));
	    
	    
	    //4.返回结果
		return "factorysale";
	}*/
	/**
	 * 产品销量的前15名
	 */
	public String productsale() throws Exception {
		//1.执行sql语句，得到统计结果
		List<String> list = execSQL("select product_no,sum(amount) samount from contract_product_c group by product_no order by samount desc limit 16");
		
		//2.组织符合要求的xml数据
		String content = genBarDataSet(list);
		
		//3.将拼接好的字符串写入data.xml文件中
		writeXML("stat\\chart\\productsale\\data.xml",content);
		
		//4.返回结果
		return "productsale";
	}
	/**
	 * 在线人数统计
	 * 表：LOGIN_LOG_P
	 *     它的数据来源于，在登录时，对于登录者的ip信息进行记录
	 *     
	 *  select a.a1, nvl(b.c,0)
		from 
		     (select * from online_info_t) a
		left join 
		      (select to_char(login_time,'HH24') a1, count(*) c from login_log_p group by  to_char(login_time,'HH24') order by a1)
		       b
		on (a.a1=b.a1)
		order by a.a1
	 */
	public String onlineinfo() throws Exception {
		//1.执行sql语句，得到统计结果
		List<String> list = execSQL("select a.a1, ifnull(b.c,0) from (select * from onlineinfo_t) a left join (select login_time,'HH24' a1, count(*) c from login_log_p group by  login_time,'HH24' order by a1) b on (a.a1=b.a1) order by a.a1");
		
		//2.组织符合要求的xml数据
		String content = genBarDataSet(list);
		
		//3.将拼接好的字符串写入data.xml文件中
		writeXML("stat\\chart\\onlineinfo\\data.xml",content);
		
		//writeXML("stat\\chart\\factorysale\\data.xml",this.genPieDataSet(this.execSQL("select factory_name,sum(amount) samount from contract_product_c group by factory_name order by samount desc")));
		
		
		//4.返回结果
		return "onlineinfo";
	}


	/**
	 * 执行sql
	 * @param sql
	 * @return
	 */
	private List<String> execSQL(String sql ) {
	    //1.执行sql语句，得到统计结果
	    List<String> list = sqlDao.executeSQL(sql);
		return list;
	}


	/**
	 * 生成柱状图的数据源
	 * @param list
	 * @return
	 */
	private String genBarDataSet(List<String> list) {
		StringBuilder sb = new StringBuilder();
	    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    sb.append("<chart><series>");
	    
	    int j=0;
	    for(int i=0;i<list.size();i++){
	    	sb.append("<value xid=\""+(j++)+"\">"+list.get(i)+"</value>");
	    	i++;
	    }
	    
	    sb.append("</series><graphs><graph gid=\"30\" color=\"#FFCC00\" gradient_fill_colors=\"#111111, #1A897C\">");
	    
	    
	    j=0;
	    for(int i=0;i<list.size();i++){
	    	i++;
	    	sb.append("<value xid=\""+(j++)+"\" description=\"\" url=\"\">"+list.get(i)+"</value>");
	    }
	    
	    sb.append("</graph></graphs>");
	    sb.append("<labels><label lid=\"0\"><x>0</x><y>20</y><rotate></rotate><width></width><align>center</align><text_color></text_color><text_size></text_size><text><![CDATA[<b>JavaEE28期产品销量排名</b>]]></text></label></labels>");
	    sb.append("</chart>");
		return sb.toString();
	}
	/**
	 * 生成饼图的数据源
	 * @param list
	 * @return
	 */
	private String genPieDataSet(List<String> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<pie>");
		
		for(int i=0;i<list.size();i++){
			sb.append("<slice title=\""+list.get(i)+"\" pull_out=\"true\">"+list.get(++i)+"</slice>");
		}
		
		sb.append("</pie>");
		return sb.toString();
	}


	/**
	 * 写入xml
	 * @param sb
	 * @throws FileNotFoundException
	 */
	private void writeXML(String fileName ,String content) throws FileNotFoundException {
		FileUtil fileUtil = new FileUtil();
	    String sPath = ServletActionContext.getServletContext().getRealPath("/");
	    fileUtil.createTxt(sPath, fileName, content, "UTF-8");
	}
}
