<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${basePackage}.mapping.${className}Mapper">
	<resultMap id="BaseResultMap" type="${basePackage}.entity.${className}">
		<#list columnList as e>
		<result column="${e.columnName}" property="${e.fieldName}" ></result>
		</#list>
	</resultMap>
	<sql id="Base_Column_List">
		<#list columnList as e>
			<#if e_has_next> 
			${e.columnName},
			<#else>
			${e.columnName}
			</#if>			
		</#list>
	</sql>
	
	<select id="selectByPK" parameterType = "${pkDataTypeClass}" resultMap="BaseResultMap">
		select <include refid="Base_Column_List"></include>
		from ${table_name}
		where ${pkColumnName} = ${'#{'}${pkFieldName}${'}'};
	</select>
	
	<select id="select" parameterType = "HashMap" resultMap="BaseResultMap">
		select <include refid="Base_Column_List"></include>
		from ${table_name}
		where 1=1
		<#list columnList as e>
		<if test="${e.fieldName} != null and ${e.fieldName} != '' ">
			and ${e.columnName} = ${'#{'}${e.fieldName}${'}'}
		</if>
		</#list>
	</select>
	
	<insert id="insert" parameterType="${basePackage}.entity.${className}">
           insert into ${table_name}(<include refid="Base_Column_List"></include>) 
               values(
               <#list columnList as e>
				<#if e_has_next> 
					${'#{'}${e.fieldName}${'}'},
				<#else>
					${'#{'}${e.fieldName}${'}'}
				</#if>			
			   </#list>
               )
	</insert>
	
	<update id="update" parameterType="${basePackage}.entity.${className}">
           update ${table_name}
		   <set>
           <#list columnList as e>
	           <#if e_has_next> 
	           		<if test="${e.fieldName} != null">
	           		${e.columnName} = ${'#{'}${e.fieldName}${'}'},
	           		</if>
	           	<#else>
					<if test="${e.fieldName} != null">
	           		${e.columnName} = ${'#{'}${e.fieldName}${'}'}
	           		</if>
	           </#if>           
           </#list>
		   </set>
           where ${pkColumnName} = ${'#{'}${pkFieldName}${'}'}
   </update>
	
	<delete id="delete" parameterType="${pkDataTypeClass}">
		delete from ${table_name} where ${pkColumnName} = ${'#{'}${pkFieldName}${'}'};
	</delete>
	
</mapper>
