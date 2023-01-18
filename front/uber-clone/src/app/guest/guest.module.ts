import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './home-page/home-page.component';
import { ModelModule } from '../models/model.module';
import { FormsModule } from '@angular/forms';
import { GeocodingComponent } from './geocoding/geocoding.component';

@NgModule({
  declarations: [HomePageComponent, GeocodingComponent],
  imports: [CommonModule, ModelModule, FormsModule],
  exports: [HomePageComponent],
})
export class GuestModule {}
