import {Injectable, NgModule} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {UserData} from './models/UserData.model';

@NgModule()

@Injectable()
export class Data {
  private source = new BehaviorSubject(new UserData());
  currentMessage = this.source.asObservable();

  constructor() {}

  changeMessage(message: UserData) {
    this.source.next(message);
  }
}
