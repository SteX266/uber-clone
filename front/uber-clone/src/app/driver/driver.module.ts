import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DriverRoutingModule } from './driver-routing.module';
import { EditDriverProfileComponent } from './edit-driver-profile/edit-driver-profile.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [EditDriverProfileComponent],
  imports: [CommonModule, DriverRoutingModule, FormsModule],
  exports: [EditDriverProfileComponent],
})
export class DriverModule {}
