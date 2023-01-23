import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HeaderComponent } from '../guest/header/header.component';
import { UserProfileComponent } from '../shared/user-profile/user-profile.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ChangePhotoComponent } from './change-photo/change-photo.component';
import { ClientHomePageComponent } from './client-home-page/client-home-page.component';
import { CurrentClientProfileComponent } from './current-client-profile/current-client-profile.component';
import { UserPersonalInfoUpdateComponent } from './user-personal-info-update/user-personal-info-update.component';

const routes: Routes = [
  {
    path: 'client',
    component: HeaderComponent,
    children: [
      { path: 'profile/:id', component: CurrentClientProfileComponent },
      {
        path: 'edit-personal-info',
        component: UserPersonalInfoUpdateComponent,
      },
      { path: 'edit-password', component: ChangePasswordComponent },
      { path: 'edit-picture', component: ChangePhotoComponent },
      { path: 'edit-payment', component: ChangePasswordComponent },
      { path: '', component: ClientHomePageComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClientRoutingModule {}
