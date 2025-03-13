package com.project.ITAM.Service;

import com.project.ITAM.Exception.NotFoundException;
import com.project.ITAM.Model.SmtpConfig;
import com.project.ITAM.Model.SmtpConfigRequest;
import com.project.ITAM.Repository.SmptConfigRepo;
import com.project.ITAM.helper.DateTimeUtil;
import com.project.ITAM.helper.EncryptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.ITAM.helper.ExtractJsonUtil.updateIfNotEmpty;

@Service
public class SmtpConfigServiceImpl implements SmtpConfigService {

    @Autowired
    private SmptConfigRepo smptConfigRepo;

    @Override
    public SmtpConfig createSMPTConfiguration(SmtpConfigRequest smtpConfigRequest) throws Exception {
        String encryptedPassword=null;
        if(!StringUtils.isEmpty(smtpConfigRequest.getPassword())){
             encryptedPassword = EncryptionUtil.encrypt(smtpConfigRequest.getPassword());
        }
        return smptConfigRepo.save(SmtpConfig.builder().createdBy("default").createdTime(DateTimeUtil.currentDateTime())
                        .port(smtpConfigRequest.getPort()).host(smtpConfigRequest.getHost()).encryptionType(smtpConfigRequest.getEncryptionType())
                .username(smtpConfigRequest.getUsername()).fromEmail(smtpConfigRequest.getFromEmail()).password(encryptedPassword)
                        .build());
    }

    @Override
    public SmtpConfig updateSMPTConfiguration(Long smptId, SmtpConfigRequest smtpConfigRequest) {
        SmtpConfig smtpConfig = smptConfigRepo.findById(smptId)
                .orElseThrow(() -> new NotFoundException("SSO not found"));

        Optional.ofNullable(smtpConfigRequest.getPassword())
                .ifPresent(token -> {
                    try {
                        smtpConfig.setPassword(EncryptionUtil.encrypt(smtpConfigRequest.getPassword()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        updateIfNotEmpty(smtpConfigRequest.getPort(), smtpConfig::setPort);
        updateIfNotEmpty(smtpConfigRequest.getHost(), smtpConfig::setHost);
        updateIfNotEmpty(smtpConfigRequest.getUsername(), smtpConfig::setUsername);
        updateIfNotEmpty(smtpConfigRequest.getPassword(), smtpConfig::setPassword);
        updateIfNotEmpty(smtpConfigRequest.getFromEmail(), smtpConfig::setFromEmail);
        updateIfNotEmpty(smtpConfigRequest.getEncryptionType(), smtpConfig::setEncryptionType);

        smtpConfig.setUpdatedBy("default");
        smtpConfig.setUpdatedTime(DateTimeUtil.currentDateTime());

        return smptConfigRepo.save(smtpConfig);
    }

    @Override
    public void deleteBySMPTConfigureIdId(Long smptId) {
        if(smptConfigRepo.existsById(smptId)){
            smptConfigRepo.deleteById(smptId);
        }
    }

    @Override
    public List<SmtpConfig> getAllSMPTConfiguration() {
        return smptConfigRepo.findAll();
    }

    @Override
    public SmtpConfig getSMPTConfigById(Long smptId) {
        return smptConfigRepo.findById(smptId)
                .orElseThrow(() -> new NotFoundException(smptId + " not found"));
    }

    @Override
    public Optional<SmtpConfig> getSmtpConfigByProvider(String provider) {
        return smptConfigRepo.findByProvider(provider);
    }
}
