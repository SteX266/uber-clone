import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DriverRoutingModule } from './driver-routing.module';
import { EditDriverProfileComponent } from './edit-driver-profile/edit-driver-profile.component';
import { ChangeCarComponent } from './change-car/change-car.component';
import { SharedModule } from '../shared/shared.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [EditDriverProfileComponent, ChangeCarComponent],
  imports: [CommonModule, DriverRoutingModule, SharedModule, FormsModule],
  exports: [EditDriverProfileComponent],
})
export class DriverModule {}
