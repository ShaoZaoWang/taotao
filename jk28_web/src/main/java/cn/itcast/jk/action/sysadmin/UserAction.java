package cn.itcast.jk.action.sysadmin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.service.DeptService;
import cn.itcast.jk.service.RoleService;
import cn.itcast.jk.service.UserService;
import cn.itcast.jk.utils.Page;
/**
 * 部门管理Action
 * @author Administrator
 *
 */
public class UserAction extends BaseAction implements ModelDriven<User> {
	private User model = new User();
	public User getModel() {
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
	
	
	//注入UserService
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	private DeptService deptService;
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	/**
	 * 分页查询
	 */
	public String list() throws Exception {
		userService.findPage("from User", page, User.class, null);
		
		//设置分页的url地址
		page.setUrl("userAction_list");
		
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
		User dept = userService.get(User.class, model.getId());
		
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
		List<Dept> deptList = deptService.find("from Dept where state=1", Dept.class, null);
		//将查询的结果放入值栈中 ,它放在context区域中
		super.put("deptList", deptList);
		
		List<User> userList = userService.find("from User where state=1", User.class, null);
		super.put("userList", userList);
		//跳页面
		return "tocreate";
	}
	
	/**
	 * 保存
	 *     <s:select name="parent.id"
	 *     <input type="text" name="UserName" value=""/>
	 * model对象能接收
	 *      parent 
	 *           id
	 *      UserName
	 */
	public String insert() throws Exception {
		//1.调用业务方法，实现保存
		userService.saveOrUpdate(model);
		//跳页面
		return "alist";
	}
	
	
	/**
	 * 进入修改页面
	 */
	public String toupdate() throws Exception {
		//1.根据id,得到一个对象
		User obj = userService.get(User.class, model.getId());
		
		//2.将对象放入值栈中
		super.push(obj);
		
		//3.查询父部门
		List<Dept> deptList = deptService.find("from Dept where state=1", Dept.class, null);
		
		//4.将查询的结果放入值栈中 ,它放在context区域中
		super.put("deptList", deptList);
		
		//5.跳页面
		return "toupdate";
	}
	
	/**
	 * 修改
	 */
	public String update() throws Exception {
		//调用业务
		User obj = userService.get(User.class, model.getId());//根据id,得到一个数据库中保存的对象
		
		//2.设置修改的属性
		obj.setDept(model.getDept());
		obj.setUserName(model.getUserName());
		obj.setState(model.getState());
		
		
		userService.saveOrUpdate(obj);
		return "alist";
	}
	/*
	 * 删除
	 * <input type="checkbox" name="id" value="100"/>
	 * <input type="checkbox" name="id" value="3d00290a-1af0-4c28-853e-29fbf96a2722"/>
	 * ....
	 *model User类型
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
		userService.delete(User.class, ids);
		
		return "alist";
	}
	
	//进入角色分配页面
	public String torole() throws Exception {
		//1.根据id,得到对象
		User obj = userService.get(User.class, model.getId());
		//2.将对象保存到值栈中
		super.push(obj);
		
		//3.调用角色业务方法，得到角色列表
		List<Role> roleList = roleService.find("from Role ", Role.class, null);
		
		//4.将roleList放入值栈
		super.put("roleList", roleList);
		
		//5.得到当前用户的角色列表
		Set<Role> roleSet = obj.getRoles();
		StringBuilder sb = new StringBuilder();
		for(Role role :roleSet){
			sb.append(role.getName()).append(",");   //管理员,船运经理，
		}
		
		//6.当前用户的角色字符串放入值栈中
		super.put("roleStr", sb.toString());
		
		return "torole";
	}
	
	
	/**
	 * 实现角色分配
	 * <input type="hidden" name="id" value="58b537be-2eea-4dee-bc90-f73032476fd6"/>----model.id
	 * 
	 * <input type="checkbox" name="roleIds" value="4028a1c34ec2e5c8014ec2ebf8430001" class="input"
			
		>
		<input type="checkbox" name="roleIds" value="4028a1c34ec2e5c8014ec2ebf8430001" class="input"
			
		>

	 */
	public String role() throws Exception {
		//1.根据用户的id,得到对象
		User obj = userService.get(User.class, model.getId());
		
		//2.有哪些角色？只要遍历roleIds，就知道了
		Set<Role> roles = new HashSet<Role>();//当前选中的角色列表
		for(String id :roleIds){
			Role role = roleService.get(Role.class, id);
			
			roles.add(role);//向角色列表中添加一个新的角色
		}
		
		//3.设置用户与角色列表之间的关系
		obj.setRoles(roles);
		
		
		//4.保存到数据库表中
		userService.saveOrUpdate(obj);//影响的是用户角色的中间表
		
		//5.跳页面
		return "alist";
	}
	private String []roleIds;//保存角色的列表
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
}
