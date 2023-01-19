import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { MapSearchService } from './services/map-search.service';
import { GuestModule } from './guest/guest.module';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { AuthService } from './services/auth/auth.service';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    LeafletModule,
    HttpClientModule,
    MatDialogModule,
    GuestModule,
    MatSnackBarModule,
    BrowserAnimationsModule,
  ],
  providers: [
    MapSearchService,
    {
      provide: MatDialogRef,
      useValue: {},
    },
    AuthService
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
