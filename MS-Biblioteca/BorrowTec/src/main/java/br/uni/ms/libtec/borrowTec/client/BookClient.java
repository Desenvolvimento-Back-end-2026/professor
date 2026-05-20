package br.uni.ms.libtec.borrowTec.client;

import br.uni.ms.libtec.borrowTec.dto.BookSimpleListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "BOOKTEC")
public interface BookClient {

    @GetMapping("/api/book/{isbn}")
    public ResponseEntity<BookSimpleListDto> getOneBook(@PathVariable("isbn") String isbn);

    // PAtch não funciona no feign!!!!!
    @PatchMapping("/api/book/{isbn}/emprestar")
    public ResponseEntity<BookSimpleListDto> emprestaBook(@PathVariable("isbn") String isbn);

}
