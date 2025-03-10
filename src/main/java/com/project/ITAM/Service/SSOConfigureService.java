package com.project.ITAM.Service;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.DashBoardRequest;
import com.project.ITAM.Model.SSOConfigurationRequest;
import com.project.ITAM.Model.SsoConfig;

import java.util.List;

public interface SSOConfigureService {

public SsoConfig createSSOConfiguration(SSOConfigurationRequest ssoConfigurationRequest);

public SsoConfig updateSSOConfiguration(Long ssoId,SSOConfigurationRequest ssoConfigurationRequest);

public void deleteBySSOConfigureIdId(Long ssoId);

public List<SsoConfig> getAllSSOConfiguration();

public SsoConfig getSSOConfigById(Long ssoId);
}
