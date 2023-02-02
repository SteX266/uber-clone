import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModelModule } from '../models/model.module';
import { FormsModule } from '@angular/forms';
import { UserPersonalInfoUpdateComponent } from '../profile/user-personal-info-update/user-personal-info-update.component';
import { ChangePasswordComponent } from '../profile/change-password/change-password.component';
import { ChangePhotoComponent } from '../profile/change-photo/change-photo.component';
import { ClientRoutingModule } from './client-routing.module';
import { ClientHomePageComponent } from './client-home-page/client-home-page.component';
import { EditClientProfileComponent } from './edit-client-profile/edit-client-profile.component';
import { ProfilePageComponent } from '../profile/profile-page/profile-page.component';

import { ReviewRideModalComponent } from './review-ride-modal/review-ride-modal.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatDialogModule } from '@angular/material/dialog';
import { BalanceComponent } from './balance/balance.component';
import { ClientMapComponent } from './client-map/client-map.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { MapSidebarComponent } from './map-sidebar/map-sidebar.component';
import { ReservationPreviewComponent } from './reservation-preview/reservation-preview.component';
import { SplitFareComponent } from './split-fare/split-fare.component';
import { ClinetNavbarComponent } from './clinet-navbar/clinet-navbar.component';
import { PaymentModalComponent } from './payment-modal/payment-modal.component';
import { RideMapComponent } from './ride-map/ride-map.component';
import { CalendarModule } from 'primeng/calendar';

@NgModule({
  declarations: [
    ClientHomePageComponent,
    EditClientProfileComponent,
    ReviewRideModalComponent,
    BalanceComponent,
    ClientMapComponent,
    MapSidebarComponent,
    ReservationPreviewComponent,
    SplitFareComponent,
    ClinetNavbarComponent,
    PaymentModalComponent,
    RideMapComponent,
  ],
  imports: [
    CommonModule,
    ModelModule,
    FormsModule,
    ClientRoutingModule,
    NgbModule,
    MatDialogModule,
    LeafletModule,
    CalendarModule,
  ],
  exports: [EditClientProfileComponent],
})
export class ClientModule {}
