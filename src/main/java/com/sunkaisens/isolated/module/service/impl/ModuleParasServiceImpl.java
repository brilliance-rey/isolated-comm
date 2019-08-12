package com.sunkaisens.isolated.module.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunkaisens.isolated.common.domain.QueryRequest;
import com.sunkaisens.isolated.common.domain.SysConstant;
import com.sunkaisens.isolated.common.utils.SortUtil;
import com.sunkaisens.isolated.module.dao.ModuleParasMapper;
import com.sunkaisens.isolated.module.domain.ModuleParas;
import com.sunkaisens.isolated.module.service.ModuleParasService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Reneryan
 */
@Slf4j
@Service("moduleParasService")
public class ModuleParasServiceImpl extends ServiceImpl<ModuleParasMapper, ModuleParas> implements ModuleParasService {

	/**   
	 * <p>Title: findModuleParas</p>   
	 * <p>Description: </p>   
	 * @param moduleParas
	 * @param queryRequest
	 * @return   
	 * @see com.sunkaisens.isolated.module.service.ModuleParasService#findModuleParas(com.sunkaisens.isolated.system.domain.User, com.sunkaisens.isolated.common.domain.QueryRequest)   
	 */
	@Override
	public IPage<ModuleParas> findModuleParas(ModuleParas moduleParas, QueryRequest request) {
		try {
            Page<ModuleParas> page = new Page<>();
            SortUtil.handlePageSort(request, page, "paraId", SysConstant.ORDER_ASC, false);
            return this.baseMapper.findModuleParas(page, moduleParas);
        } catch (Exception e) {
            log.error("查询模块参数异常", e);
            return null;
        }
	}

}
