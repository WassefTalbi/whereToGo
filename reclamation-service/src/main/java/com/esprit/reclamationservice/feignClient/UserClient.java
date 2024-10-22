package com.esprit.reclamationservice.feignClient;

import com.esprit.reclamationservice.modal.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service",url="localhost:1998")
public interface UserClient {
    @GetMapping("/user/current-user")
    User getCurrentConnected();
}
