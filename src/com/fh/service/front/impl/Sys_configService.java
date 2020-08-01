package com.fh.service.front.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.front.Sys_configManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 说明： 系统参数配置
 * 创建人：
 * 创建时间：2019-12-20
 * @version
 */
@Service("sys_configService")
public class Sys_configService implements Sys_configManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("Sys_configMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("Sys_configMapper.delete", pd);
	}

	/** 清空前台所有表
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void deleteAllTable(PageData pd)throws Exception{
		for (Object var : pd.keySet()) {
			dao.delete("Sys_configMapper.deleteAllTable", var);
		}

	}

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("Sys_configMapper.edit", pd);
	}

	/** 重置序列
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void reset_seq(PageData pd)throws Exception{
		dao.update("Sys_configMapper.reset_seq", pd);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Sys_configMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Sys_configMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Sys_configMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Sys_configMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

