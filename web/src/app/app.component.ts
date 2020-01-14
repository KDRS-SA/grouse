import {Component, Inject} from '@angular/core';
import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [
    trigger('modeChange', [
      state('login', style({
        width: '100%',
        opacity: '1'
      })),
      state('register', style({
        width: '0%',
        opacity: '0'
      })),
      transition('login => register', [
        animate('0.3s ease-out')
      ]),
      transition('register => login', [
        animate('0.3s ease-out')
      ])
    ])
  ]
})
export class AppComponent {
  title = 'Grouse - Logg inn';
  public login: boolean;

  constructor() {
    this.login = true;
  }


  public changeMode() {
    this.login = !this.login;
  }
}
