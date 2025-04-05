package com.project.ITAM.Controller;

import com.project.ITAM.Model.SmtpConfig;
import com.project.ITAM.Model.SmtpConfigRequest;
import com.project.ITAM.Service.SmtpConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/data/smpt")
@CrossOrigin(origins = "http://localhost:5173")
@Validated
public class SMPTController {

    @Autowired
    private SmtpConfigService smptConfigureService;

    /** configure new SMTP
     *
     * @param smtpConfigRequest
     * @return
     */
    @PostMapping("/configure")
    public ResponseEntity<SmtpConfig> configurenewSMTP(@RequestBody SmtpConfigRequest smtpConfigRequest) throws Exception {
        return ResponseEntity.ok(smptConfigureService.createSMPTConfiguration(smtpConfigRequest));
    }

    /** update STP configuration
     *
     * @param smtpId
     * @param smtpConfigRequest
     * @return
     */
    @PatchMapping("/{smtpId}/update")
    public ResponseEntity<SmtpConfig> updateSMTP(@PathVariable("smtpId") Long smtpId,@RequestBody SmtpConfigRequest smtpConfigRequest){
        return ResponseEntity.ok(smptConfigureService.updateSMPTConfiguration(smtpId,smtpConfigRequest));
    }

    /** delete smtp configuration
     *
     * @param smtpId
     * @return
     */
    @DeleteMapping("/{smtpId}/delete")
    public String deleteBySMTPConfigureIdId(@PathVariable("ssoId") Long smtpId){
        smptConfigureService.deleteBySMPTConfigureIdId(smtpId);
        return "SSO deleted";
    }

    /** get ALll smtp configuration
     *
     * @return
     */
    @GetMapping("/readAll")
    public ResponseEntity<List<SmtpConfig>> getAllSMTPConfiguration(){
        return  ResponseEntity.ok(smptConfigureService.getAllSMPTConfiguration());
    }

    /** get  smtp configuration by id
     *
     * @return
     */
    @GetMapping("/{smtpId}/read")
    public ResponseEntity<SmtpConfig> getSMTPConfigurationbyId(@PathVariable("smtpId") Long ssoId){
        return  ResponseEntity.ok(smptConfigureService.getSMPTConfigById(ssoId));
    }

    /** get  smtp configuration by provider
     *
     * @return
     */
    @GetMapping("/{provider}/get")
    public ResponseEntity<Optional<SmtpConfig>> getSMTPConfigurationbyId(@PathVariable("provider") String provider){
        return  ResponseEntity.ok(smptConfigureService.getSmtpConfigByProvider(provider));
    }

}
