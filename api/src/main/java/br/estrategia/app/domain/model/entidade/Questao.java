package br.estrategia.app.domain.model.entidade;

import javax.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Questao {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    private String pergunta;
    private boolean gabarito;

    public Questao() {
    }

    public Questao(String pergunta, boolean gabarito) {
        this.pergunta = pergunta;
        this.gabarito = gabarito;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public boolean getGabarito() {
        return gabarito;
    }

    public void setGabarito(Boolean gabarito) {
        this.gabarito = gabarito;
    }

  

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Questao questao = (Questao) o;
        return id == questao.id &&
                Objects.equals(pergunta, questao.pergunta) &&
                Objects.equals(gabarito, questao.gabarito);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pergunta, gabarito);
    }
}
