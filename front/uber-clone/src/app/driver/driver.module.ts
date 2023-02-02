import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DriverRoutingModule } from './driver-routing.module';
import { EditDriverProfileComponent } from './edit-driver-profile/edit-driver-profile.component';
import { FormsModule } from '@angular/forms';
import { OtherProfileComponent } from './other-profile/other-profile.component';
import { DriverNavbarComponent } from './driver-navbar/driver-navbar.component';
import { DriverHomePageComponent } from './driver-home-page/driver-home-page.component';
import { DriverMapComponent } from './driver-map/driver-map.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { DriverSidebarComponent } from './driver-sidebar/driver-sidebar.component';

@NgModule({
  declarations: [
    EditDriverProfileComponent,
    OtherProfileComponent,
    DriverNavbarComponent,
    DriverHomePageComponent,
    DriverMapComponent,
    DriverSidebarComponent,
  ],
  imports: [CommonModule, DriverRoutingModule, FormsModule, LeafletModule],
  exports: [EditDriverProfileComponent, OtherProfileComponent],
})
export class DriverModule {}
