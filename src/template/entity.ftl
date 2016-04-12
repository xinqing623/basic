package ${basePackage}.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "${table_name}")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ${className} implements java.io.Serializable {

	<#list columnList as e>
	private ${e.dataTypeClass} ${e.fieldName};
	</#list>

	public ${className}(){}
	
	<#list columnList as e>
	public ${e.dataTypeClass} get${e.upperFieldName}(){
		return ${e.fieldName};
	}
	
	public void set${e.upperFieldName}(${e.dataTypeClass} ${e.fieldName}){
		this.${e.fieldName} = ${e.fieldName};
	}
	</#list>
		

}
