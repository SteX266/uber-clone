import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { MapSearchService } from './services/map-search.service';
import { GuestModule } from './guest/guest.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    LeafletModule,
    HttpClientModule,
    GuestModule,
  ],
  providers: [MapSearchService],
  bootstrap: [AppComponent],
})
export class AppModule {}
