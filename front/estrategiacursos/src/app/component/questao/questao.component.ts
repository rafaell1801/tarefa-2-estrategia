import { QuestaoService } from './../../service/questao.service';
import { Component, OnInit } from '@angular/core';
import { Questao } from 'src/app/model/questao.model';
import { ServiceBase } from 'src/app/service/service-base.service';
import { ComponentBaseCadastroDirective } from '../component-base-cadastro';

@Component({
  selector: 'app-questao',
  templateUrl: './questao.component.html',
  styleUrls: ['./questao.component.css']
})
export class QuestaoComponent extends ComponentBaseCadastroDirective<Questao>{
  getService(): QuestaoService {
    return this.injector.get(QuestaoService);
  }
  novaInstancia(): Questao {
    return new Questao();
  }
}