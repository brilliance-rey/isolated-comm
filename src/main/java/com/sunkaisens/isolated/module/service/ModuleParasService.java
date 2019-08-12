package com.sunkaisens.isolated.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunkaisens.isolated.common.domain.QueryRequest;
import com.sunkaisens.isolated.module.domain.ModuleParas;

/**
 * @author Reneryan
 */
public interface ModuleParasService extends IService<ModuleParas> {

	 /**
     * 查询模块参数详情
     *
     * @param module module
     * @param queryRequest queryRequest
     * @return IPage
     */
    IPage<ModuleParas> findModuleParas(ModuleParas moduleParas, QueryRequest queryRequest);

}
