package com.memorynotfound.cloud;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class DiscoveryController {

    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/applications")
    public Applications getApplications() {
        return eurekaClient.getApplications();
    }

    @GetMapping("/regions")
    public Set<String> getRegions() {
        return eurekaClient.getAllKnownRegions();
    }

    @GetMapping("/instances/{app-name}")
    public List<InstanceInfo> getInstancesByVipAddress(@PathVariable("app-name") String appName) {
        return eurekaClient.getInstancesByVipAddress(appName, false);
    }
}
