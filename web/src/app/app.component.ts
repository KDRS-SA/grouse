import {Component, Inject} from '@angular/core';
import {Animations} from './app.animations';
import {FormControl, Validators} from '@angular/forms';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [
    Animations.toggleSlide
  ]
})
export class AppComponent {
  public title = 'Grouse';
  public login: boolean;

  email = new FormControl('',
    [Validators.required, Validators.email]);

  getErrorMessage() {
    return this.email.hasError('required') ? 'Du må skrive inn en E-post adresse' :
    this.email.hasError('email') ? 'Ikke en gyldig E-post Adresse' : '';
  }

  constructor() {
    this.login = true;
  }

  public changeMode() {
    this.login = !this.login;
  }
}
