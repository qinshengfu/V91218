package com.fh.service.record.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.record.LuckyDrawRecManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 说明： 抽奖列表
 * 创建人：
 * 创建时间：2019-12-24
 * @version
 */
@Service("luckydrawrecService")
public class LuckyDrawRecService implements LuckyDrawRecManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("LuckyDrawRecMapper.save", pd);
	}

	/**新增记录和增加用户余额并更新抽奖次数
	 * @param pd
	 * @throws Exception
	 */
	@Transactional
	public void insertRec(PageData pd)throws Exception{
		// 创建中奖记录
		dao.save("LuckyDrawRecMapper.save", pd);
		// 更新抽奖次数

		// 增加用户余额

	}

	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("LuckyDrawRecMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("LuckyDrawRecMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("LuckyDrawRecMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("LuckyDrawRecMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LuckyDrawRecMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("LuckyDrawRecMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

