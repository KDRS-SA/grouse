import {Component, Inject} from '@angular/core';
import {Animations} from './app.animations';
import {FormControl, Validators} from '@angular/forms';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {Injectable} from '@angular/core';

// Lar klienten konfigurere HttpClienten, som brukes for å komunisere med serveren
@Injectable()
export class ConfigService {
  constructor(private http: HttpClient) {}
}

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

  private http: HttpClient;

  email = new FormControl('',
    [Validators.required, Validators.email]);

  getErrorMessage() {
    return this.email.hasError('required') ? 'Du må skrive inn en E-post adresse' :
    this.email.hasError('email') ? 'Ikke en gyldig E-post Adresse' : '';
  }

  constructor(http: HttpClient) {
    this.login = true;
    this.http = http;
  }

  public changeMode() {
    this.login = !this.login;
  }

  public httpGetTest() {
    this.http.get<string>('http://localhost:9294/grouse/').subscribe(result => {
      console.log(result);
    }, error => console.error(error));
  }
}
