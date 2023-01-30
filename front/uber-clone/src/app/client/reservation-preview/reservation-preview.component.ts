import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReservationDTO } from 'src/app/models/reservation-dto.model';
import { ReservationService } from 'src/app/services/reservation/reservation.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';

@Component({
  selector: 'app-reservation-preview',
  templateUrl: './reservation-preview.component.html',
  styleUrls: ['./reservation-preview.component.scss'],
})
export class ReservationPreviewComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<ReservationPreviewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ReservationDTO,
    private reservationService: ReservationService,
    private snackbar: SnackBarService
  ) {}
  time!: string;
  ngOnInit(): void {
    this.time = this.prettyTime(this.data.estimatedTime);
  }

  prettyTime(time: number) {
    var minutesInSeconds = time * 60;
    var wholeMinutes = Math.floor(time);
    var remainingSeconds = Math.round(minutesInSeconds - wholeMinutes * 60);
    return wholeMinutes + ':' + remainingSeconds.toString().padStart(2, '0');
  }

  confirmReservation() {
    this.reservationService.makeReservation(this.data).subscribe({
      next: (data: any) => {
        console.log(data);
      },
      error: (err: any) => {
        console.log(err);
      },
    });
    this.dialogRef.close();
  }
}