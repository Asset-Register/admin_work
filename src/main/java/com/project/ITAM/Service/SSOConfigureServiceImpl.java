package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.SSOConfigurationRequest;
import com.project.ITAM.Model.SsoConfig;
import com.project.ITAM.Repository.SSOConfigRepo;
import com.project.ITAM.helper.DateTimeUtil;
import com.project.ITAM.helper.EncryptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.ITAM.helper.ExtractJsonUtil.updateIfNotEmpty;

@Service
public class SSOConfigureServiceImpl implements SSOConfigureService{

    @Autowired
    private SSOConfigRepo ssoConfigRepo;

    @Override
    public SsoConfig createSSOConfiguration(SSOConfigurationRequest ssoConfigurationRequest) throws Exception {
        String encryptedClientSecret=null;
        if(!StringUtils.isEmpty(ssoConfigurationRequest.getClientSecret())){
             encryptedClientSecret = EncryptionUtil.encrypt(ssoConfigurationRequest.getClientSecret());
        }
        return ssoConfigRepo.save(SsoConfig.builder().createdBy("default").createdTime(DateTimeUtil.currentDateTime())
                        .ssoConfigurationName(ssoConfigurationRequest.getSsoConfigureName())
                .scope(ssoConfigurationRequest.getScope()).authorizationUri(ssoConfigurationRequest.getAuthorizationUri())
                .clientId(ssoConfigurationRequest.getClientId()).enabled(ssoConfigurationRequest.isEnable())
                .providerName(ssoConfigurationRequest.getProviderName()).redirectUri(ssoConfigurationRequest.getRedirectUri())
                .userInfoUri(ssoConfigurationRequest.getUserInfoUri()).tokenUri(ssoConfigurationRequest.getTokenUri())
                .clientSecret(encryptedClientSecret).build());
    }

    @Override
    public SsoConfig updateSSOConfiguration(Long ssoId, SSOConfigurationRequest ssoConfigurationRequest) {
        SsoConfig ssoConfig = ssoConfigRepo.findById(ssoId)
                .orElseThrow(() -> new NotFoundException("SSO not found"));
        Optional.ofNullable(ssoConfigurationRequest.getClientSecret())
                .ifPresent(token -> {
                    try {
                        ssoConfig.setClientSecret(EncryptionUtil.encrypt(ssoConfigurationRequest.getClientSecret()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        updateIfNotEmpty(ssoConfigurationRequest.getSsoConfigureName(), ssoConfig::setSsoConfigurationName);
        updateIfNotEmpty(ssoConfigurationRequest.getClientId(), ssoConfig::setClientId);
        updateIfNotEmpty(ssoConfigurationRequest.getScope(), ssoConfig::setScope);
        updateIfNotEmpty(ssoConfigurationRequest.getRedirectUri(), ssoConfig::setRedirectUri);
        updateIfNotEmpty(ssoConfigurationRequest.getTokenUri(), ssoConfig::setTokenUri);
        updateIfNotEmpty(ssoConfigurationRequest.getProviderName(), ssoConfig::setProviderName);
        updateIfNotEmpty(ssoConfigurationRequest.getUserInfoUri(), ssoConfig::setUserInfoUri);
        updateIfNotEmpty(ssoConfigurationRequest.getAuthorizationUri(), ssoConfig::setAuthorizationUri);

        ssoConfig.setUpdatedBy("default");
        ssoConfig.setUpdatedTime(DateTimeUtil.currentDateTime());

        return ssoConfigRepo.save(ssoConfig);
    }

    @Override
    public void deleteBySSOConfigureIdId(Long ssoId) {
        if(ssoConfigRepo.existsById(ssoId)){
            ssoConfigRepo.deleteById(ssoId);
        }
    }

    @Override
    public List<SsoConfig> getAllSSOConfiguration() {
        return ssoConfigRepo.findAll();
    }

    @Override
    public SsoConfig getSSOConfigById(Long ssoId) {
        return ssoConfigRepo.findById(ssoId)
                .orElseThrow(() -> new NotFoundException(ssoId + " not found"));
    }

    @Override
    public Optional<SsoConfig> getSSOConfigByProviderNameAndEnabled(String provider) {
        return ssoConfigRepo.findByProviderAndEnabled(provider);
    }
}
