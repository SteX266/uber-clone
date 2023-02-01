import { NgModule } from '@angular/core';
import { ChangeCarComponent } from './change-car/change-car.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ChangePhotoComponent } from './change-photo/change-photo.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { UserPersonalInfoUpdateComponent } from './user-personal-info-update/user-personal-info-update.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AdminModule } from '../admin/admin.module';
import { ClientModule } from '../client/client.module';
import { DriverModule } from '../driver/driver.module';
import { PaymentMethodComponent } from './payment-method/payment-method.component';

@NgModule({
  declarations: [
    ChangeCarComponent,
    ChangePasswordComponent,
    ChangePhotoComponent,
    ProfilePageComponent,
    UserPersonalInfoUpdateComponent,
    UserProfileComponent,
    PaymentMethodComponent
  ],
  imports: [
    RouterModule,
    BrowserModule,
    FormsModule,
    NgbModule,
    AdminModule,
    ClientModule,
    DriverModule,
  ],
})
export class ProfileModule {}
