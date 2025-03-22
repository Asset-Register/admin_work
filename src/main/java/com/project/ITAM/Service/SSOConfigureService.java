package com.project.ITAM.Service;

import com.project.ITAM.Model.SSOConfigurationRequest;
import com.project.ITAM.Model.SsoConfig;

import java.util.List;
import java.util.Optional;

public interface SSOConfigureService {

public SsoConfig createSSOConfiguration(SSOConfigurationRequest ssoConfigurationRequest) throws Exception;

public SsoConfig updateSSOConfiguration(Long ssoId,SSOConfigurationRequest ssoConfigurationRequest);

public void deleteBySSOConfigureIdId(Long ssoId);

public List<SsoConfig> getAllSSOConfiguration();

public SsoConfig getSSOConfigById(Long ssoId);

    public Optional<SsoConfig> getSSOConfigByProviderNameAndEnabled(String  provider);
}
