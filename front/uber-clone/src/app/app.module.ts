import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { FormMapContainerComponent } from './form-map-container/form-map-container.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { GoogleMapsModule } from '@angular/google-maps';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    HeaderComponent,
    FooterComponent,
    FormMapContainerComponent,
    SignUpComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, GoogleMapsModule, FormsModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
