package com.project.ITAM.Service;

import com.project.ITAM.Model.SmtpConfig;
import com.project.ITAM.Model.SmtpConfigRequest;

import java.util.List;
import java.util.Optional;

public interface SmtpConfigService {

public SmtpConfig createSMPTConfiguration(SmtpConfigRequest smtpConfigRequest) throws Exception;

public SmtpConfig updateSMPTConfiguration(Long ssoId,SmtpConfigRequest smtpConfigRequest);

public void deleteBySMPTConfigureIdId(Long smptId);

public List<SmtpConfig> getAllSMPTConfiguration();

public SmtpConfig getSMPTConfigById(Long smptId);

public Optional<SmtpConfig> getSmtpConfigByProvider(String provider);
}
