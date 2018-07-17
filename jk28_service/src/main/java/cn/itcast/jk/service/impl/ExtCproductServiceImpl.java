package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.service.ExtCproductService;
import cn.itcast.jk.utils.Page;
import cn.itcast.jk.utils.UtilFuns;

public class ExtCproductServiceImpl implements ExtCproductService {

	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public Page<ExtCproduct> findPage(String hql, Page<ExtCproduct> page, Class<ExtCproduct> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public void saveOrUpdate(ExtCproduct entity) {
		double amount = 0d;
		if(UtilFuns.isEmpty(entity.getId())){
			//新增
			if(UtilFuns.isNotEmpty(entity.getPrice())&& UtilFuns.isNotEmpty(entity.getCnumber())){
				amount= entity.getPrice()*entity.getCnumber();//货物总金额
				entity.setAmount(amount);
			}
			
			//修改购销合同的总金额 
			Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());//根据购销合同的id,得到购销合同对象
			contract.setTotalAmount(contract.getTotalAmount()+amount);
			
			//保存购销合同的总金额 
			baseDao.saveOrUpdate(contract);
			
		}else{
			//修改
			double oldAmount = entity.getAmount();//取出货物的原有总金额 
			if(UtilFuns.isNotEmpty(entity.getPrice())&& UtilFuns.isNotEmpty(entity.getCnumber())){
				amount= entity.getPrice()*entity.getCnumber();//货物总金额
				entity.setAmount(amount);
			}
			
			Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());//根据购销合同的id,得到购销合同对象
			contract.setTotalAmount(contract.getTotalAmount()-oldAmount+amount);
		}
		baseDao.saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<ExtCproduct> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<ExtCproduct> entityClass, Serializable id) {
		
		baseDao.deleteById(entityClass, id);//删除一个对象
	}

	public void delete(Class<ExtCproduct> entityClass, Serializable[] ids) {
		
		for(Serializable id :ids){
			this.deleteById(ExtCproduct.class,id);
		}
	}

	@Override
	public void delete(Class<ExtCproduct> entityClass, ExtCproduct model) {
		ExtCproduct extCproduct = baseDao.get(ExtCproduct.class, model.getId());//得到附件对象
		
		Contract contract = baseDao.get(Contract.class, model.getContractProduct().getContract().getId());//得到购销合同对象
		
		//修改购销合同的总金额 
		contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount());
		
		//保存总金额 
		baseDao.saveOrUpdate(contract);
		
		//删除附件
		baseDao.deleteById(ExtCproduct.class,model.getId());
		
	}

}
