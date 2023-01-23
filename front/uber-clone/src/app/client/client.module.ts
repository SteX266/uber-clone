import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModelModule } from '../models/model.module';
import { FormsModule } from '@angular/forms';

import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { MatInputModule } from '@angular/material/input';
import { UserPersonalInfoUpdateComponent } from '../client/user-personal-info-update/user-personal-info-update.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ChangePhotoComponent } from './change-photo/change-photo.component';
import { ClientRoutingModule } from './client-routing.module';
import { ClientHomePageComponent } from './client-home-page/client-home-page.component';
import { EditClientProfileComponent } from './edit-client-profile/edit-client-profile.component';
import { CurrentClientProfileComponent } from './current-client-profile/current-client-profile.component';
import { SharedModule } from '../shared/shared.module';
@NgModule({
  declarations: [
    UserPersonalInfoUpdateComponent,
    ChangePasswordComponent,
    ChangePhotoComponent,
    ClientHomePageComponent,
    EditClientProfileComponent,
    CurrentClientProfileComponent,
  ],
  imports: [
    CommonModule,
    ModelModule,
    FormsModule,
    ClientRoutingModule,
    SharedModule,
  ],
  exports: [UserPersonalInfoUpdateComponent],
})
export class ClientModule {}
