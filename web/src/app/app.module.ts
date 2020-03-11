import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
// tslint:disable-next-line:max-line-length
import {
    MatCardModule, MatProgressBarModule,
    MatProgressSpinnerModule,
    MatSliderModule,
    MatSnackBarModule,
    MatTableModule,
    MatTabsModule,
    MatTooltipModule
} from '@angular/material';
import {MatButtonModule} from '@angular/material/button';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {GDPRContent, LoginComponent} from './Login/Login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import {DeleteProjectDialog, MenuComponent, NewProjectDialog} from './Menu/menu.component';
import {AppComponent} from './app.component';
import {Data} from './data.service';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatListModule} from '@angular/material/list';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatMenuModule} from '@angular/material/menu';
import {DeleteRequirmentDialog, kravEditComponent} from './KravEdit/kravEdit.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatTreeModule} from '@angular/material/tree';
import {userEditComponent} from './UserEdit/userEdit.component';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MenuComponent,
    kravEditComponent,
    NewProjectDialog,
    GDPRContent,
    userEditComponent,
    DeleteRequirmentDialog,
    DeleteProjectDialog
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
        MatSnackBarModule,
        MatToolbarModule,
        MatListModule,
        MatDialogModule,
        FormsModule,
        MatMenuModule,
        MatCheckboxModule,
        MatSidenavModule,
        MatTreeModule,
        MatTabsModule,
        MatButtonToggleModule,
        MatTooltipModule,
        MatProgressBarModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
          }
        )
    ],
  providers: [],
  bootstrap: [AppComponent],
  exports: [
    NewProjectDialog,
    GDPRContent,
    DeleteRequirmentDialog,
    DeleteProjectDialog
  ],
  entryComponents: [
    NewProjectDialog,
    GDPRContent,
    DeleteRequirmentDialog,
    DeleteProjectDialog
  ]
})
export class AppModule { }
