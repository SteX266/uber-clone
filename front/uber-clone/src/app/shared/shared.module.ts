import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { RideCardComponent } from './ride-card/ride-card.component';
import { BrowserModule } from '@angular/platform-browser';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [UserProfileComponent, RideCardComponent, FooterComponent],
  imports: [RouterModule, BrowserModule],
  exports: [UserProfileComponent, RideCardComponent, FooterComponent],
})
export class SharedModule {}
