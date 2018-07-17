package cn.itcast.jk.action.sysadmin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Module;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.exception.SysException;
import cn.itcast.jk.service.ModuleService;
import cn.itcast.jk.service.RoleService;
import cn.itcast.jk.utils.Page;
/**
 * 部门管理的Action
 * @author Administrator
 *
 */
public class RoleAction extends BaseAction implements ModelDriven<Role> {
	private Role model = new Role();
	public Role getModel() {
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
	
	
	//注入RoleService
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	private ModuleService moduleService;
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	/**
	 * 分页查询
	 */
	public String list() throws Exception {
		roleService.findPage("from Role", page, Role.class, null);
		
		//设置分页的url地址
		page.setUrl("roleAction_list");
		
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
		try {
			//1.调用业务方法，根据id,得到对象
			Role dept = roleService.get(Role.class, model.getId());
			
			//放入栈顶
			super.push(dept);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysException("请先选中再操作！");
		}
		
		//3.跳页面
		return "toview";
	}
	
	/**
	 * 进入新增页面
	 */
	public String tocreate() throws Exception {
		//调用业务方法
	
		//跳页面
		return "tocreate";
	}
	
	/**
	 * 保存
	 *     <s:select name="parent.id"
	 *     <input type="text" name="deptName" value=""/>
	 * model对象能接收
	 *      parent 
	 *           id
	 *      deptName
	 */
	public String insert() throws Exception {
		//1.调用业务方法，实现保存
		roleService.saveOrUpdate(model);
		//跳页面
		return "alist";
	}
	
	
	/**
	 * 进入修改页面
	 */
	public String toupdate() throws Exception {
		//1.根据id,得到一个对象
		Role obj = roleService.get(Role.class, model.getId());
		
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
		Role obj = roleService.get(Role.class, model.getId());//根据id,得到一个数据库中保存的对象
		
		//2.设置修改的属性
		obj.setName(model.getName());
		obj.setRemark(model.getRemark());
  
		
		roleService.saveOrUpdate(obj);
		return "alist";
	}
	
	/**
	 * 删除
	 * <input type="checkbox" name="id" value="100"/>
	 * <input type="checkbox" name="id" value="3d00290a-1af0-4c28-853e-29fbf96a2722"/>
	 * .....
	 * model
	 *    id:String类型
	 *       具有同名框的一组值如何封装数据？
	 *       如何服务端是String类型：
	 *                       100, 3d00290a-1af0-4c28-853e-29fbf96a2722, 3d00290a-1af0-4c28-853e-29fbf96a2722
	 *                       
	 *    id:Integer,Float,Double.Date类型                  id=100               id=200        id=300  
	 *    id=300
	 *    Integer []id;  {100,200,300}
	 *        
	 *                       
	 */
	public String delete() throws Exception {
		String ids[] = model.getId().split(", ");
		
		//调用业务方法，实现批量删除
		roleService.delete(Role.class, ids);
		
		
		return "alist";
	}
	
	/**
	 * 进入模块分配页面
	 * 
	 */
	public String tomodule() throws Exception {
		//1.根据角色id,得到角色对象
		Role obj = roleService.get(Role.class, model.getId());
		
		//2.保存值栈中
		super.push(obj);
		
		//跳页面
		return "tomodule";
	}
	
	/**
	 * 为了使用 zTree树，就要组织好zTree树所使用的json数据
	 * json数据结构如下：
	 * [{"id":"模块的id","pId":"父模块id","name":"模块名","checked":"true|false"},{"id":"模块的id","pId":"父模块id","name":"模块名","checked":"true|false"}]
	 * 
	 * 常用的json插件有哪些？
	 * json-lib    fastjson     struts-json-plugin-xxx.jar,手动拼接
	 * 
	 * 如何输出?
	 * 借助于response对象输出数据
	 */
	public String roleModuleJsonStr() throws Exception{
		//1.根据角色id,得到角色对象
		Role role = roleService.get(Role.class, model.getId());
		
		//2.通过对象导航方式，加载出当前角色的模块列表
		Set<Module> moduleSet = role.getModules();
		
		//3.加载出所有的模块列表
		List<Module> moduleList = moduleService.find("from Module", Module.class, null);
		int size=moduleList.size();
		//4.组织json串
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		for(Module module :moduleList){
			size--;
			sb.append("{\"id\":\"").append(module.getId());
			sb.append("\",\"pId\":\"").append(module.getParentId());
			sb.append("\",\"name\":\"").append(module.getName());
			sb.append("\",\"checked\":\"");
			if(moduleSet.contains(module)){
				sb.append("true");
			}else{
				sb.append("false");
			}
			sb.append("\"}");
			
			if(size>0){
				sb.append(",");
			}
		}
		
		
		
		sb.append("]");
		
		//5.得到response对象
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		//6.使用 response对象输出json串
		response.getWriter().write(sb.toString());
		//7.返回NONE
		return NONE;
		
	}

	
	/**
	 * 保存当前角色的模块列表
	 * 	<input type="hidden" name="id" value="${id}"/>
	    <input type="hidden" id="moduleIds" name="moduleIds" value="" />
	 * 
	 */
	public String module() throws Exception {
		//1.哪个角色？
		Role role = roleService.get(Role.class, model.getId());
		//2.选中的模块有哪些？
		String ids [] = moduleIds.split(",");
		
		//加载出这些模块列表
		Set<Module> moduleSet = new HashSet<Module>();
		if(ids!=null && ids.length>0){
			for(String id :ids){
				moduleSet.add(moduleService.get(Module.class, id));//添加选中的模块，放到模块列表中
			}
		}
		
		//3.实现角色分配新的模块
		role.setModules(moduleSet);
		
		//4.保存结果
		roleService.saveOrUpdate(role);
		
		//5.跳页面
		return "alist";
	}
	private String moduleIds;
	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}
	
}
