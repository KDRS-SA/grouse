import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
// tslint:disable-next-line:max-line-length
import {
    MatCardModule, MatExpansionModule, MatProgressBarModule,
    MatProgressSpinnerModule, MatSelectModule,
    MatSliderModule,
    MatSnackBarModule,
    MatTabsModule,
    MatTooltipModule
} from '@angular/material';
import {MatButtonModule} from '@angular/material/button';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import {AppComponent} from './app.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatListModule} from '@angular/material/list';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatMenuModule} from '@angular/material/menu';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatTreeModule} from '@angular/material/tree';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {MatRadioModule} from "@angular/material/radio";

import {userEditComponent} from './UserEdit/userEdit.component';
import {kravEditComponent} from './KravEdit/kravEdit.component';
import {GDPRContent, LoginComponent} from './Login/Login.component';
import {MenuComponent} from './Menu/menu.component';
import {adminComponent} from "./Admin/Admin.component";
import {AdminDeleteUserDialog} from './Modals/AdminDeleteUser/AdminDeleteUser.component';
import {ConcurrencyResolver} from './Modals/ConcurrencyResolver/ConcurrencyResolver.component';
import {DeleteUserDialog} from "./Modals/DeleteUser/DeleteUser.component";
import {NewProjectDialog} from './Modals/NewProject/NewProject.component';
import {DeleteProjectDialog} from "./Modals/RemoveProject/RemoveProject.compnent";
import { PasswordChangeConfirmedDialog } from './Modals/PasswordChangeConfirmed/PasswordChangeConfimred.component';
import {ShareMenu} from "./Modals/ShareMenu/shareMenu.component";
import {DeleteRequirmentDialog} from "./Modals/RemoveReq/RemoveReq.component";

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MenuComponent,
    NewProjectDialog,
    GDPRContent,
    userEditComponent,
    DeleteProjectDialog,
    PasswordChangeConfirmedDialog,
    DeleteUserDialog,
    adminComponent,
    AdminDeleteUserDialog,
    kravEditComponent,
    DeleteProjectDialog,
    ShareMenu,
    ConcurrencyResolver,
    DeleteRequirmentDialog
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
    DeleteProjectDialog,
    PasswordChangeConfirmedDialog,
    DeleteUserDialog,
    ShareMenu,
    AdminDeleteUserDialog,
  ],
  entryComponents: [
    NewProjectDialog,
    GDPRContent,
    DeleteRequirmentDialog,
    DeleteProjectDialog,
    PasswordChangeConfirmedDialog,
    ShareMenu,
    AdminDeleteUserDialog,
    ConcurrencyResolver,
    DeleteRequirmentDialog,
    DeleteUserDialog
  ]
})
export class AppModule { }
