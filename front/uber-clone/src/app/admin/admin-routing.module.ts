import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientHomePageComponent } from '../client/client-home-page/client-home-page.component';
import { ChangePasswordComponent } from '../profile/change-password/change-password.component';
import { ChangePhotoComponent } from '../profile/change-photo/change-photo.component';
import { ProfilePageComponent } from '../profile/profile-page/profile-page.component';
import { UserPersonalInfoUpdateComponent } from '../profile/user-personal-info-update/user-personal-info-update.component';
import { ChatComponent } from '../shared/chat/chat.component';
import { AdminNavbarComponent } from './admin-navbar/admin-navbar.component';
import { UpdateProfileRequestsComponent } from './update-profile-requests/update-profile-requests.component';
import { UpdateRequestComponent } from './update-request/update-request.component';

const routes: Routes = [
  {
    path: 'admin',
    component: AdminNavbarComponent,
    children: [
      { path: 'profile/:id', component: ProfilePageComponent },
      {
        path: 'edit-personal-info',
        component: UserPersonalInfoUpdateComponent,
      },
      { path: 'edit-password', component: ChangePasswordComponent },
      { path: 'edit-picture', component: ChangePhotoComponent },
      { path: 'support', component: ChatComponent },
      {
        path: 'profile-change-requests',
        component: UpdateProfileRequestsComponent,
      },
      {
        path: 'profile-change-request/:id',
        component: UpdateRequestComponent,
      },
      { path: '', component: ClientHomePageComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
