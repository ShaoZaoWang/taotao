package cn.itcast.jk.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.service.DeptService;
import cn.itcast.jk.service.ContractService;
import cn.itcast.jk.utils.Page;
import cn.itcast.jk.utils.SysConstant;
/**
 * 
 * @author Administrator
 *
 */
public class ContractAction extends BaseAction implements ModelDriven<Contract> {
	private Contract model = new Contract();
	public Contract getModel() {
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
	
	
	//注入ContractService
	private ContractService contractService;
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	
	/**
	 * 分页查询
	 */
	public String list() throws Exception {
		String hql = "from Contract where 1=1 ";
		//2,得到当前登录的用户信息
		User user = super.getCurUser();
		//3,根据用户的级别，进行判断，从而得到这个用户可以查看什么样的数据
		if(user.getUserinfo().getDegree()==4){
			//员工
			hql+=" and createBy='"+user.getId()+"'";
		}else if(user.getUserinfo().getDegree()==3){
			//部门经理
			hql+=" and createDept='"+user.getDept().getId()+"'";
		}else if(user.getUserinfo().getDegree()==2){
			//管理本部门及下属部门
		
		}else if(user.getUserinfo().getDegree()==1){
			//跨部门跨人员
			
		}else if(user.getUserinfo().getDegree()==0){
			//总经理
		}
		contractService.findPage(hql, page, Contract.class, null);
		
		//设置分页的url地址
		page.setUrl("contractAction_list");
		
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
		Contract dept = contractService.get(Contract.class, model.getId());
		
		//放入栈顶
		super.push(dept);
		
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
		//1，得到当前登录的用户对象
		User user = super.getCurUser();
		//2,设置创建者的id
		model.setCreateBy(user.getId());
		
		//3,设置创建者所在部门id
		model.setCreateDept(user.getDept().getId());
		//1.调用业务方法，实现保存
		contractService.saveOrUpdate(model);
		//跳页面
		return "alist";
	}
	
	
	/**
	 * 进入修改页面
	 */
	public String toupdate() throws Exception {
		//1.根据id,得到一个对象
		Contract obj = contractService.get(Contract.class, model.getId());
		
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
		Contract obj = contractService.get(Contract.class, model.getId());//根据id,得到一个数据库中保存的对象
		
		//2.设置修改的属性
		obj.setCustomName(model.getCustomName());
		obj.setPrintStyle(model.getPrintStyle());
		obj.setContractNo(model.getContractNo());
		obj.setOfferor(model.getOfferor());
		obj.setInputBy(model.getInputBy());
		obj.setCheckBy(model.getCheckBy());
		obj.setInspector(model.getInspector());
		obj.setSigningDate(model.getSigningDate());
		obj.setImportNum(model.getImportNum());
		obj.setShipTime(model.getShipTime());
		obj.setTradeTerms(model.getTradeTerms());
		obj.setDeliveryPeriod(model.getDeliveryPeriod());
        obj.setCrequest(model.getCrequest());
        obj.setRemark(model.getRemark());
        
		contractService.saveOrUpdate(obj);
		return "alist";
	}
	
	/**
	 * 删除
	 */
	public String delete() throws Exception {
		String ids[] = model.getId().split(", ");
		
		//调用业务方法，实现批量删除
		contractService.delete(Contract.class, ids);
		
		
		return "alist";
	}
	
	/**
	 * 提交
	 */
	public String submit() throws Exception {
		String ids []  = model.getId().split(", ");
		
		//2.遍历ids,并加载出每个购销合同对象，再修改购销合同的状态
		contractService.changeState(ids, 1);
		return "alist";
	}
	
	/**
	 * 取消
	 */
	public String cancel() throws Exception {
		String ids []  = model.getId().split(", ");
		
		//2.遍历ids,并加载出每个购销合同对象，再修改购销合同的状态
		contractService.changeState(ids, 0);
		return "alist";
	}

}
