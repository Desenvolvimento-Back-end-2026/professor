package br.uni.ms.libtec.authtec.client;

import br.uni.ms.libtec.authtec.dto.LoginDTO;
import br.uni.ms.libtec.authtec.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userTec")
public interface UserTecClient {

    @PostMapping("/api/user/validate")
    ResponseEntity<UserDto> validateUser(@RequestBody LoginDTO dto);

}
