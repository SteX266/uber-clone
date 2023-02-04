import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChangePasswordComponent } from '../profile/pages/change-password/change-password.component';
import { ChangePhotoComponent } from '../profile/pages/change-photo/change-photo.component';
import { ClientHomePageComponent } from '../client/pages/client-home-page/client-home-page.component';
import { ProfilePageComponent } from '../profile/pages/profile-page/profile-page.component';
import { UserPersonalInfoUpdateComponent } from '../profile/pages/user-personal-info-update/user-personal-info-update.component';
import { HeaderComponent } from '../guest/components/header/header.component';
import { ChangeCarComponent } from '../profile/pages/change-car/change-car.component';
import { DriverNavbarComponent } from './components/driver-navbar/driver-navbar.component';
import { ChatComponent } from '../shared/pages/chat/chat.component';
import { DriverHomePageComponent } from './pages/driver-home-page/driver-home-page.component';
import { RidesHistoryPageComponent } from '../shared/pages/rides-history-page/rides-history-page.component';
import { ReportPageComponent } from '../shared/pages/report-page/report-page.component';

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
      {
        path: 'report/:id',
        component: ReportPageComponent,
      },
      { path: '', component: DriverHomePageComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DriverRoutingModule {}
