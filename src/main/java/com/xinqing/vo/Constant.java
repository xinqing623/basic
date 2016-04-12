package com.xinqing.vo;

public class Constant {
	
	public static final class OrderDirection{
		
		public static String DESC = "desc";
		
		public static String ASC = "asc";
	}

	public static final String ID_SPLIT = ",";
	
	public static final String USER = "user";
	
	public static final String USER_MENU = "user_menu";
	
	public static interface ArticleStatus{
		/**
		 * 正常
		 */
		String NORMAL = "1";
		/**
		 * 未审核
		 */
		String UN_CHECK = "2";
		/**
		 * 审核失败
		 */
		String CHECK_FAIL = "3";
		/**
		 * 已屏蔽
		 */
		String MASKED ="4";
	}
	
	public static interface HasBounds{
		
		String YES = "1";
		
		String NO = "0";
	}
	
	public static interface HasMorePage{
		String YES = "1";
		
		String NO = "0";
	}
	
	/**
	 * 字符集
	 */
	public static interface CHARSET {
		String GBK = "GBK";
		String UTF8 = "UTF-8";
	}
	
	public static interface IsView{
		
		String YES = "1";
		
		String NO = "0";
		
		String ING = "2";
	}
	
	public static interface IsSuccess{
		
		String NO = "0";
		
		String YES = "1";
		
		String ERR = "2";
		
		String TEST = "3";
	}
	
	public static interface IsCheck{
		String YES = "1";
		
		String NO = "0";
	}
	
	public static interface PageStatus{
		
		String NORMAL = "1";
		
		String DISABLE = "0";
	}
	
	public static interface IsAuto{
		
		String YES = "1";
		
		String NO = "0";
	}
	
	public static interface ChkResult{
		
		String SUCC = "1";
		
		String FAIL = "0";
	}
	
	public static interface HostStatus{
		
		String ACTV = "1";
		
		String DISB = "0";
	}
	
	public static interface DownStatus{
		String UNDO = "0";
		
		String DONE = "1";
		
		String FAIL = "2";
	}

}
