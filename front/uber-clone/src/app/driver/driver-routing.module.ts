import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChangePasswordComponent } from '../profile/change-password/change-password.component';
import { ChangePhotoComponent } from '../profile/change-photo/change-photo.component';
import { ClientHomePageComponent } from '../client/client-home-page/client-home-page.component';
import { ProfilePageComponent } from '../profile/profile-page/profile-page.component';
import { UserPersonalInfoUpdateComponent } from '../profile/user-personal-info-update/user-personal-info-update.component';
import { HeaderComponent } from '../guest/header/header.component';
import { ChangeCarComponent } from '../profile/change-car/change-car.component';
import { DriverNavbarComponent } from './driver-navbar/driver-navbar.component';
import { ChatComponent } from '../shared/chat/chat.component';
import { DriverHomePageComponent } from './driver-home-page/driver-home-page.component';
import { RidesHistoryPageComponent } from '../shared/rides-history-page/rides-history-page.component';

const routes: Routes = [
  {
    path: 'driver',
    component: DriverNavbarComponent,
    children: [
      { path: 'profile/:id', component: ProfilePageComponent },
      {
        path: 'edit-personal-info',
        component: UserPersonalInfoUpdateComponent,
      },
      { path: 'history', component: RidesHistoryPageComponent },
      { path: 'edit-password', component: ChangePasswordComponent },
      { path: 'edit-picture', component: ChangePhotoComponent },
      { path: 'edit-car', component: ChangeCarComponent },
      { path: 'support', component: ChatComponent },
      { path: '', component: DriverHomePageComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DriverRoutingModule {}
