import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChangePasswordComponent } from '../profile/pages/change-password/change-password.component';
import { ChangePhotoComponent } from '../profile/pages/change-photo/change-photo.component';
import { ClientHomePageComponent } from './pages/client-home-page/client-home-page.component';
import { ProfilePageComponent } from '../profile/pages/profile-page/profile-page.component';
import { UserPersonalInfoUpdateComponent } from '../profile/pages/user-personal-info-update/user-personal-info-update.component';
import { ClinetNavbarComponent } from './components/clinet-navbar/clinet-navbar.component';
import { ChatComponent } from '../shared/pages/chat/chat.component';
import { RideCardComponent } from '../shared/components/ride-card/ride-card.component';
import { RideMapComponent } from './pages/ride-map/ride-map.component';
import { PaymentMethodComponent } from '../profile/pages/payment-method/payment-method.component';
import { RidesHistoryPageComponent } from '../shared/pages/rides-history-page/rides-history-page.component';
import { ReportPageComponent } from '../shared/pages/report-page/report-page.component';
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
      { path: 'edit-payment', component: PaymentMethodComponent },
      { path: 'history', component: RidesHistoryPageComponent },
      {
        path: 'report/:id',
        component: ReportPageComponent,
      },
      { path: 'support', component: ChatComponent },
      { path: 'ride/:id', component: RideMapComponent },
      { path: '', component: ClientHomePageComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClientRoutingModule {}
