import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DriverRoutingModule } from './driver-routing.module';
import { EditDriverProfileComponent } from './edit-driver-profile/edit-driver-profile.component';
import { FormsModule } from '@angular/forms';
import { OtherProfileComponent } from './other-profile/other-profile.component';

@NgModule({
  declarations: [EditDriverProfileComponent, OtherProfileComponent],
  imports: [CommonModule, DriverRoutingModule, FormsModule],
  exports: [EditDriverProfileComponent, OtherProfileComponent],
})
export class DriverModule {}
