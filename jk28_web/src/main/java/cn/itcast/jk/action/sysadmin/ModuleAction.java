package cn.itcast.jk.action.sysadmin;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.domain.Module;
import cn.itcast.jk.service.DeptService;
import cn.itcast.jk.service.ModuleService;
import cn.itcast.jk.utils.Page;
/**
 * 部门管理Action
 * @author Administrator
 *
 */
public class ModuleAction extends BaseAction implements ModelDriven<Module> {
	private Module model = new Module();
	public Module getModel() {
		return model;
	}
	
	//分页查询
	private Page page  = new Page();
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	
	//注入ModuleService
	private ModuleService moduleService;
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	
	/**
	 * 分页查询
	 */
	public String list() throws Exception {
		moduleService.findPage("from Module", page, Module.class, null);
		
		//设置分页的url地址
		page.setUrl("moduleAction_list");
		
		//将page对象压入栈顶
		super.push(page);
		
		
		return "list";
	}
	
	/**
	 * 查看
	 *     id=rterytrytrytr
	 * model对象
	 *      id属性：rterytrytrytr
	 */
	public String toview() throws Exception {
		//1.调用业务方法，根据id,得到对象
		Module dept = moduleService.get(Module.class, model.getId());
		
		//放入栈顶
		super.push(dept);
		
		//3.调页面
		return "toview";
	}
	
	/**
	 * 进入新增页面
	 */
	public String tocreate() throws Exception {
		//调用业务方法
		
		return "tocreate";
	}
	
	/**
	 * 保存
	 *     <s:select name="parent.id"
	 *     <input type="text" name="ModuleName" value=""/>
	 * model对象能接收
	 *      parent 
	 *           id
	 *      ModuleName
	 */
	public String insert() throws Exception {
		//1.调用业务方法，实现保存
		moduleService.saveOrUpdate(model);
		//跳页面
		return "alist";
	}
	
	
	/**
	 * 进入修改页面
	 */
	public String toupdate() throws Exception {
		//1.根据id,得到一个对象
		Module obj = moduleService.get(Module.class, model.getId());
		
		//2.将对象放入值栈中
		super.push(obj);
		
		
		//5.跳页面
		return "toupdate";
	}
	
	/**
	 * 修改
	 */
	public String update() throws Exception {
		//调用业务
		Module obj = moduleService.get(Module.class, model.getId());//根据id,得到一个数据库中保存的对象
		
		//2.设置修改的属性
		obj.setName(model.getName());
		obj.setLayerNum(model.getLayerNum());
		obj.setCpermission(model.getCpermission());
		obj.setCurl(model.getCurl());
		obj.setCtype(model.getCtype());
		obj.setState(model.getState());
		obj.setBelong(model.getBelong());
		obj.setCwhich(model.getCwhich());
		obj.setRemark(model.getRemark());
		obj.setOrderNo(model.getOrderNo());
		
		
		moduleService.saveOrUpdate(obj);
		return "alist";
	}
	/*
	 * 删除
	 * <input type="checkbox" name="id" value="100"/>
	 * <input type="checkbox" name="id" value="3d00290a-1af0-4c28-853e-29fbf96a2722"/>
	 * ....
	 *model Module类型
	 *    id:对String类型，struts2会采用“,”方式进行字符串连接
	 *        具有同名框的一组值如何封装数据？
	 *        服务端是String类型
	 *        		100, 3d00290a-1af0-4c28-853e-29fbf96a2722, 3d00290a-1af0-4c28-853e-29fbf96a2722
	 *    id：Integer，Float,Double,Date类型    struts2默认接收的最后一个值
	 *       例如，客户端给id传了三个值 id=2，id=3，id=4   服务器端只能接收最后一个值，即id=4
	 *       解决方法使用数组 Integer []id；
	 * */
	public String delete() throws Exception {
		String ids[] = model.getId().split(",");
		
		//调用业务方法，实现批量删除
		moduleService.delete(Module.class, ids);
		
		return "alist";
	}
}
