import { Component, Inject, Input } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReservationPreviewComponent } from 'src/app/client/components/reservation-preview/reservation-preview.component';
import { ReviewRideModalComponent } from 'src/app/client/components/review-ride-modal/review-ride-modal.component';
import { ReservationDTO } from 'src/app/models/reservation-dto.model';
import { Ride } from 'src/app/models/ride';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ReservationService } from 'src/app/services/reservation/reservation.service';
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
    public dialog: MatDialog,
    private reservationService:ReservationService
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
    this.reservationService.getReservationByRideId(this.ride.id).subscribe({next:(val:any)=>{
      console.log(val);
      val.reservationTime = new Date();
      this.openReservationPreviewModal(val,"0ms","0ms");
    },error:(err)=>{
      console.log("greska");
      console.log(err);

    }});

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

  openReservationPreviewModal(
    reservationDTO:ReservationDTO,
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {
    this.dialog.open(ReservationPreviewComponent, {
      data: reservationDTO,
      width: '500px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  }
}
