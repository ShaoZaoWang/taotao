package cn.itcast.jk.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.service.ExtCproductService;
import cn.itcast.jk.service.ContractService;
import cn.itcast.jk.service.FactoryService;
import cn.itcast.jk.utils.Page;

/**
 * 
 * @author Administrator
 *
 */
public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct> {
	private ExtCproduct model = new ExtCproduct();

	public ExtCproduct getModel() {
		return model;
	}

	// 分页查询
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	// 注入ContractProductService
	private ExtCproductService extCproductService;
	public void setExtCproductService(ExtCproductService extCproductService) {
		this.extCproductService = extCproductService;
	}

	private FactoryService factoryService;
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}

	

	/**
	 * 进入新增页面 <a href=
	 * "/jk28_web/cargo/contractProductAction_tocreate?contract.id=4028d3db5662dfb4015662f0ecbc0001">
	 * [货物]</a>
	 */
	public String tocreate() throws Exception {
		// 1.调用业务方法,查询出生产附件的厂家
		String hql = "from Factory where ctype='附件' and state=1";
		List<Factory> factoryList = factoryService.find(hql, Factory.class, null);

		super.put("factoryList", factoryList);// 放入值栈中
		// 2.查询出当前货物下的附件列表
		extCproductService.findPage("from ExtCproduct where contractProduct.id=?", page, ExtCproduct.class,
				new String[] { model.getContractProduct().getId() });
		
		//设置page的url
		page.setUrl("extCproductAction_tocreate");
		
		//将page放入栈顶
		super.push(page);
		
		
		// 跳页面
		return "tocreate";
	}

	/**
	 * 保存 <s:select name="parent.id"
	 * <input type="text" name="deptName" value=""/> model对象能接收 parent id
	 * deptName
	 */
	public String insert() throws Exception {
		// 1.调用业务方法，实现保存
		extCproductService.saveOrUpdate(model);
		// 跳页面
		return tocreate();
	}

	/**
	 * 进入修改页面
	 */
	public String toupdate() throws Exception {
		// 1.根据id,得到一个对象
		ExtCproduct obj = extCproductService.get(ExtCproduct.class, model.getId());

		// 2.将对象放入值栈中
		super.push(obj);
		
		//3.加载生产厂家列表
		String hql = "from Factory where ctype='附件' and state=1";
		List<Factory> factoryList = factoryService.find(hql, Factory.class, null);
		super.put("factoryList", factoryList);// 放入值栈中

		// 5.跳页面
		return "toupdate";
	}

	/**
	 * 修改
	 */
	public String update() throws Exception {
		// 调用业务
		ExtCproduct obj = extCproductService.get(ExtCproduct.class, model.getId());// 根据id,得到一个数据库中保存的对象

		// 2.设置修改的属性
		obj.setFactory(model.getFactory());
		obj.setFactoryName(model.getFactoryName());
		obj.setProductNo(model.getProductNo());
		obj.setProductImage(model.getProductImage());
		obj.setCnumber(model.getCnumber());
	    obj.setAmount(model.getAmount());
	    obj.setPackingUnit(model.getPackingUnit());
	 
	    obj.setPrice(model.getPrice());
	    obj.setOrderNo(model.getOrderNo());
	    obj.setProductDesc(model.getProductDesc());   
	    obj.setProductRequest(model.getProductRequest());
	     
	
	
		  

		extCproductService.saveOrUpdate(obj);
		return tocreate();
	}

	/**
	 * 删除
	 * <a href="extCproductAction_delete.action
	 * ?id=4028d3db5662dfb4015663e169be0005
	 * &contractProduct.id=4028d3db5662dfb40156632e01490004
	 * &contractProduct.contract.id=4028d3db5662dfb4015662f0ecbc0001">[删除]</a>
	 * 
	 * 结论：如果操作子项时，最好同时传递他的所有祖宗
	 */
	public String delete() throws Exception {
		extCproductService.delete(ExtCproduct.class, model);

		return tocreate();
	}

}
