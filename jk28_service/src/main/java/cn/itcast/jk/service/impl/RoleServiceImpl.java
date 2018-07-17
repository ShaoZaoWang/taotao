package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.service.RoleService;
import cn.itcast.jk.utils.Page;
import cn.itcast.jk.utils.UtilFuns;

public class RoleServiceImpl implements RoleService {

	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<Role> find(String hql, Class<Role> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	public Role get(Class<Role> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public Page<Role> findPage(String hql, Page<Role> page, Class<Role> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public void saveOrUpdate(Role entity) {
		if(UtilFuns.isEmpty(entity.getId())){
			
		}
		baseDao.saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<Role> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<Role> entityClass, Serializable id) {
		
		baseDao.deleteById(entityClass, id);//删除一个对象
	}

	public void delete(Class<Role> entityClass, Serializable[] ids) {
		
		for(Serializable id : ids){
			this.deleteById(Role.class, id);
		}
	}

}
