package com.furniture.market.config;

import com.furniture.market.security.ShiroManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * SpringJpa 审计配置
 *
 * @author Lijq
 */
@Configuration
public class AdminAuditorBean implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String username = ShiroManager.getAccount().getUsername();
        return Optional.of(username);
    }
}
