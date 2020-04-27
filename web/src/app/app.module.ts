import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
// tslint:disable-next-line:max-line-length
import {
    MatCardModule, MatExpansionModule, MatProgressBarModule,
    MatProgressSpinnerModule, MatSelectModule,
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
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatListModule} from '@angular/material/list';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatMenuModule} from '@angular/material/menu';
import {ConcurrencyResolver, DeleteRequirmentDialog, kravEditComponent, ShareMenu} from './KravEdit/kravEdit.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatTreeModule} from '@angular/material/tree';
import {DeleteUserDialog, PasswordChangeConfirmedDialog, userEditComponent} from './UserEdit/userEdit.component';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {adminComponent, AdminDeleteUserDialog} from "./Admin/Admin.component";
import {MatRadioModule} from "@angular/material/radio";

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
    DeleteProjectDialog,
    PasswordChangeConfirmedDialog,
    DeleteUserDialog,
    ShareMenu,
    adminComponent,
    AdminDeleteUserDialog,
    ConcurrencyResolver
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
        ),
        MatSelectModule,
        MatExpansionModule,
        MatRadioModule
    ],
  providers: [],
  bootstrap: [AppComponent],
  exports: [
    NewProjectDialog,
    GDPRContent,
    DeleteRequirmentDialog,
    DeleteProjectDialog,
    PasswordChangeConfirmedDialog,
    DeleteUserDialog,
    ShareMenu,
    AdminDeleteUserDialog,
    ConcurrencyResolver
  ],
  entryComponents: [
    NewProjectDialog,
    GDPRContent,
    DeleteRequirmentDialog,
    DeleteProjectDialog,
    PasswordChangeConfirmedDialog,
    DeleteUserDialog,
    ShareMenu,
    AdminDeleteUserDialog,
    ConcurrencyResolver
  ]
})
export class AppModule { }
