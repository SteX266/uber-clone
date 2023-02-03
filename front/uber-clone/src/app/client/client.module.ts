import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModelModule } from '../models/model.module';
import { FormsModule } from '@angular/forms';
import { UserPersonalInfoUpdateComponent } from '../profile/pages/user-personal-info-update/user-personal-info-update.component';
import { ChangePasswordComponent } from '../profile/pages/change-password/change-password.component';
import { ChangePhotoComponent } from '../profile/pages/change-photo/change-photo.component';
import { ClientRoutingModule } from './client-routing.module';
import { ClientHomePageComponent } from './pages/client-home-page/client-home-page.component';
import { EditClientProfileComponent } from './components/edit-client-profile/edit-client-profile.component';
import { ProfilePageComponent } from '../profile/pages/profile-page/profile-page.component';

import { ReviewRideModalComponent } from './components/review-ride-modal/review-ride-modal.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatDialogModule } from '@angular/material/dialog';
import { BalanceComponent } from './components/balance/balance.component';
import { ClientMapComponent } from './components/client-map/client-map.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { MapSidebarComponent } from './components/map-sidebar/map-sidebar.component';
import { ReservationPreviewComponent } from './components/reservation-preview/reservation-preview.component';
import { SplitFareComponent } from './components/split-fare/split-fare.component';
import { ClinetNavbarComponent } from './components/clinet-navbar/clinet-navbar.component';
import { PaymentModalComponent } from './components/payment-modal/payment-modal.component';
import { RideMapComponent } from './pages/ride-map/ride-map.component';
import { CalendarModule } from 'primeng/calendar';
import { FavoriteRouteModalComponent } from './components/favorite-route-modal/favorite-route-modal.component';

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
    FavoriteRouteModalComponent,
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
