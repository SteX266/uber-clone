import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChangePasswordComponent } from '../shared/change-password/change-password.component';
import { ChangePhotoComponent } from '../shared/change-photo/change-photo.component';
import { ClientHomePageComponent } from '../client/client-home-page/client-home-page.component';
import { ProfilePageComponent } from '../shared/profile-page/profile-page.component';
import { UserPersonalInfoUpdateComponent } from '../shared/user-personal-info-update/user-personal-info-update.component';
import { HeaderComponent } from '../guest/header/header.component';
import { ChangeCarComponent } from '../shared/change-car/change-car.component';

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
