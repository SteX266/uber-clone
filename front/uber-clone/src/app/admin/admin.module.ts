import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminOtherUserProfileComponent } from './admin-other-user-profile/admin-other-user-profile.component';
import { AdminNavbarComponent } from './admin-navbar/admin-navbar.component';
import { SharedModule } from '../shared/shared.module';
import { EditAdminProfileComponent } from './edit-admin-profile/edit-admin-profile.component';
import { UpdateProfileRequestsComponent } from './update-profile-requests/update-profile-requests.component';
import { FormsModule } from '@angular/forms';
import { UpdateRequestComponent } from './update-request/update-request.component';
import { NotesComponent } from './notes/notes.component';

@NgModule({
  declarations: [
    AdminOtherUserProfileComponent,
    AdminNavbarComponent,
    EditAdminProfileComponent,
    UpdateProfileRequestsComponent,
    UpdateRequestComponent,
    NotesComponent,
  ],
  imports: [CommonModule, AdminRoutingModule, SharedModule, FormsModule],
  exports: [AdminOtherUserProfileComponent, EditAdminProfileComponent],
})
export class AdminModule {}
