import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminOtherUserProfileComponent } from './admin-other-user-profile/admin-other-user-profile.component';
import { AdminNavbarComponent } from './admin-navbar/admin-navbar.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [AdminOtherUserProfileComponent, AdminNavbarComponent],
  imports: [CommonModule, AdminRoutingModule, SharedModule],
  exports: [AdminOtherUserProfileComponent],
})
export class AdminModule {}
