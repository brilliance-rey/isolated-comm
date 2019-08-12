package com.sunkaisens.isolated.module.controller;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunkaisens.isolated.common.controller.BaseController;
import com.sunkaisens.isolated.common.domain.QueryRequest;
import com.sunkaisens.isolated.common.domain.RetrueCode;
import com.sunkaisens.isolated.common.domain.SunkResponse;
import com.sunkaisens.isolated.common.exception.SysInnerException;
import com.sunkaisens.isolated.module.constant.IsolatedCommProtoType;
import com.sunkaisens.isolated.module.domain.ModuleParas;
import com.sunkaisens.isolated.module.service.ModuleParasService;
import com.sunkaisens.isolated.module.service.UdpNetService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Reneryan
 */
@Slf4j
@RestController
@RequestMapping("/module-paras")
public class ModuleParasController extends BaseController {

	@Autowired
	ModuleParasService moduleParasService;
	
	@Autowired
	UdpNetService udpNetService;

	@GetMapping
	public Map<String, Object> moduleAllList(QueryRequest queryRequest, ModuleParas moduleParas) {
		return getDataTable(moduleParasService.findModuleParas(moduleParas, queryRequest));
	}

	@GetMapping("/status")
	public Map<String, Object> moduleStateList(QueryRequest queryRequest, ModuleParas moduleParas) {
		moduleParas.setFunction(IsolatedCommProtoType.FUNCTION_STATUS);
		return getDataTable(moduleParasService.findModuleParas(moduleParas, queryRequest));
	}
	
	@GetMapping("/{md}")
	public Map<String, Object> moduleList(QueryRequest queryRequest, ModuleParas moduleParas, 
			@NotBlank(message = "{required}") @PathVariable String md) {
		moduleParas.setModule(md);
		return getDataTable(moduleParasService.findModuleParas(moduleParas, queryRequest));
	}

//	@Log("配置参数")
    @PutMapping
    public Map<String, Object> updateModule(@RequestBody @Valid ModuleParas moduleParas) throws SysInnerException {
        try {
            log.info("下发配置！");
    		moduleParas.setParaId(moduleParas.getModule() + "-" + moduleParas.getFunction());
    		if(!StringUtils.isBlank(moduleParas.getSubFunction())) {
    			moduleParas.setParaId(moduleParas.getParaId() + "-" + moduleParas.getSubFunction());
    		}
            this.udpNetService.sendMsg(moduleParas);
            
            log.info("配置参数成功，更新数据库！");
            this.moduleParasService.saveOrUpdate(moduleParas);
            
            return new SunkResponse().retureCode(RetrueCode.OK).message("参数配置成功！");
            
        } catch (Exception e) {
            log.error("配置参数失败", e);
            throw new SysInnerException("配置参数失败");
        }
    }

    @PutMapping("/test")
    public Map<String, Object> test(@RequestParam String name) throws SysInnerException {
        log.error("test 失败 {}", name);
        throw new SysInnerException("test 失败: " + name);
    }
    
}
