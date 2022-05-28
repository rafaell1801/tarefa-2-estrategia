package br.estrategia.app.domain.repository;

import br.estrategia.app.domain.model.entidade.Questao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestaoRepository extends CrudRepository<Questao, Long> {
}
