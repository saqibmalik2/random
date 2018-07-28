package com.saqib.puzzles.utils;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.saqib.puzzles.OptimalPath;

@Configuration
public class TestConfig {
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public OptimalPath getOptimalPath() {
		return new OptimalPath();
	}
}
