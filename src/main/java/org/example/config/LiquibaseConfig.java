package org.example.config;

import io.spring.gradle.dependencymanagement.org.apache.commons.lang3.StringUtils;
import liquibase.integration.spring.SpringLiquibase;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuppressWarnings("checkstyle:VisibilityModifier")
public class LiquibaseConfig {
    static final String LIQUIBASE_INIT_MSG = "Initializing Liquibase with real instance.";
    static final String LIQUIBASE_INIT_STUB_MSG = "Liquibase initializing stub Liquibase instance.";
    static final String LIQUIBASE_CHANGE_LOG_FILE_ERROR_MSG = "Liquibase change-log file is not specified. "
            + "Configure 'spring.datasource.liquibase.change-log' property to use Liquibase.";
    @Autowired
    DataSource dataSource;
    @Value("${spring.datasource.liquibase.change-log}")
    String changelogFile;

    @Bean
    @ConditionalOnMissingBean(name = "liquibase")
    public SpringLiquibase liquibaseInit() {
        if (StringUtils.isBlank(changelogFile)) {
            log.warn(LIQUIBASE_CHANGE_LOG_FILE_ERROR_MSG);
            log.info(LIQUIBASE_INIT_STUB_MSG);
            return new SpringLiquibase();
        } else {
            log.info(LIQUIBASE_INIT_MSG);
            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setDataSource(dataSource);
            liquibase.setChangeLog(changelogFile);
            return liquibase;
        }
    }
}