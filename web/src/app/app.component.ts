import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./common.css']
})
export class AppComponent implements OnInit {
  private router: Router;

  constructor(router: Router) {
    this.router = router;
  }


  ngOnInit() {
    this.router.navigate(['/Login']);
  }
}
