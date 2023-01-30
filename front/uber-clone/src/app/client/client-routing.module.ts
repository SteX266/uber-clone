import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChangePasswordComponent } from '../profile/change-password/change-password.component';
import { ChangePhotoComponent } from '../profile/change-photo/change-photo.component';
import { ClientHomePageComponent } from './client-home-page/client-home-page.component';
import { ProfilePageComponent } from '../profile/profile-page/profile-page.component';
import { UserPersonalInfoUpdateComponent } from '../profile/user-personal-info-update/user-personal-info-update.component';
import { ClinetNavbarComponent } from './clinet-navbar/clinet-navbar.component';
import { ChatComponent } from '../shared/chat/chat.component';
import { RideCardComponent } from '../shared/ride-card/ride-card.component';

const routes: Routes = [
  {
    path: 'client',
    component: ClinetNavbarComponent,
    children: [
      { path: 'profile/:id', component: ProfilePageComponent },
      {
        path: 'edit-personal-info',
        component: UserPersonalInfoUpdateComponent,
      },
      { path: 'edit-password', component: ChangePasswordComponent },
      { path: 'edit-picture', component: ChangePhotoComponent },
      { path: 'edit-payment', component: ChangePasswordComponent },
      { path: 'history', component: RideCardComponent },

      { path: 'support', component: ChatComponent },
      { path: '', component: ClientHomePageComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClientRoutingModule {}
