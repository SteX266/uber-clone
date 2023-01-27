import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './guest/home-page/home-page.component';
import { ForgotPasswordComponent } from './guest/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './guest/reset-password/reset-password.component';
import { ChangePasswordComponent } from './shared/change-password/change-password.component';
import { UserProfileComponent } from './shared/user-profile/user-profile.component';
import { UserPersonalInfoUpdateComponent } from './shared/user-personal-info-update/user-personal-info-update.component';
import { ChangePhotoComponent } from './shared/change-photo/change-photo.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { RideCardComponent } from './shared/ride-card/ride-card.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset_password', component: ResetPasswordComponent },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
