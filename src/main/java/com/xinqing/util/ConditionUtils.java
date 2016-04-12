package com.xinqing.util;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class ConditionUtils {
	
	public static void addEqContidions(List<Criterion> criterions, Map<String, Object> params, String key){
		if(params != null){
			if(params.get(key) != null && !"".equals(params.get(key))){
				Criterion criteria = Restrictions.eq(key, (String)params.get(key));
				criterions.add(criteria);
			}
		}
	}
	
	public static void addLikeContidions(List<Criterion> criterions, Map<String, Object> params, String key){
		if(params != null){
			if(params.get(key) != null && !"".equals(params.get(key))){
				Criterion criteria = Restrictions.ilike(key, "%" + (String)params.get(key) + "%");
				criterions.add(criteria);
			}
		}
	}
	
	

}
