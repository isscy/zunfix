package cn.ff.zunfix.common.security.service;

import org.springframework.security.oauth2.provider.ClientDetailsService;

@FunctionalInterface
@Deprecated
public interface ClientDetailsServiceResolver {

    ClientDetailsService resolve();
}
