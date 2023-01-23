import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserProfilePageComponent } from './shared/user-profile-page/user-profile-page.component';
import { HomePageComponent } from './guest/home-page/home-page.component';
import { ForgotPasswordComponent } from './guest/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './guest/reset-password/reset-password.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'profile', component: UserProfilePageComponent },
  {path:'forgot-password', component:ForgotPasswordComponent},
  {path:'reset_password', component:ResetPasswordComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
