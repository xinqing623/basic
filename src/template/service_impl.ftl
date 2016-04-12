package ${basePackage}.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import ${basePackage}.entity.${className};
import ${basePackage}.mapping.${className}Mapper;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vo.PageInfo;
import ${basePackage}.vo.Pager;

@Service("${tableName}Service")
@Transactional
public class ${className}ServiceImpl implements ${className}Service{
	
	private static Logger logger = LoggerFactory.getLogger(${className}ServiceImpl.class);
	
	@Autowired
	private ${className}Mapper ${tableName}Mapper;

	@Override
	public ${className} get(String id) {
		return ${tableName}Mapper.selectByPK(id);
	}

	@Override
	public List<${className}> findAll() {
		return ${tableName}Mapper.select(null);
	}

	@Override
	public int save(${className} entity) {
		return ${tableName}Mapper.insert(entity);
	}

	@Override
	public int update(${className} entity) {
		return ${tableName}Mapper.update(entity);
	}

	@Override
	public int delete(String[] ids) {
		for(String id : ids){
			${tableName}Mapper.delete(id);
		}
		return ids.length;
	}


	@Override
	public Pager<${className}> findPage(PageInfo pageInfo, Map<String, Object> params) {
		Page<${className}> page = PageHelper.startPage(pageInfo.getPage(), pageInfo.getRows());
		if(pageInfo.getSort() != null){
			page.setOrderBy(pageInfo.getSort() + " " + pageInfo.getOrder());
		}
		List<${className}> list = ${tableName}Mapper.select(params);
		Pager<${className}> pager = new Pager<${className}>(pageInfo, (int)(page.getTotal()), page.getResult());
		return pager;
	}

	@Override
	public List<${className}> findList(Map<String, Object> params) {
		return ${tableName}Mapper.select(params);
	}

	@Override
	public List<${className}> findList(Map<String, Object> params, String orderName, String orderDirect) {
		Page<${className}> page = PageHelper.startPage(1, 1000000);
		if(orderName != null){
			page.setOrderBy(orderName + " " + orderDirect);
		}
		${tableName}Mapper.select(params);
		return page.getResult();
	}

		

}
