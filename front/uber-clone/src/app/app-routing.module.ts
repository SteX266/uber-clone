import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './guest/home-page/home-page.component';
import { ChangePasswordComponent } from './client/change-password/change-password.component';
import { UserProfileComponent } from './shared/user-profile/user-profile.component';
import { UserPersonalInfoUpdateComponent } from './client/user-personal-info-update/user-personal-info-update.component';
import { ChangePhotoComponent } from './client/change-photo/change-photo.component';
import { NotFoundComponent } from './not-found/not-found.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
