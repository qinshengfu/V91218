package com.fh.service.record.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.record.ErcSellManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 说明： 法币交易之出售
 * 创建人：
 * 创建时间：2019-12-30
 */
@Service("ercsellService")
public class ErcSellService implements ErcSellManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 新增
     *
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd) throws Exception {
        dao.save("ErcSellMapper.save", pd);
    }

    /**
     * 删除
     *
     * @param pd
     * @throws Exception
     */
    public void delete(PageData pd) throws Exception {
        dao.delete("ErcSellMapper.delete", pd);
    }

    /**
     * 修改
     *
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd) throws Exception {
        dao.update("ErcSellMapper.edit", pd);
    }

    /**
     * 减少库存
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public void reduceSurplus(PageData pd) throws Exception {
        dao.update("ErcSellMapper.reduceSurplus", pd);
    }

    /**
     * 增加库存
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public void addSurplus(PageData pd) throws Exception {
        dao.update("ErcSellMapper.addSurplus", pd);
    }

    /**
     * 列表
     *
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("ErcSellMapper.datalistPage", page);
    }

    /**
     * 列表(全部)
     *
     * @param pd
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ErcSellMapper.listAll", pd);
    }

    /**
     * 通过id获取数据
     *
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ErcSellMapper.findById", pd);
    }

    /**
     * 批量删除
     *
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("ErcSellMapper.deleteAll", ArrayDATA_IDS);
    }

}

