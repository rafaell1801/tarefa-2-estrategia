package br.estrategia.app.application.resource;

import br.estrategia.app.domain.model.entidade.Questao;
import br.estrategia.app.domain.repository.QuestaoRepository;
import br.estrategia.app.infra.rest.URI_API_PATHS;
import br.estrategia.app.util.AbstractAPITesting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestaoResourceTest extends AbstractAPITesting {

    @Autowired
    private QuestaoRepository questaoRepository;

    @BeforeEach
    public void setUp() {
        web = web.mutate().responseTimeout(Duration.ofMillis(10000)).build();
        questaoRepository.deleteAll();
    }

    @Test
    public void deve_salvar_questao() {

        String _pergunta = "O consumo compartilhado de cinco aplicações em um servidor físico que utiliza controles de um servidor"+
        "de aplicações para manter a disponibilidade de cada aplicação com configurações distintas caracteriza o uso de virtualização.";
        boolean _gabarito = false;
        Questao questao = new Questao(_pergunta, _gabarito);

        web.post().uri(URI_API_PATHS.QUESTOES_API)
                .header("Authorization", TOKEN_ADMIN)
                .accept(MediaType.ALL)
                .body(BodyInserters.fromValue(questao))
                .exchange()
                .expectStatus().isCreated().expectBody(Questao.class)
                .value(c -> assertTrue(c.getId() > 0));
    }

    @Test
    public void deve_listar_questoes() {

        deve_salvar_questao();
        deve_salvar_questao();

        conferirQuestoesListadas(2);
    }

    @Test
    public void deve_atualizar_questao() {
        Questao questao = new Questao("O Brasil tem 27 estados?", true);

        web.post().uri(URI_API_PATHS.QUESTOES_API)
                .accept(MediaType.ALL)
                .header("Authorization", TOKEN_ADMIN)
                .body(BodyInserters.fromValue(questao))
                .exchange()
                .expectStatus().isCreated().expectBody(Questao.class)
                .value(c -> {
                    assertEquals("O Brasil tem 27 estados?", c.getPergunta());
                    questao.setId(c.getId());
                    questao.setPergunta("O Brasil não tem 27 estados?");
                    questao.setGabarito(false);

                    web.put().uri(URI_API_PATHS.QUESTOES_API + "/" + c.getId())
                            .accept(MediaType.ALL)
                            .header("Authorization", TOKEN_ADMIN)
                            .body(BodyInserters.fromValue(questao))
                            .exchange()
                            .expectStatus().isOk().expectBody(Questao.class)
                            .value(questaoAtualizada -> assertEquals("O Brasil não tem 27 estados?", questaoAtualizada.getPergunta()));
                });

    }

    @Test
    public void deve_remover_questao() {
        conferirQuestoesListadas(0);
        deve_salvar_questao();
        conferirQuestoesListadas(1);
        web.delete().uri(URI_API_PATHS.QUESTOES_API + "/1")
                .accept(MediaType.ALL)
                .header("Authorization", TOKEN_ADMIN)
                .exchange()
                .expectStatus().isOk();
        conferirQuestoesListadas(0);
    }

   
    private void conferirQuestoesListadas(int total) {
        web.get().uri(URI_API_PATHS.QUESTOES_API)
                .accept(MediaType.ALL)
                .header("Authorization", TOKEN_ADMIN)
                .exchange()
                .expectStatus().isOk().expectBodyList(Questao.class)
                .hasSize(total);
    }
}
