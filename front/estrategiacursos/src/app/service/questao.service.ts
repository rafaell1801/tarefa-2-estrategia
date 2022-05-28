import { constants } from './constants';
import { Injectable } from '@angular/core';
import { Questao } from '../model/questao.model';
import { ServiceBase } from './service-base.service';

@Injectable({
  providedIn: 'root',
})
export class QuestaoService extends ServiceBase<Questao> {
  getURL(): string {
    return constants.QUESTOES_API;
  }
}
