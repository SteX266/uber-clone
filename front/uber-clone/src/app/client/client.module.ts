import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModelModule } from '../models/model.module';
import { FormsModule } from '@angular/forms';
import { UserPersonalInfoUpdateComponent } from '../client/user-personal-info-update/user-personal-info-update.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ChangePhotoComponent } from './change-photo/change-photo.component';
import { ClientRoutingModule } from './client-routing.module';
import { ClientHomePageComponent } from './client-home-page/client-home-page.component';
import { EditClientProfileComponent } from './edit-client-profile/edit-client-profile.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { SharedModule } from '../shared/shared.module';
import { DriverModule } from '../driver/driver.module';
@NgModule({
  declarations: [
    UserPersonalInfoUpdateComponent,
    ChangePasswordComponent,
    ChangePhotoComponent,
    ClientHomePageComponent,
    EditClientProfileComponent,
    ProfilePageComponent,
  ],
  imports: [
    CommonModule,
    ModelModule,
    FormsModule,
    ClientRoutingModule,
    SharedModule,
    DriverModule,
  ],
  exports: [UserPersonalInfoUpdateComponent],
})
export class ClientModule {}
