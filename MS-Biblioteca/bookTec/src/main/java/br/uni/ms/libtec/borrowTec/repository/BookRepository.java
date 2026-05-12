package br.uni.ms.libtec.borrowTec.repository;

import br.uni.ms.libtec.borrowTec.model.Book;
import br.uni.ms.libtec.borrowTec.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository
        extends JpaRepository<Book, String> {

}
