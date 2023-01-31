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

import { ClientModule } from './client/client.module';
import { SharedModule } from './shared/shared.module';
import { NotFoundComponent } from './not-found/not-found.component';
import { DriverModule } from './driver/driver.module';
import { SignUpDriverModalComponent } from './admin/sign-up-driver-modal/sign-up-driver-modal.component';
import { LocationService } from './services/location/location.service';
import { PriceService } from './services/price/price.service';
import { AdminModule } from './admin/admin.module';
import { ProfileModule } from './profile/profile.module';

@NgModule({
  declarations: [AppComponent, NotFoundComponent, SignUpDriverModalComponent],
  imports: [
    BrowserModule,
    AdminModule,
    DriverModule,
    FormsModule,
    LeafletModule,
    HttpClientModule,
    MatDialogModule,
    GuestModule,
    ClientModule,
    MatSnackBarModule,
    BrowserAnimationsModule,
    SharedModule,
    AppRoutingModule,

    ProfileModule,
  ],
  providers: [
    MapSearchService,
    LocationService,
    PriceService,
    {
      provide: MatDialogRef,
      useValue: {},
    },

    AuthService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
