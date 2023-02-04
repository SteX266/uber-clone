import { Component } from '@angular/core';

import { MatDialog } from '@angular/material/dialog';
import { BalanceComponent } from 'src/app/client/components/balance/balance.component';
import { ReviewRideModalComponent } from 'src/app/client/components/review-ride-modal/review-ride-modal.component';
import { SignInModalComponent } from '../sign-in-modal/sign-in-modal.component';
import { SignUpModalComponent } from '../sign-up-modal/sign-up-modal.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  constructor(public dialog: MatDialog){}

  
  openSignUpDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {
    this.dialog.open(SignUpModalComponent, {
      width: '500px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  }

  openSignInDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {
    this.dialog.open(SignInModalComponent, {
      width: '500px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  }

  openReviewDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {
    this.dialog.open(ReviewRideModalComponent, {
      width: '500px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  }

  openBalanceDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {
    this.dialog.open(BalanceComponent, {
      width: '300px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  }

}
