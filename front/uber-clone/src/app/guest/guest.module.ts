import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { ModelModule } from '../models/model.module';
import { GeocodingComponent } from './components/geocoding/geocoding.component';
import { SignUpModalComponent } from './components/sign-up-modal/sign-up-modal.component';
import { SignInModalComponent } from './components/sign-in-modal/sign-in-modal.component';
import { FormMapContainerComponent } from './components/form-map-container/form-map-container.component';
import { MapSmallComponent } from './components/map-small/map-small.component';
import { HeaderComponent } from './components/header/header.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {
  SocialAuthServiceConfig,
  SocialLoginModule,
} from '@abacritt/angularx-social-login';
import {
  FacebookLoginProvider,
  GoogleLoginProvider,
} from '@abacritt/angularx-social-login';
import { GuestRoutingModule } from './guest-routing.module';

@NgModule({
  declarations: [
    HomePageComponent,
    GeocodingComponent,
    SignUpModalComponent,
    SignInModalComponent,
    FormMapContainerComponent,
    MapSmallComponent,
    HeaderComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
  ],
  imports: [
    RouterModule,
    CommonModule,
    ModelModule,
    FormsModule,
    LeafletModule,
    MatInputModule,
    SocialLoginModule,
    GuestRoutingModule,
  ],
  exports: [HomePageComponent],
  providers:[


        {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider('518524193712114'),
          },
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '164509104596-3kctgprgablt8h80f8av9palueb5817k.apps.googleusercontent.com'
            ),
          },
        ],
        onError: (err) => {
          console.log(err);
        },
      } as SocialAuthServiceConfig,
    },
  ]
})
export class GuestModule {}
