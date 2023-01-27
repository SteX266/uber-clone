import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { RideCardComponent } from './ride-card/ride-card.component';
import { BrowserModule } from '@angular/platform-browser';
import { UserPersonalInfoUpdateComponent } from './user-personal-info-update/user-personal-info-update.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ChangePhotoComponent } from './change-photo/change-photo.component';
import { DriverModule } from '../driver/driver.module';
import { ClientModule } from '../client/client.module';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ChangeCarComponent } from './change-car/change-car.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { AdminModule } from '../admin/admin.module';
@NgModule({
  declarations: [
    UserProfileComponent,
    RideCardComponent,
    UserPersonalInfoUpdateComponent,
    ChangePasswordComponent,
    ChangePhotoComponent,
    ChangeCarComponent,
    ProfilePageComponent,
  ],
  imports: [
    RouterModule,
    BrowserModule,
    DriverModule,
    ClientModule,
    FormsModule,
    NgbModule,
    AdminModule,
  ],
  exports: [UserProfileComponent, RideCardComponent],
})
export class SharedModule {}
