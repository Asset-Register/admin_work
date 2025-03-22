package com.project.ITAM.Controller;

import com.project.ITAM.Model.SSOConfigurationRequest;
import com.project.ITAM.Model.SsoConfig;
import com.project.ITAM.Service.SSOConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/sso")
@CrossOrigin(origins = "http://localhost:5173")
@Validated
public class SSOController {

    @Autowired
    private SSOConfigureService ssoConfigureService;

    /** configure new SSO
     *
     * @param ssoConfigurationRequest
     * @return
     */
    @PostMapping("/configure")
    public ResponseEntity<SsoConfig> configurenewSSO(@RequestBody SSOConfigurationRequest ssoConfigurationRequest) throws Exception {
        return ResponseEntity.ok(ssoConfigureService.createSSOConfiguration(ssoConfigurationRequest));
    }

    /** update SSO configuration
     *
     * @param ssoId
     * @param ssoConfigurationRequest
     * @return
     */
    @PatchMapping("/{ssoId}/update")
    public ResponseEntity<SsoConfig> updateSSO(@PathVariable("ssoId") Long ssoId,@RequestBody SSOConfigurationRequest ssoConfigurationRequest){
        return ResponseEntity.ok(ssoConfigureService.updateSSOConfiguration(ssoId,ssoConfigurationRequest));
    }

    /** delete SSO configuration
     *
     * @param ssoId
     * @return
     */
    @DeleteMapping("/{ssoId}/delete")
    public String deleteBySSOConfigureIdId(@PathVariable("ssoId") Long ssoId){
        ssoConfigureService.deleteBySSOConfigureIdId(ssoId);
        return "SSO deleted";
    }

    /** get ALL SSO configuration
     *
     * @return
     */
    @GetMapping("/readAll")
    public ResponseEntity<List<SsoConfig>> getAllSSOConfiguration(){
        return  ResponseEntity.ok(ssoConfigureService.getAllSSOConfiguration());
    }

    /** get  SSO configuration by id
     *
     * @return
     */
    @GetMapping("/{ssoId}/read")
    public ResponseEntity<SsoConfig> getSSOConfigurationbyId(@PathVariable("ssoId") Long ssoId){
        return  ResponseEntity.ok(ssoConfigureService.getSSOConfigById(ssoId));
    }

    /** get  SSO configuration by provider
     *
     * @return
     */
    @GetMapping("/{provider}/get")
    public ResponseEntity<Optional<SsoConfig>> getSSOConfigurationbyProvider(@PathVariable("provider") String provider){
        return  ResponseEntity.ok(ssoConfigureService.getSSOConfigByProviderNameAndEnabled(provider));
    }


}
