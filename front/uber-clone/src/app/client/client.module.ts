import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModelModule } from '../models/model.module';
import { FormsModule } from '@angular/forms';
import { UserPersonalInfoUpdateComponent } from '../shared/user-personal-info-update/user-personal-info-update.component';
import { ChangePasswordComponent } from '../shared/change-password/change-password.component';
import { ChangePhotoComponent } from '../shared/change-photo/change-photo.component';
import { ClientRoutingModule } from './client-routing.module';
import { ClientHomePageComponent } from './client-home-page/client-home-page.component';
import { EditClientProfileComponent } from './edit-client-profile/edit-client-profile.component';
import { ProfilePageComponent } from '../shared/profile-page/profile-page.component';

import { ReviewRideModalComponent } from './review-ride-modal/review-ride-modal.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    ClientHomePageComponent,
    EditClientProfileComponent,
    ReviewRideModalComponent,
  ],
  imports: [
    CommonModule,
    ModelModule,
    FormsModule,
    ClientRoutingModule,
    NgbModule,
    MatDialogModule,
  ],
  exports: [EditClientProfileComponent],
})
export class ClientModule {}
