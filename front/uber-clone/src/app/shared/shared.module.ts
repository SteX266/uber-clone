import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { RideCardComponent } from './ride-card/ride-card.component';
import { BrowserModule } from '@angular/platform-browser';
@NgModule({
  declarations: [UserProfileComponent, RideCardComponent],
  imports: [RouterModule, BrowserModule],
  exports: [UserProfileComponent, RideCardComponent],
})
export class SharedModule {}
