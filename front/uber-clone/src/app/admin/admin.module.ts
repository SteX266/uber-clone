import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminOtherUserProfileComponent } from './components/admin-other-user-profile/admin-other-user-profile.component';
import { AdminNavbarComponent } from './components/admin-navbar/admin-navbar.component';
import { SharedModule } from '../shared/shared.module';
import { EditAdminProfileComponent } from './components/edit-admin-profile/edit-admin-profile.component';
import { UpdateProfileRequestsComponent } from './pages/update-profile-requests/update-profile-requests.component';
import { FormsModule } from '@angular/forms';
import { UpdateRequestComponent } from './pages/update-request/update-request.component';
import { NotesComponent } from './pages/notes/notes.component';

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
