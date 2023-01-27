import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminOtherUserProfileComponent } from './admin-other-user-profile/admin-other-user-profile.component';

@NgModule({
  declarations: [AdminOtherUserProfileComponent],
  imports: [CommonModule, AdminRoutingModule],
  exports: [AdminOtherUserProfileComponent],
})
export class AdminModule {}
