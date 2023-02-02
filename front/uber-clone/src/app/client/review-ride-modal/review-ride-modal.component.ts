import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgbRating } from '@ng-bootstrap/ng-bootstrap';
import { Review } from 'src/app/models/Review';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-review-ride-modal',
  templateUrl: './review-ride-modal.component.html',
  styleUrls: ['./review-ride-modal.component.scss'],
  styles: [
    `
      i {
        font-size: 1.5rem;
        padding-right: 0.1rem;
        color: #b0c4de;
      }
      .filled {
        color: #1e90ff;
      }
      .low {
        color: #deb0b0;
      }
      .filled.low {
        color: #ff1e1e;
      }
    `,
  ],
})
export class ReviewRideModalComponent {
  driverScore: number = 0;
  vehicleScore: number = 0;
  comment: string = '';

  constructor(
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public rideId: number,
    public dialogRef: MatDialogRef<ReviewRideModalComponent>,
    private router: Router
  ) {}

  createReview() {
    let review = new Review(
      this.rideId,
      this.driverScore,
      this.vehicleScore,
      this.comment
    );

    this.userService.createReview(review);
    this.dialogRef.close();
    this.router.navigate(['/client']);
  }
}
