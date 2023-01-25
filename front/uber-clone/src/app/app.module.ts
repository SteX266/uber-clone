import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { MapSearchService } from './services/map-search/map-search.service';
import { GuestModule } from './guest/guest.module';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { AuthService } from './services/auth/auth.service';
import {
  SocialAuthServiceConfig,
  SocialLoginModule,
} from '@abacritt/angularx-social-login';
import {
  FacebookLoginProvider,
  GoogleLoginProvider,
} from '@abacritt/angularx-social-login';
import { ClientModule } from './client/client.module';
import { SharedModule } from './shared/shared.module';
import { NotFoundComponent } from './not-found/not-found.component';
import { DriverModule } from './driver/driver.module';
import { SignUpDriverModalComponent } from './admin/sign-up-driver-modal/sign-up-driver-modal.component';

@NgModule({
  declarations: [AppComponent, NotFoundComponent, SignUpDriverModalComponent],
  imports: [
    BrowserModule,
    DriverModule,
    FormsModule,
    LeafletModule,
    HttpClientModule,
    MatDialogModule,
    GuestModule,
    ClientModule,
    MatSnackBarModule,
    BrowserAnimationsModule,
    SocialLoginModule,
    SharedModule,
    AppRoutingModule,
  ],
  providers: [
    MapSearchService,
    {
      provide: MatDialogRef,
      useValue: {},
    },
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
              '191262979102-176eepve2drkqg8bllce1dv3996mjf1o.apps.googleusercontent.com'
            ),
          },
        ],
        onError: (err) => {
          console.log(err);
        },
      } as SocialAuthServiceConfig,
    },
    AuthService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
