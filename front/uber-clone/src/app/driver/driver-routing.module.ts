import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChangePasswordComponent } from '../client/change-password/change-password.component';
import { ChangePhotoComponent } from '../client/change-photo/change-photo.component';
import { ClientHomePageComponent } from '../client/client-home-page/client-home-page.component';
import { ProfilePageComponent } from '../client/profile-page/profile-page.component';
import { UserPersonalInfoUpdateComponent } from '../client/user-personal-info-update/user-personal-info-update.component';
import { HeaderComponent } from '../guest/header/header.component';
import { ChangeCarComponent } from './change-car/change-car.component';

const routes: Routes = [
  {
    path: 'driver',
    component: HeaderComponent,
    children: [
      { path: 'profile/:id', component: ProfilePageComponent },
      {
        path: 'edit-personal-info',
        component: UserPersonalInfoUpdateComponent,
      },
      { path: 'edit-password', component: ChangePasswordComponent },
      { path: 'edit-picture', component: ChangePhotoComponent },
      { path: 'edit-car', component: ChangeCarComponent },
      { path: '', component: ClientHomePageComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DriverRoutingModule {}
