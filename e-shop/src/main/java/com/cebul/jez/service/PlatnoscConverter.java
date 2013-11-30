package com.cebul.jez.service;

import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.stereotype.Service;

@Service("platnoscConverter")
public class PlatnoscConverter extends DefaultConversionService
{
	public void ConversionService() {
        addDefaultConverters();
    }

    @Override
    protected void addDefaultConverters() {
        super.addDefaultConverters();
    }
}
