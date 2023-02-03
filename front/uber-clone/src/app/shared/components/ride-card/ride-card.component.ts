import { Component, Inject, Input } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReviewRideModalComponent } from 'src/app/client/components/review-ride-modal/review-ride-modal.component';
import { Ride } from 'src/app/models/ride';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-ride-card',
  templateUrl: './ride-card.component.html',
  styleUrls: ['./ride-card.component.scss'],
})
export class RideCardComponent {
  @Input()
  ride!: Ride;
  startString = '';
  endString = '';
  canReview = false;
  stations = '';
  client = false;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.client = this.authService.getCurrentUserRole() === 'CLIENT';
    this.ride.stops.forEach((element) => {
      this.stations = this.stations + ' - ' + element;
    });
    this.stations = this.stations.slice(2);
    this.startString = this.formateDate(this.ride.startTime);
    this.endString = this.formateDate(this.ride.endTime);
    this.review();
  }

  review() {
    if (this.authService.getCurrentUserRole() === 'CLIENT') {
      this.userService.isReviewPossible(this.ride.id).subscribe((data) => {
        this.canReview = data.able;
      });
    }
  }

  formateDate(date: any) {
    return (
      date[3] + ':' + date[4] + ' ' + date[1] + '/' + date[2] + '/' + date[0]
    );
  }

  repeatRide() {
    console.log('PUSI GA ESTEBANJEEe');
  }

  openReviewDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {
    this.dialog.open(ReviewRideModalComponent, {
      data: this.ride.id,
      width: '500px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  }
}
