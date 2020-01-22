import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {MatCardModule, MatProgressSpinnerModule, MatSliderModule} from '@angular/material';
import {MatButtonModule} from '@angular/material/button';
import { HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './Login/Login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {ReactiveFormsModule} from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { MenuComponent } from './menu/menu.component';
import {AppComponent} from './app.component';
import {Data} from './data.service';
import {MatToolbarModule} from "@angular/material/toolbar";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MenuComponent
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        MatSliderModule,
        MatButtonModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        ReactiveFormsModule,
        HttpClientModule,
        AppRoutingModule,
        MatProgressSpinnerModule,
        Data,
        MatToolbarModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
