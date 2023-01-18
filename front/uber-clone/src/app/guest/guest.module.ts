import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './home-page/home-page.component';
import { ModelModule } from '../models/model.module';
import { FormsModule } from '@angular/forms';
import { GeocodingComponent } from './geocoding/geocoding.component';
import { SignUpModalComponent } from './sign-up-modal/sign-up-modal.component';
import { SignInModalComponent } from './sign-in-modal/sign-in-modal.component';
import { FormMapContainerComponent } from './form-map-container/form-map-container.component';
import { MapSmallComponent } from './map-small/map-small.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';

@NgModule({
  declarations: [
    HomePageComponent,
    GeocodingComponent,
    SignUpModalComponent,
    SignInModalComponent,
    FormMapContainerComponent,
    MapSmallComponent,
    HeaderComponent,
    FooterComponent,
  ],
  imports: [CommonModule, ModelModule, FormsModule, LeafletModule],
  exports: [HomePageComponent],
})
export class GuestModule {}
