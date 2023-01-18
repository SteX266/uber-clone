import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { FormMapContainerComponent } from './form-map-container/form-map-container.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { SignUpModalComponent } from './sign-up-modal/sign-up-modal.component';
import {MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import { SignInModalComponent } from './sign-in-modal/sign-in-modal.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    HeaderComponent,
    FooterComponent,
    FormMapContainerComponent,
    SignUpModalComponent,
    SignInModalComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    GoogleMapsModule,
    FormsModule,
    MatDialogModule,
    HttpClientModule,
    MatSnackBarModule,
    BrowserAnimationsModule
  ],
  providers: [     {
    provide: MatDialogRef,
    useValue: {}
  }],
  bootstrap: [AppComponent],
})
export class AppModule {}
