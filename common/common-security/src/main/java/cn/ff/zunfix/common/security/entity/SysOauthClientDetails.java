package cn.ff.zunfix.common.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.*;
import lombok.SneakyThrows;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName(value = "sys_oauth_client_details")
public class SysOauthClientDetails implements ClientDetails {

    private final static JsonParser parser = JsonParserFactory.getJsonParser();

    @TableField("client_id")
    private String clientId;

    @TableField("client_secret")
    private String clientSecret;

    private String scope;

    @TableField("resource_ids")
    private String resourceIds;

    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;

    @TableField("redirect_uris")
    private String redirectUris;

    @TableField("auto_approve")
    private String autoApprove;

    private String authorities;

    @TableField("access_token_validity")
    private Integer accessTokenValiditySeconds;

    @TableField("refresh_token_validity")
    private Integer refreshTokenValiditySeconds;

    @TableField("additional_information")
    private String additionalInformation;

    public SysOauthClientDetails() {
    }


    public SysOauthClientDetails(String clientId, String resourceIds,
                                 String scopes, String grantTypes, String authorities) {
        this(clientId, resourceIds, scopes, grantTypes, authorities, null);
    }

    public SysOauthClientDetails(String clientId, String resourceIds,
                                 String scopes, String grantTypes, String authorities,
                                 String redirectUris) {
        this.clientId = clientId;
        this.resourceIds = resourceIds;
        this.scope = scopes;
        this.authorizedGrantTypes = grantTypes;
        this.authorities = authorities;
        this.redirectUris = redirectUris;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setAutoApprove(String autoApprove) {
        this.autoApprove = autoApprove;
    }

    public Set<String> getAutoApprove() {
        return StringUtils.commaDelimitedListToSet(autoApprove);
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (StringUtils.isEmpty(autoApprove)) {
            return false;
        }
        for (String auto : autoApprove.split(",")) {
            if (scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    @JsonIgnore
    public boolean isScoped() {
        return StringUtils.hasText(this.scope);
    }

    @Override
    public Set<String> getScope() {
        return StringUtils.commaDelimitedListToSet(scope);
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public Set<String> getResourceIds() {
        return StringUtils.commaDelimitedListToSet(resourceIds);
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return StringUtils.commaDelimitedListToSet(authorizedGrantTypes);
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    @Override
    @JsonIgnore
    public Set<String> getRegisteredRedirectUri() {
        return StringUtils.commaDelimitedListToSet(redirectUris);
    }

    public Set<String> getRedirectUris() {
        return getRegisteredRedirectUri();
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    @JsonProperty("authorities")
    private List<String> getAuthoritiesAsStrings() {
        return authorities == null ? Collections.emptyList()
                : Arrays.asList(authorities.split(","));
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    @Override
    @JsonIgnore
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities == null ? Collections.emptyList()
                : AuthorityUtils.createAuthorityList(authorities.split(","));
    }

    @Override
    @JsonIgnore
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValiditySeconds = accessTokenValidity;
    }

    @Override
    @JsonIgnore
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValidity(
            Integer refreshTokenValidity) {
        this.refreshTokenValiditySeconds = refreshTokenValidity;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    @Override
    @SneakyThrows
    @JsonAnyGetter
    public Map<String, Object> getAdditionalInformation() {
        return parser.parseMap(this.additionalInformation);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((accessTokenValiditySeconds == null) ? 0
                : accessTokenValiditySeconds);
        result = prime
                * result
                + ((refreshTokenValiditySeconds == null) ? 0
                : refreshTokenValiditySeconds);
        result = prime * result
                + ((authorities == null) ? 0 : authorities.hashCode());
        result = prime
                * result
                + ((authorizedGrantTypes == null) ? 0 : authorizedGrantTypes
                .hashCode());
        result = prime * result
                + ((clientId == null) ? 0 : clientId.hashCode());
        result = prime * result
                + ((clientSecret == null) ? 0 : clientSecret.hashCode());
        result = prime
                * result
                + ((redirectUris == null) ? 0
                : redirectUris.hashCode());
        result = prime * result
                + ((resourceIds == null) ? 0 : resourceIds.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        result = prime * result + ((additionalInformation == null) ? 0
                : additionalInformation.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SysOauthClientDetails other = (SysOauthClientDetails) obj;
        if (accessTokenValiditySeconds == null) {
            if (other.accessTokenValiditySeconds != null) {
                return false;
            }
        } else if (!accessTokenValiditySeconds.equals(other.accessTokenValiditySeconds)) {
            return false;
        }
        if (refreshTokenValiditySeconds == null) {
            if (other.refreshTokenValiditySeconds != null) {
                return false;
            }
        } else if (!refreshTokenValiditySeconds.equals(other.refreshTokenValiditySeconds)) {
            return false;
        }
        if (authorities == null) {
            if (other.authorities != null) {
                return false;
            }
        } else if (!authorities.equals(other.authorities)) {
            return false;
        }
        if (authorizedGrantTypes == null) {
            if (other.authorizedGrantTypes != null) {
                return false;
            }
        } else if (!authorizedGrantTypes.equals(other.authorizedGrantTypes)) {
            return false;
        }
        if (clientId == null) {
            if (other.clientId != null) {
                return false;
            }
        } else if (!clientId.equals(other.clientId)) {
            return false;
        }
        if (clientSecret == null) {
            if (other.clientSecret != null) {
                return false;
            }
        } else if (!clientSecret.equals(other.clientSecret)) {
            return false;
        }
        if (redirectUris == null) {
            if (other.redirectUris != null) {
                return false;
            }
        } else if (!redirectUris.equals(other.redirectUris)) {
            return false;
        }
        if (resourceIds == null) {
            if (other.resourceIds != null) {
                return false;
            }
        } else if (!resourceIds.equals(other.resourceIds)) {
            return false;
        }
        if (scope == null) {
            if (other.scope != null) {
                return false;
            }
        } else if (!scope.equals(other.scope)) {
            return false;
        }
        if (additionalInformation == null) {
            return other.additionalInformation == null;
        } else {
            return additionalInformation.equals(other.additionalInformation);
        }
    }

    @Override
    public String toString() {
        return "BaseClientDetails [clientId=" + clientId + ", clientSecret="
                + clientSecret + ", scope=" + scope + ", resourceIds="
                + resourceIds + ", authorizedGrantTypes="
                + authorizedGrantTypes + ", registeredRedirectUris="
                + redirectUris + ", authorities=" + authorities
                + ", accessTokenValiditySeconds=" + accessTokenValiditySeconds
                + ", refreshTokenValiditySeconds="
                + refreshTokenValiditySeconds + ", additionalInformation="
                + additionalInformation + "]";
    }
}
