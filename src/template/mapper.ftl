package ${basePackage}.mapping;

import ${basePackage}.entity.${className};

public interface ${className}Mapper extends BaseMapper<${className}>{
	
	public ${className} selectByPK(${pkDataTypeClass} ${pkFieldName});

	public int insert(${className} entity);
	
	public int update(${className} entity);
	
	public int delete(${pkDataTypeClass} ${pkFieldName});
	
}
