package com.fh.service.front.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.MemUser;
import com.fh.entity.Page;
import com.fh.service.front.AccountManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 说明： 前台用户表
 * 创建人：
 * 创建时间：2019-12-20
 */
@Service("accountService")
public class AccountService implements AccountManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 新增
     *
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd) throws Exception {
        dao.save("AccountMapper.save", pd);
    }

    /**
     * 删除
     *
     * @param pd
     * @throws Exception
     */
    public void delete(PageData pd) throws Exception {
        dao.delete("AccountMapper.delete", pd);
    }

    /**
     * 清空所有数据，保留顶点账号
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public void deleteAllData(PageData pd) throws Exception {
        dao.delete("AccountMapper.deleteAllData", pd);
    }

    /**
     * 修改
     *
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd) throws Exception {
        dao.update("AccountMapper.edit", pd);
    }

    /**
     * 更新推荐人数和团队人数
     *
     * @param pd
     * @throws Exception
     */
    @Override
    @Transactional
    public void updateTeamAmount(PageData pd) throws Exception {
        if ("10000".equals(pd.get("ACCOUNT_ID").toString())) {
            pd.put("RE_PATH", 10000);
        } else {
            pd.put("RE_PATH", pd.get("RE_PATH") + "," + pd.get("ACCOUNT_ID"));
        }
        dao.update("AccountMapper.updateReNumber", pd);
        dao.update("AccountMapper.updateTeamAmount", pd);
    }

    /**
     * 更新有效会员人数
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public void updatEmemberAmount(PageData pd) throws Exception {
        if ("10000".equals(pd.get("ACCOUNT_ID").toString())) {
            pd.put("RE_PATH", 10000);
        } else {
            pd.put("RE_PATH", pd.get("RE_PATH"));
        }
        dao.update("AccountMapper.updatEmemberAmount", pd);
    }

    /**
     * 更新团队业绩
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public void updateTeamSum(PageData pd) throws Exception {
        if ("10000".equals(pd.get("ACCOUNT_ID").toString())) {
            pd.put("RE_PATH", 10000);
        } else {
            pd.put("RE_PATH", pd.get("RE_PATH") + "," + pd.get("ACCOUNT_ID"));
        }
        dao.update("AccountMapper.updateTeamSum", pd);
    }

    /**
     * 增加动静态累积
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public void addAccumula(PageData pd) throws Exception {
        dao.update("AccountMapper.addAccumula", pd);
    }

    /**
     * 循环字段修改
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public void updateFor(PageData pd) throws Exception {
        dao.update("AccountMapper.updateFor", pd);
    }

    /**
     * 每天重置
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public void resetDaily(PageData pd) throws Exception {
        dao.update("AccountMapper.resetDaily", pd);
    }

    /**
     * 更新钱包余额
     *
     * @param pd
     * @throws Exception
     */
    @Override
    @Transactional
    public void updateMoney(PageData pd) throws Exception {
        if ("+".equals(pd.getString("tag"))) {
            dao.update("AccountMapper.addMoney", pd);
        } else {
            dao.update("AccountMapper.reduceMoney", pd);
        }
    }

    /**
     * 重置顶点账号
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public void resetAccount(PageData pd) throws Exception {
        dao.update("AccountMapper.resetAccount", pd);
    }

    /**
     * 列表
     *
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("AccountMapper.datalistPage", page);
    }

    /**
     * 列表(全部)
     *
     * @param pd
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("AccountMapper.listAll", pd);
    }

    /**
     * 列表(所有下级)
     *
     * @param pd
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PageData> listByDownUserId(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("AccountMapper.listByDownUserId", pd);
    }

    /**
     * 列表(所有上级)
     *
     * @param pd
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PageData> listByUpperUserId(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("AccountMapper.listByUpperUserId", pd);
    }

    /**
     * 列表(根据推荐人查询)
     *
     * @param pd
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PageData> listByRecommender(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("AccountMapper.listByRecommender", pd);
    }

    /**
     * 列表(通过for循环字段)
     *
     * @param pd
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PageData> listByFor(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("AccountMapper.listByFor", pd);
    }

    /**
     * 通过id获取数据
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("AccountMapper.findById", pd);
    }

    /**
     * 通过邀请码获取数据
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public PageData findByCode(PageData pd) throws Exception {
        return (PageData) dao.findForObject("AccountMapper.findByCode", pd);
    }

    /**
     * 查询直推人数
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public PageData getReCount(PageData pd) throws Exception {
        return (PageData) dao.findForObject("AccountMapper.getReCount", pd);
    }

    /**
     * 获取所有用户累积
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public PageData getAllCount(PageData pd) throws Exception {
        return (PageData) dao.findForObject("AccountMapper.getAllCount", pd);
    }

    /**
     * 通过邮箱获取数据
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public PageData findByEmail(PageData pd) throws Exception {
        return (PageData) dao.findForObject("AccountMapper.findByEmail", pd);
    }

    /**
     * 通过用户名获取数据
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public PageData findByUserName(PageData pd) throws Exception {
        return (PageData) dao.findForObject("AccountMapper.findByUserName", pd);
    }

    /**
     * 通过id获取数据，返回实体类
     *
     * @param pd
     * @throws Exception
     */
    @Override
    public MemUser findByIdReturnEntity(PageData pd) throws Exception {
        return (MemUser) dao.findForObject("AccountMapper.findByIdReturnEntity", pd);
    }

    /**
     * 批量删除
     *
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("AccountMapper.deleteAll", ArrayDATA_IDS);
    }

}

