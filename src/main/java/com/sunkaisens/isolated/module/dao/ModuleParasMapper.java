package com.sunkaisens.isolated.module.dao;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunkaisens.isolated.module.domain.ModuleParas;

/**
 * @author Reneryan
 */
public interface ModuleParasMapper extends BaseMapper<ModuleParas> {

	/**   
	 * @param page
	 * @param moduleParas
	 * @return
	 * @return IPage<ModuleParas>
	 */
	IPage<ModuleParas> findModuleParas(Page<ModuleParas> page, @Param("moduleParas") ModuleParas moduleParas);

}
