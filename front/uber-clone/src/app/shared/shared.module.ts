import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserProfileComponent } from './user-profile/user-profile.component';
@NgModule({
  declarations: [UserProfileComponent],
  imports: [RouterModule],
  exports: [UserProfileComponent],
})
export class SharedModule {}
