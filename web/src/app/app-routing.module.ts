import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from './Login/Login.component';
import {MenuComponent} from './menu/menu.component';
import {AppComponent} from './app.component';
import {kravEditComponent} from './kravEdit/kravEdit.component';
import {userEditComponent} from './UserEdit/userEdit.component';

const routes: Routes = [
  { path: 'Login', component: LoginComponent },
  { path: 'Menu', component: MenuComponent },
  { path: 'kravEdit', component: kravEditComponent},
  { path: 'userEdit', component: userEditComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
