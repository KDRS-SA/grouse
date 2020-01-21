import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from './Login/Login.component';
import {MenuComponent} from './menu/menu.component';

const routes: Routes = [
  { path: 'Login', component: LoginComponent },
  { path: 'Menu', component: MenuComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
